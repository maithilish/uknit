package org.codetab.uknit.core.make.method.body.initializer;

import static java.util.Objects.isNull;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.NodeGroups;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;

/**
 * Initializer defined in source.
 * @author Maithilish
 *
 */
class DefinedInitialzer {
    @Inject
    private Nodes nodes;
    @Inject
    private Packs packs;
    @Inject
    private Methods methods;
    @Inject
    private NodeGroups nodeGroups;

    public boolean isAllowed(final Expression exp) {
        List<Class<? extends Expression>> clzs =
                nodeGroups.allowedAsInitializer();
        for (Class<?> clz : clzs) {
            if (nodes.is(exp, clz)) {
                return true;
            }
        }
        if (nodes.is(exp, MethodInvocation.class) && methods
                .isStaticCall(nodes.as(exp, MethodInvocation.class))) {
            return true;
        }
        return false;
    }

    /**
     * Whether MI is allowed as initializer.
     * <p>
     * mock returns mock - false
     * <p>
     * mock returns real - false
     * <p>
     * real returns mock - true (false if real collection returns mock)
     * <p>
     * real returns real - true
     *
     * Ex: file.getName().toLowercase(), The 1st call getName() is mock returns
     * real (infer var apple) so don't use MI as initializer and 2nd call
     * toLowercase() is real returns real (infer var grape) for which use MI as
     * initializer. Output is,
     *
     * <code>
     * String apple = "Foo";
     * String grape = apple.toLowercase();
     * </code>
     *
     * @param var
     * @param exp
     * @param heap
     * @return
     */
    public boolean isMIAllowed(final IVar var, final Expression exp,
            final Heap heap) {
        boolean miAllowedAsInitializer = false;
        if (nodes.is(exp, MethodInvocation.class)) {
            MethodInvocation mi = nodes.as(exp, MethodInvocation.class);
            Expression miExp = mi.getExpression();
            Optional<Invoke> invokeO =
                    packs.findInvokeByExp(miExp, heap.getPacks());
            if (invokeO.isPresent()
                    && invokeO.get().getReturnType().isPresent()) {
                boolean varIsMock =
                        invokeO.get().getReturnType().get().isMock();
                boolean returnIsMock = !var.isMock();
                // real returns real - mi can be initializer
                if (!varIsMock && !returnIsMock) {
                    miAllowedAsInitializer = true;
                }
                /*
                 * real returns mock - mi can be initializer
                 *
                 * TODO H - if collection type which holds and returns mock then
                 * mi can't be initializer, enable this after inserter refactor.
                 */
                if (!varIsMock && returnIsMock) {
                    miAllowedAsInitializer = true;
                }

                // mock returns mock - false, mock returns real - false
            }
        }
        return miAllowedAsInitializer;
    }

    /**
     * The ExpVar maps Expression to an IVar, IVar to Expression or Expression
     * to Expression. This method gets initializer for following combinations.
     *
     * var = var, example: x = y
     *
     * var = exp, example: x = a[0]
     *
     * exp = var, example: a[0] = i;
     *
     * exp = exp, example: a[0] = a[1]
     *
     * @param var
     * @param heap
     * @return
     */
    public Optional<Expression> getInitializer(final IVar var,
            final Heap heap) {
        Optional<Pack> pack =
                packs.findByVarName(var.getName(), heap.getPacks());
        Expression iniExp = pack.get().getExp();
        if (isNull(iniExp)) {
            return Optional.empty();
        }
        /**
         * Casted initializer var type is already changed in Packer.packVars(),
         * so discard the cast from initializer. <code>
         * Object o = new Foo();
         * Foo foo = (Foo) o;
         * </code> the type o is set to Foo instead of Object.
         */
        if (nodes.is(iniExp, CastExpression.class)) {
            CastExpression ce = nodes.as(iniExp, CastExpression.class);
            if (nodes.is(ce.getExpression(), SimpleName.class)) {
                iniExp = ce.getExpression();
            }
        }
        return Optional.ofNullable(iniExp);
    }
}
