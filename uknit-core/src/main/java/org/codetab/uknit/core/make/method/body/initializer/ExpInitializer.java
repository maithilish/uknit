package org.codetab.uknit.core.make.method.body.initializer;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.exception.ResolveException;
import org.codetab.uknit.core.make.exp.Arrays;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.patch.Patcher;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar.Nature;
import org.codetab.uknit.core.make.model.Initializer;
import org.codetab.uknit.core.make.model.Initializer.Kind;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.NodeGroups;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.Expression;

/**
 * Assign initializer if exp is allowed ASTNode.
 *
 * @author Maithilish
 *
 */
class ExpInitializer implements IInitializer {

    private static final Logger LOG = LogManager.getLogger();

    @Inject
    private Patcher patcher;
    @Inject
    private AllowedExps allowedExps;
    @Inject
    private Wrappers wrappers;
    @Inject
    private Nodes nodes;
    @Inject
    private Packs packs;
    @Inject
    private Arrays arrays;
    @Inject
    private ModelFactory modelFactory;

    @Override
    public Optional<Initializer> getInitializer(final Pack pack,
            final Pack iniPack, final Heap heap) {

        Optional<Initializer> initializer = Optional.empty();

        // iniPack exp is not allowed as initializer
        if (!allowedExps.isAllowed(wrappers.unpack(iniPack.getExp()), iniPack,
                heap)) {
            return initializer;
        }

        Expression patchedExp = null;
        ASTNode parent = wrappers.stripAndGetParent(iniPack.getExp());
        if (nodes.is(parent, CastExpression.class)) {
            /*
             * Ex: int lid = (int) (person1.lid + person2.lid); here iniPack is
             * inner infix exp but initializer needs cast, so use parent cast
             * exp instead of infix as initializer exp.
             */
            Optional<Pack> pPackO =
                    packs.findByExp((Expression) parent, heap.getPacks());
            if (pPackO.isPresent()) {
                patchedExp = patcher.copyAndPatch(pPackO.get(), heap);
            } else {
                patchedExp = (Expression) parent;
            }
        } else if (nodes.is(pack.getExp(), ArrayAccess.class)
                && nodes.is(iniPack.getExp(), ArrayInitializer.class,
                        ArrayCreation.class)) {
            /*
             * ArrayAccess and ArrayCreation is not allowed as initializer,
             * instead get value returned by array access. Can't get value
             * directly from iniPack as ArrayInitializer may be multi
             * dimensional.
             */
            ArrayAccess arrayAccess = (ArrayAccess) pack.getExp();
            Optional<Expression> value =
                    arrays.getValue(arrayAccess, pack, heap);

            if (value.isPresent()
                    && allowedExps.isAllowed(value.get(), pack, heap)) {
                patchedExp = value.get();
            }
            if (value.isPresent() && nodes.isSimpleName(value.get())) {
                patchedExp = value.get();
            }
        } else {
            patchedExp = patcher.copyAndPatch(iniPack, heap);
        }
        if (nonNull(patchedExp)) {
            Initializer ini =
                    modelFactory.createInitializer(Kind.EXP, patchedExp);
            initializer = Optional.of(ini);
            LOG.debug("Var [name={}] {}", pack.getVar().getName(), ini);
        }

        return initializer;
    }
}

class AllowedExps {

    @Inject
    private NodeGroups nodeGroups;
    @Inject
    private Nodes nodes;
    @Inject
    private Packs packs;
    @Inject
    private Methods methods;
    @Inject
    private Arrays arrays;

    public boolean isAllowed(final Expression exp, final Pack pack,
            final Heap heap) {

        List<Class<? extends Expression>> clzs =
                nodeGroups.allowedAsInitializer();
        for (Class<?> clz : clzs) {
            if (nodes.is(exp, clz)) {
                return true;
            }
        }

        /*
         * Ex: boolean[] a = {Boolean.valueOf(true)}, for a[0] initializer is
         * static call Boolean.valueOf(true).
         */
        try {
            if (methods.isStaticCall(exp)) {
                return true;
            }
        } catch (ResolveException e) {
            // patched exp will not resolve
        }

        if (nodes.is(exp, ArrayAccess.class)) {
            /*
             * new String[] {"foo", "bar"}[0], as array name is null the array
             * access exp can be ini. Ref itest:
             * exp.value.ArrayAccess.arrayExpIsArrayCreation()
             */
            String arrayName =
                    arrays.getArrayName((ArrayAccess) exp, pack, heap);
            if (isNull(arrayName)) {
                return true;
            }

            /*
             * If array is OFFLIMIT then not allowed. Ex: String name =
             * array[0], if array is parameter then it is accessible (not
             * OFFLIMIT) and it is allowed, but if array is defined in the
             * method then it is OFFLIMIT. Ref itest:
             * linked.Assign.assignArrayAccess()
             */
            Optional<Pack> arrayPackO =
                    packs.findByVarName(arrayName, heap.getPacks());
            if (arrayPackO.isPresent() && nonNull(arrayPackO.get().getVar())) {
                return !arrayPackO.get().getVar().is(Nature.OFFLIMIT);
            }
        }

        return false;
    }
}
