package org.codetab.uknit.core.make.method.body.initializer;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.exp.Arrays;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.patch.Patcher;
import org.codetab.uknit.core.make.method.var.linked.LinkedPack;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.SimpleName;

/**
 * Find appropriate initializer defined in source.
 *
 * @author Maithilish
 *
 */
class InitialzerFinder {

    @Inject
    private Nodes nodes;
    @Inject
    private Packs packs;
    @Inject
    private Wrappers wrappers;
    @Inject
    private Patcher patcher;
    // PKGBREAKER - cross access
    @Inject
    private LinkedPack linkedPack;
    @Inject
    private Arrays arrays;

    public Optional<Pack> findInitializerPack(final Pack pack,
            final Heap heap) {

        checkNotNull(pack.getVar());

        Optional<Pack> iniPack = Optional.empty();

        if (nonNull(pack.getExp())
                && nodes.isName(wrappers.unpack(pack.getExp()))) {
            // if name then pack itself is the initializer pack
            iniPack = Optional.of(pack);
        } else {
            IVar var = pack.getVar();
            Optional<Expression> iniExpO = findInitializer(var, null, heap);
            if (iniExpO.isPresent()) {
                iniPack = packs.findByExp(wrappers.unpack(iniExpO.get()),
                        heap.getPacks());
            }
        }
        return iniPack;
    }

    public Optional<Expression> findInitializer(final IVar var,
            final Heap heap) {
        return findInitializer(var, null, heap);
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
    private Optional<Expression> findInitializer(final IVar var,
            final Expression initExp, final Heap heap) {

        checkState(nonNull(var) || nonNull(initExp));

        Optional<Pack> packO;
        if (isNull(var)) {
            packO = packs.findByExp(initExp, heap.getPacks());
        } else {
            packO = packs.findByVarName(var.getName(), heap.getPacks());
        }

        // by default, the pack's exp is initializer
        Expression exp = null;
        if (packO.get().hasExp()) {
            exp = packO.get().getExp();
        } else {
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
            Optional<Expression> expO = findInitializer(null, exp, heap);
            if (expO.isPresent()) {
                exp = expO.get();
            }
        }

        /**
         * ArrayAccess is not allowed as initializer, instead get iniPack
         * returned by array name.<code>
         *  int[] array = { 10 };
         *  int foo = array[0];
         *  </code> for int foo the initializer is 10 i.e. first item in
         * ArrayInitializer. Ref itest:
         * initializer.Stepin.accessArrayInitializer()
         */
        if (exp.equals(packO.get().getExp())
                && nodes.is(exp, ArrayAccess.class)) {
            ArrayAccess aa =
                    (ArrayAccess) patcher.copyAndPatch(packO.get(), heap);
            String arrayName = arrays.getArrayName(aa, heap);
            Optional<Pack> aPackO =
                    packs.findByVarName(arrayName, heap.getPacks());
            if (aPackO.isPresent()) {
                List<Pack> linkedPacks =
                        linkedPack.getLinkedVarPacks(aPackO.get(), heap);
                for (Pack linkPack : linkedPacks) {
                    if (linkPack.hasExp() && nodes.is(linkPack.getExp(),
                            ArrayInitializer.class, ArrayCreation.class)) {
                        aPackO = Optional.of(linkPack);
                    }
                }

                /*
                 * Only elements in the ArrayInitializer can be initializer but
                 * not ArrayCreation. Ex: Allowed int[] array = { 10 }, not
                 * allowed int[] array = new int[10]; Ref itest:
                 * initializer.Stepin.accessArrayCreation()
                 */
                if (aPackO.get().hasExp()) {
                    exp = aPackO.get().getExp();
                }
            }
        }
        return Optional.ofNullable(exp);
    }
}
