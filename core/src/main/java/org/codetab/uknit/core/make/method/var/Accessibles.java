package org.codetab.uknit.core.make.method.var;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.nonNull;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Nature;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.Expression;

/**
 *
 * @author Maithilish
 *
 */
public class Accessibles {

    @Inject
    private Nodes nodes;
    @Inject
    private Packs packs;

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
     * method can't access the array. The foo is returned by the method and is
     * accessible. Whether var is accessible is set with Nature.ACCESSIBLE.
     *
     * Fields, Parameters and return vars, either real or mock, are accessible.
     *
     * The objects returned by accessible mocks are also accessible.
     *
     * The objects returned by accessible real are accessible only if it is
     * field or parameters or return var. (1st rule takes care of this) or infer
     * var.
     *
     * The objects returned by accessible real collections are accessible.
     *
     * The objects returned by accessible array (array access) are accessible.
     *
     * @param pack
     * @param heap
     */
    // REVIEW
    public void addNature(final Pack pack, final Heap heap) {
        checkNotNull(pack.getVar());

        IVar var = pack.getVar();

        boolean accessible = false;
        if (var.isField() || var.isParameter() || var.isReturnVar()) {
            accessible = true;
        }

        if (pack instanceof Invoke) {
            Invoke invoke = (Invoke) pack;
            Optional<IVar> callVarO = invoke.getCallVar();
            if (callVarO.isPresent()) {
                IVar callVar = callVarO.get();
                boolean callVarIsAccessible = !callVar.is(Nature.OFFLIMIT);
                if (callVar.isMock() && callVarIsAccessible) {
                    accessible = true;
                }
                if (!callVar.isMock() && callVarIsAccessible
                        && var.isInferVar()) {
                    accessible = true;
                }
                if (!callVar.isMock() && callVarIsAccessible
                        && callVar.is(Nature.COLLECTION)) {
                    accessible = true;
                }
            }
        }

        if (nonNull(pack.getExp())
                && nodes.is(pack.getExp(), ArrayAccess.class)) {
            Expression array = ((ArrayAccess) pack.getExp()).getArray();
            Optional<Pack> arrayPackO = packs.findByExp(array, heap.getPacks());
            if (arrayPackO.isPresent() && nonNull(arrayPackO.get().getVar())) {
                if (!arrayPackO.get().getVar().is(Nature.OFFLIMIT)) {
                    accessible = true;
                }
            }
        }

        if (!accessible) {
            var.addNature(Nature.OFFLIMIT);
        }
    }
}
