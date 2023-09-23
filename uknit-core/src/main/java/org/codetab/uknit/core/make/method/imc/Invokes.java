package org.codetab.uknit.core.make.method.imc;

import static java.util.Objects.isNull;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.VarNotFoundException;
import org.codetab.uknit.core.make.method.Vars;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.Pack.Nature;
import org.codetab.uknit.core.node.Expressions;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.TypeLiteral;

public class Invokes {

    @Inject
    private Vars vars;
    @Inject
    private Nodes nodes;
    @Inject
    private Expressions expressions;

    /**
     * Get call var of MIs in IM. The processEnhancedFor() depends on call var
     * to load collections.
     *
     * TODO L - Only collection loader uses this. The method.invoke.Invokes has
     * a simpler version. Explore whether can be simplified.
     *
     * @param invoke
     * @param heap
     * @return
     */
    public Optional<IVar> getCallVar(final Invoke invoke, final Heap heap) {
        Optional<IVar> callVarO = Optional.empty();
        Optional<Expression> patchedCallExpO =
                heap.getPatcher().copyAndPatchCallExp(invoke, heap);
        if (patchedCallExpO.isPresent()) {
            // if var for name is not found then find var for old name
            Expression pCallExp = patchedCallExpO.get();
            String name = expressions.getName(pCallExp);
            if (invoke.is(Nature.STATIC_CALL)) {
                nodes.doNothing();
            } else if (isNull(name)) {
                /*
                 * this.foo() or String.class.cast(source), ignore
                 * ThisExpression and TypeLiteral as there is no call var in
                 * method invoke.
                 */
                if (nodes.is(pCallExp, ClassInstanceCreation.class,
                        ArrayAccess.class)) {
                    nodes.doNothing();
                } else if (!nodes.is(patchedCallExpO.get(), TypeLiteral.class,
                        ThisExpression.class)) {
                    throw new VarNotFoundException(nodes.exMessage(
                            "call var name is null", patchedCallExpO.get()));
                }
            } else if (nodes.is(patchedCallExpO.get(), QualifiedName.class)) {
                // Ref itest: internal.CutArg
                Name q = ((QualifiedName) patchedCallExpO.get()).getQualifier();
                if (nodes.is(q, SimpleName.class)) {
                    name = nodes.getName(q);
                    try {
                        callVarO = Optional.of(vars.findVarByName(name, heap));
                    } catch (VarNotFoundException e) {
                        try {
                            callVarO = Optional
                                    .of(vars.findVarByOldName(name, heap));
                        } catch (VarNotFoundException e1) {
                            /*
                             * Ref itest imc.ret.Invoke.invokeOnFieldAccess()
                             * Ex: Zoo.CURRENT_USER.equals("foo");
                             */
                            // ignore
                        }
                    }
                }
            } else {
                try {
                    callVarO = Optional.of(vars.findVarByName(name, heap));
                } catch (VarNotFoundException e) {
                    callVarO = Optional.of(vars.findVarByOldName(name, heap));
                }
            }
        }
        return callVarO;
    }
}
