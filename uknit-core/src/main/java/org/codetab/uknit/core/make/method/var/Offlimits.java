package org.codetab.uknit.core.make.method.var;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.nonNull;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.exp.Arrays;
import org.codetab.uknit.core.make.exp.ExpManager;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.load.Loader;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Nature;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Expressions;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Variables;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.SimpleName;

/**
 *
 * @author Maithilish
 *
 */
public class Offlimits {

    @Inject
    private Nodes nodes;
    @Inject
    private Packs packs;
    @Inject
    private Expressions expressions;
    @Inject
    private Wrappers wrappers;
    @Inject
    private Variables variables;
    @Inject
    private Arrays arrays;
    @Inject
    private ExpManager expManager;

    /**
     * Test class don't have access to instances produced and consumed inside a
     * method. <code>
     *
     * public int foo() {
     *   int[] array = new int[2];
     *   int foo = array[0];
     *   return foo;
     * }
     *
     * </code> Here array is created and accessed within the method and test
     * method can't access the array. Mark such vars with Nature.OFFLIMIT. The
     * foo is returned by the method and is visible to test class.
     *
     * Fields are visible if not created or static.
     *
     * Parameters and return vars, either real or mock, are visible.
     *
     * The objects returned by visible mocks are also visible.
     *
     * The objects returned by visible real are visible only if it is field or
     * parameter or return var (1st rule takes care of this) or if it is an
     * infer var.
     *
     * The objects returned by visible real collections are visible.
     *
     * The objects returned by visible array (array access) are visible.
     *
     * The LHS var is visible if RHS var is visible.
     *
     * @param pack
     * @param heap
     */
    public void addNature(final Pack pack, final Heap heap) {
        checkNotNull(pack.getVar());

        IVar var = pack.getVar();

        boolean offlimit = true;

        if (var.isField() && !(var.isCreated() || variables.isStatic(var))) {
            offlimit = false;
        }

        if (var.isParameter() || var.isReturnVar()) {
            offlimit = false;
        }

        Expression cleanedExp = wrappers.unpack(pack.getExp());

        if (pack instanceof Invoke) {
            Invoke invoke = (Invoke) pack;

            Optional<IVar> callVarO = invoke.getCallVar();
            if (callVarO.isPresent()) {
                IVar callVar = callVarO.get();
                boolean callVarIsAccessible = !callVar.is(Nature.OFFLIMIT);
                if (callVar.isMock() && callVarIsAccessible) {
                    offlimit = false;
                }
                if (!callVar.isMock() && callVarIsAccessible
                        && var.isInferVar()) {
                    offlimit = false;
                }
                if (!callVar.isMock() && callVarIsAccessible
                        && callVar.is(Nature.COLLECTION)) {
                    offlimit = false;
                }
            }
        } else if (expressions.isInvokable(cleanedExp)) {
            /*
             * Ex: FileInputStream bar = ((FileInputStream) ((foo.obj()))); the
             * inner mi is processed by the above block. If the infer var is
             * OFFLIMIT then mark bar too. Ref itest:
             * linked.Cast.assginMultiCast().
             */
            Optional<Pack> invoke =
                    packs.findByExp(cleanedExp, heap.getPacks());
            if (invoke.isPresent() && nonNull(invoke.get().getVar())
                    && !invoke.get().getVar().is(Nature.OFFLIMIT)) {
                offlimit = false;
            }
        }

        if (nodes.is(cleanedExp, ArrayAccess.class)) {
            // is array item off limit
            Optional<Expression> valueO =
                    arrays.getValue((ArrayAccess) cleanedExp, pack, heap);
            if (valueO.isPresent()
                    && nodes.is(valueO.get(), SimpleName.class)) {
                IVar itemVar = packs.getVar(packs.findByVarName(
                        nodes.getName(valueO.get()), heap.getPacks()));
                if (nonNull(itemVar) && !itemVar.is(Nature.OFFLIMIT)) {
                    offlimit = false;
                }
            }

            // is array off limit
            Expression array = ((ArrayAccess) cleanedExp).getArray();
            Optional<Pack> arrayPackO;
            if (nodes.isName(array)) {
                arrayPackO = packs.findByVarName(nodes.getName(array),
                        heap.getPacks());
            } else {
                arrayPackO = packs.findByExp(array, heap.getPacks());
            }
            if (arrayPackO.isPresent() && nonNull(arrayPackO.get().getVar())
                    && !arrayPackO.get().getVar().is(Nature.OFFLIMIT)) {
                offlimit = false;
            }
        }

        /*
         * File a = flag ? f1 : f2; If f1 or f2 is off limit then then a is also
         * same otherwise not
         */
        if (offlimit && nodes.is(cleanedExp, ConditionalExpression.class)) {
            Expression value = expManager.getValue(cleanedExp, cleanedExp, pack,
                    false, heap);
            Optional<Pack> valuePackO;
            if (nodes.isName(value)) {
                valuePackO = packs.findByVarName(nodes.getName(value),
                        heap.getPacks());
            } else {
                valuePackO = packs.findByExp(value, heap.getPacks());
            }
            if (valuePackO.isPresent() && nonNull(valuePackO.get().getVar())
                    && !valuePackO.get().getVar().is(Nature.OFFLIMIT)) {
                offlimit = false;
            }
        }

        // The LHS var is visible if RHS var is visible.
        if (nodes.isName(cleanedExp)) {
            Optional<IVar> rhsVarO =
                    packs.findVarByName(cleanedExp, heap.getPacks());
            if (rhsVarO.isPresent() && !rhsVarO.get().is(Nature.OFFLIMIT)) {
                offlimit = false;
            }
        }

        Loader loader = heap.getLoader();
        if (loader.usedByLoader(var)) {
            offlimit = false;
        }

        if (offlimit) {
            var.addNature(Nature.OFFLIMIT);
        }
    }
}
