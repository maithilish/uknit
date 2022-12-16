package org.codetab.uknit.core.make.method.body.initializer;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

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

    public Optional<Expression> getInitializer(final IVar var,
            final Heap heap) {
        return getInitializer(var, null, heap);
    }

    /**
     * The Pack maps Expression to an IVar. This method gets initializer for
     * following combinations.
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
    private Optional<Expression> getInitializer(final IVar var,
            final Expression initExp, final Heap heap) {

        checkState(nonNull(var) || nonNull(initExp));

        Optional<Pack> packO;
        if (isNull(var)) {
            packO = packs.findByExp(initExp, heap.getPacks());
        } else {
            packO = packs.findByVarName(var.getName(), heap.getPacks());
        }

        // by default, the pack's exp is initializer
        Expression exp = packO.get().getExp();
        if (isNull(exp)) {
            return Optional.empty();
        }
        /**
         * Casted initializer var type is already changed in Packer.packVars(),
         * so discard the cast from initializer. <code>
         * Object o = new Foo();
         * Foo foo = (Foo) o;
         * </code> the type o is set to Foo instead of Object.
         */
        if (nodes.is(exp, CastExpression.class)) {
            CastExpression ce = nodes.as(exp, CastExpression.class);
            if (nodes.is(ce.getExpression(), SimpleName.class)) {
                exp = ce.getExpression();
            }
        }

        /**
         * The default initializer is pack.exp but pack's exp may refer some
         * other pack before it. Ex: <code>
         * int[] array = new int[1];
         * array[0] = 10;
         * int foo = array[0];
         * </code>. Pack [var:foo, exp: array[0]] refers Pack [exp: 10, leftExp:
         * array[10]]. The effective initializer is 10.
         *
         * From the packs (headList) that comes before the pack, find the last
         * pack (stream reduce) whose leftExp matches the pack's exp. The exp
         * from different stmt are not equal so as a workaround compare their
         * string representation. The initializer for foo is set to 10 as
         * leftExpPack is [leftExp: array[0], exp: 10]. Ref itest:
         * array.Access.assignAccessPrimitive().
         */
        List<Pack> headList = packs.headList(packO.get(), heap.getPacks());
        final String expString = exp.toString();
        Optional<Pack> leftExpPackO = headList.stream().filter(p -> {
            return p.getLeftExp().isPresent()
                    && p.getLeftExp().get().toString().equals(expString);
        }).reduce((f, s) -> s);

        if (leftExpPackO.isPresent()) {

            exp = leftExpPackO.get().getExp();

            /**
             * Again exp may refer earlier pack, try to recursively find
             * initializer if found use its exp as initializer else fall back to
             * the exp. Ex: <code>
             * array[0] = 10;
             * array[0] = array[1];
             * int foo = array[0];</code>
             */
            Optional<Expression> expO = getInitializer(null, exp, heap);
            if (expO.isPresent()) {
                exp = expO.get();
            }
        }

        return Optional.ofNullable(exp);
    }

    /**
     * Whether exp is allowed as initializer.
     *
     * @param exp
     * @return
     */
    public boolean isAllowed(final Expression exp, final Heap heap) {
        List<Class<? extends Expression>> clzs =
                nodeGroups.allowedAsInitializer();
        for (Class<?> clz : clzs) {
            if (nodes.is(exp, clz)) {
                return true;
            }
        }
        if (nodes.is(exp, MethodInvocation.class)) {
            MethodInvocation mi = nodes.as(exp, MethodInvocation.class);
            // mi can't be internal
            if (methods.isInternalCall(mi,
                    Optional.ofNullable(mi.getExpression()), heap.getMut())) {
                return false;
            }
            /*
             * If var of expression of mi is created then mi can't be
             * initializer. Ex: staticGetSuperField().getRealFoo().getId(); the
             * var returned by exp staticGetSuperField().getRealFoo() is real
             * and the mi can't be initializer. Ref itest:
             * superclass.StaticCall.returnStaticSuperField()
             */
            Optional<IVar> varO =
                    packs.findVarByExp(mi.getExpression(), heap.getPacks());
            if (varO.isPresent() && varO.get().isCreated()) {
                return false;
            }
            if (methods.isStaticCall(mi)) {
                return true;
            }
        }
        return false;

    }

    /**
     * Whether MI is allowed as initializer.
     *
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

                /*
                 * The varIsReal indicates whether var on which MI is called is
                 * real and returnIsReal indicates whether the var returned by
                 * the invoke is real. Ex: String foo = list.get(0), invoke var
                 * is list and its return is assigned to var foo. The list (var)
                 * is real and foo (its return var) is also real.
                 */
                boolean varIsReal =
                        !invokeO.get().getReturnType().get().isMock();
                boolean returnIsMock = var.isMock();
                boolean returnIsReal = !var.isMock();

                // real returns real - mi can be initializer
                if (varIsReal && returnIsReal) {
                    miAllowedAsInitializer = true;
                }

                // real returns mock - mi can be initializer
                if (varIsReal && returnIsMock) {
                    miAllowedAsInitializer = true;
                }

                /*
                 * If invoke var is collection then mi can't be initializer. Ex:
                 * String name = listHolder.getList().get(0); Invoke [var: list]
                 * returned by getList() is a collection and list.get(0) can't
                 * be initializer for the var name. Ref itest:
                 * load.Lists.getAssign(listHolder).
                 */
                Object isCollection =
                        invokeO.get().getVar().getProperty("isCollection");
                if (varIsReal && nonNull(isCollection)
                        && (boolean) isCollection) {
                    miAllowedAsInitializer = false;
                }

                // mock returns mock - false, mock returns real - false
            }
        }
        return miAllowedAsInitializer;
    }

}
