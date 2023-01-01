package org.codetab.uknit.core.make.method.body.initializer;

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
import org.codetab.uknit.core.node.Expressions;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;

public class Stepins {

    @Inject
    private Nodes nodes;
    @Inject
    private Packs packs;
    @Inject
    private Expressions expressions;
    @Inject
    private Methods methods;

    /**
     * Exp is a step in if unable to use it in test method. Ref itest:
     * initializer.Stepin.
     *
     * @param exp
     * @param heap
     * @return
     */
    public boolean isStepin(final Expression exp, final Heap heap) {

        checkNotNull(exp);

        Optional<Pack> packO = Optional.empty();
        if (nodes.is(exp, ArrayAccess.class)) {
            /*
             * int[] array = new int[2]; int foo = array[0]; can't use array[0]
             * as initializer for foo as array is locally created.
             */
            ArrayAccess aa = nodes.as(exp, ArrayAccess.class);
            String name = expressions.getName(aa.getArray());
            packO = packs.findByVarName(name, heap.getPacks());
            if (packO.isPresent()) {
                IVar var = packO.get().getVar();
                return var.isCreated() || var.is(Nature.REALISH);
            } else {
                return false;
            }
        } else if (nodes.is(exp, MethodInvocation.class)) {
            /*
             * List<String> list = new ArrayList<>(); String foo = list.get(0);
             * can't use list.get(0) as initializer for foo as list is locally
             * created.
             *
             * If var is primitive then assign sensible default, so no STEPIN.
             * Ex itest: superclass.StaticCall.assignStaticSuperField
             */

            Optional<Pack> varPackO = packs.findByExp(exp, heap.getPacks());
            if (varPackO.isPresent() && nonNull(varPackO.get().getVar())) {
                IVar var = varPackO.get().getVar();
                if (var.getType().isPrimitiveType()) {
                    return false;
                }
            }

            packO = findCallVarPack(exp, heap);
            if (packO.isPresent() && varPackO.isPresent()) {
                IVar callVar = packO.get().getVar();
                IVar var = varPackO.get().getVar();
                // return callVar.isCreated() || callVar.is(Nature.REALISH);
                if ((callVar.isCreated() || callVar.is(Nature.REALISH)
                        || !callVar.isMock())
                        && (var.isCreated() || var.is(Nature.REALISH)
                                || !var.isMock())) {
                    return false;
                } else {
                    return callVar.isCreated() || callVar.is(Nature.REALISH);
                }
            }
        }

        return false;

    }

    /**
     * Get pack for the method invoke call var. Ex: Foo foo; foo.bar(); for MI
     * foo.bar() the call var is foo and first stmt is its pack.
     *
     * @param exp
     * @param heap
     * @return
     */
    private Optional<Pack> findCallVarPack(final Expression exp,
            final Heap heap) {

        Optional<Pack> callVarPackO = Optional.empty();

        MethodInvocation mi = nodes.as(exp, MethodInvocation.class);
        Optional<Invoke> invokeO = packs.findInvokeByExp(mi, heap.getPacks());
        if (invokeO.isPresent()) {
            /*
             * if var is renamed then use renamed var to known whether method
             * invoked on created var. Ref itest: internal.CallAndAssign.
             * callAndAssignToDifferentNameNullInitialized() where
             * webClient.getOptions() in configure() is invoked on renamed var
             * webClient2.
             */
            Optional<Expression> patchedCallExpO =
                    heap.getPatcher().copyAndPatchCallExp(invokeO.get(), heap);
            if (!methods.isInternalCall(mi, patchedCallExpO, heap.getMut())) {
                String name = expressions.getName(patchedCallExpO.get());
                callVarPackO = packs.findByVarName(name, heap.getPacks());
            }
        }
        return callVarPackO;
    }

}