package org.codetab.uknit.core.make.method.invoke;

import static com.google.common.base.Preconditions.checkState;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.VarNotFoundException;
import org.codetab.uknit.core.make.method.Vars;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.node.Expressions;
import org.eclipse.jdt.core.dom.Expression;

public class Invokes {

    @Inject
    private Vars vars;
    @Inject
    private Expressions expressions;

    /**
     * Set call var. The call var of MI is expression and its var is known only
     * after infer var and patch is created for it in visit post process.
     *
     * @param invoke
     * @param heap
     */
    public void setCallVar(final Invoke invoke, final Heap heap) {

        checkState(expressions.isInvokable(invoke.getExp()));

        /*
         * Find callVar of invoke. It is empty for all types of IMC - with
         * keywords this and super or without keywords (plain call)
         */
        Optional<IVar> callVarO = Optional.empty();
        Optional<Expression> patchedCallExpO =
                heap.getPatcher().copyAndPatchCallExp(invoke, heap);
        if (patchedCallExpO.isPresent()) {
            try {
                String name = expressions.getName(patchedCallExpO.get());
                callVarO = Optional.of(vars.findVarByName(name, heap));
            } catch (VarNotFoundException e) {
            }
        }
        invoke.setCallVar(callVarO);
    }

}
