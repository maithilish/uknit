package org.codetab.uknit.core.make.method.process;

import static com.google.common.base.Preconditions.checkState;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.VarNotFoundException;
import org.codetab.uknit.core.make.method.Vars;
import org.codetab.uknit.core.make.method.patch.Patcher;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.node.Expressions;
import org.codetab.uknit.core.node.Methods;
import org.eclipse.jdt.core.dom.Expression;

public class Invokes {

    @Inject
    private Methods methods;
    @Inject
    private Vars vars;
    @Inject
    private Expressions expressions;
    @Inject
    private Patcher patcher;

    /**
     * Set call var. The call var of MI is expression. The var for it is known
     * only after infer var and patch is created for it in visit post process
     *
     * @param invoke
     * @param heap
     */
    public void setCallVar(final Invoke invoke, final Heap heap) {

        checkState(methods.isInvokable(invoke.getExp()));

        /*
         * Find callVar of invoke. It is empty for all types of IMC - with
         * keywords this and super or without keywords (plain call)
         */
        Optional<Expression> patchedExpO =
                patcher.getPatchedCallExp(invoke, heap);
        Optional<IVar> callVarO = Optional.empty();
        if (patchedExpO.isPresent()) {
            try {
                String name = expressions.getName(patchedExpO.get());
                callVarO = Optional.of(vars.findVarByName(name, heap));
            } catch (VarNotFoundException e) {
            }
        }
        invoke.setCallVar(callVarO);
    }

}
