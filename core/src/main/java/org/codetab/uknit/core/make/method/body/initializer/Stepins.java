package org.codetab.uknit.core.make.method.body.initializer;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.patch.Patcher;
import org.codetab.uknit.core.make.model.Heap;
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
    private Patcher patcher;
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
        } else if (nodes.is(exp, MethodInvocation.class)) {
            /*
             * List<String> list = new ArrayList<>(); String foo = list.get(0);
             * can't use list.get(0) as initializer for foo as list is locally
             * created.
             */
            packO = findCallVarPack(exp, heap);
        }

        if (packO.isPresent()) {
            return packO.get().getVar().isCreated();
        } else {
            return false;
        }
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
            Optional<Expression> patchedExpO =
                    patcher.getPatchedCallExp(invokeO.get(), heap);
            if (!methods.isInternalCall(mi, patchedExpO, heap.getMut())) {
                String name = expressions.getName(patchedExpO.get());
                callVarPackO = packs.findByVarName(name, heap.getPacks());
            }
        }
        return callVarPackO;
    }

}
