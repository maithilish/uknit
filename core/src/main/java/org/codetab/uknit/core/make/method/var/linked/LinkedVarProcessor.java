package org.codetab.uknit.core.make.method.var.linked;

import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.Vars;
import org.codetab.uknit.core.make.method.patch.Patcher;
import org.codetab.uknit.core.make.method.patch.ServiceLoader;
import org.codetab.uknit.core.make.method.patch.service.PatchService;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Nature;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Expressions;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Resolver;
import org.codetab.uknit.core.node.Types;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Type;

public class LinkedVarProcessor {

    @Inject
    private Expressions expressions;
    @Inject
    private Methods methods;
    @Inject
    private LinkedPack linkedPack;
    @Inject
    private Nodes nodes;
    @Inject
    private Packs packs;
    @Inject
    private Resolver resolver;
    @Inject
    private CastPropagator castPropagator;
    @Inject
    private Wrappers wrappers;
    @Inject
    private Vars vars;
    @Inject
    private ServiceLoader serviceLoader;
    @Inject
    private Patcher patcher;

    /**
     * Marks var as created if exp is created.
     *
     * @param pack
     * @param packList
     */
    public void markCreation(final Pack pack, final Heap heap) {
        Expression exp = pack.getExp();

        if (expressions.isCreation(exp)) {
            pack.getVar().setCreated(true);
        }
    }

    /**
     * Marks var as created when exp in any of the linkedPack is created or
     * realish.
     *
     * @param pack
     * @param packList
     */
    // REVIEW - what about linked anon vars
    public void propagateCreationForLinkedVars(final Pack pack,
            final Heap heap) {
        Expression exp = pack.getExp();
        boolean realish = false;

        final List<Pack> packList = heap.getPacks();

        List<Pack> linkedPacks = linkedPack.getLinkedVarPacks(pack, heap);

        for (Pack linkPack : linkedPacks) {
            exp = linkPack.getExp();
            if (nonNull(exp)) {
                // if linkPack var is created, then pack var is realish
                if (nonNull(linkPack.getVar())
                        && linkPack.getVar().isCreated()) {
                    realish = true;
                }
                /*
                 * In a chained call if the leading exp is created or realish,
                 * then pack var is realish.
                 *
                 * Ex: super.staticGetSuperField().getRealJobInfo(), the
                 * staticGetSuperField() returns var payload so top name is
                 * payload and it is realish, so the pack var jobInfo is also
                 * realish.
                 */
                Expression patchedExp = patcher.copyAndPatch(linkPack, heap);
                Optional<String> topVarNameO =
                        methods.getTopVarName(patchedExp);
                if (topVarNameO.isPresent() && vars.isCreated(topVarNameO.get(),
                        packs.asVars(packList))) {
                    realish = true;
                }
            }
        }

        if (realish) {
            pack.getVar().addNature(Nature.REALISH);
        }
    }

    /**
     * If invokes call var is effectively real, add Nature.REALISH to invoke
     * var.
     *
     * @param invoke
     * @param heap
     */
    public void propagateRealish(final Invoke invoke, final Heap heap) {
        Optional<Expression> callExpO =
                patcher.copyAndPatchCallExp(invoke, heap);

        if (callExpO.isPresent() && nodes.isName(callExpO.get())) {
            Optional<Pack> callPackO = packs.findByVarName(
                    nodes.getName(callExpO.get()), heap.getPacks());
            if (callPackO.isPresent() && nonNull(callPackO.get().getVar())
                    && callPackO.get().getVar().isEffectivelyReal()) {
                invoke.getVar().addNature(Nature.REALISH);
            }
        }
    }

    /**
     * Propagates the cast type to the linked vars. Ex: <code>
     *
     * Object obj = foo.obj();
     * Locale locale = (Locale) obj;
     * </code> The return type of foo.obj() is object which is casted to Locale.
     * The 2nd pack type is propagated to 1st pack.
     *
     * If first stmt is Object obj = new Locale(); then it is possible to derive
     * the return type as Locale but if it is MI foo.obj() it is not possible as
     * return type of MI is Object. The propogateCastType() can set the proper
     * type for 1st stmt in such cases.
     *
     * @param pack
     * @param packList
     */
    public void propogateCastType(final Pack pack, final Heap heap) {
        if (nonNull(pack.getExp())) {
            Expression exp = pack.getExp();
            IVar var = pack.getVar();

            List<Pack> packList = heap.getPacks();

            if (nonNull(var) && nonNull(exp) && expressions.isCastedExp(exp)) {
                /*
                 * propagate exp cast type to linked vars.
                 */
                Type type = var.getType();
                castPropagator.propogate(pack, pack, type, heap);
            } else if (nonNull(exp)) {
                /*
                 * propagate cast of exp's exps to linked vars.
                 */
                PatchService patchService = serviceLoader.loadService(exp);
                List<Expression> exps = patchService.getExps(exp);
                for (Expression eexp : exps) {
                    if (nonNull(var) && nonNull(eexp)
                            && expressions.isCastedExp(eexp)) {

                        Expression stripedExp = wrappers.unpack(eexp);
                        // find pack by exp else by var name
                        Optional<Pack> ePackO =
                                packs.findByExp(stripedExp, packList);
                        if (ePackO.isPresent()) {
                            if (nonNull(ePackO.get().getVar())) {
                                Type type = ePackO.get().getVar().getType();
                                castPropagator.propogate(pack, ePackO.get(),
                                        type, heap);
                            }
                        } else if (nodes.isName(stripedExp)) {
                            ePackO = packs.findByVarName(
                                    nodes.getName(stripedExp), packList);
                            if (ePackO.isPresent()) {
                                Type type = resolver.getReturnType(eexp);
                                castPropagator.propogate(pack, ePackO.get(),
                                        type, heap);
                            }
                        }
                    }
                }
            }

        }
    }
}

/**
 * Manages link pack cast propagation.
 *
 * @author Maithilish
 *
 */
class CastPropagator {

    @Inject
    private LinkedPack linkedPack;
    @Inject
    private Types types;

    /**
     * Selectively propagates the pack var's type to link vars.
     *
     * Ex: Dog is subclass of Pet which is subclass of Object and MUT has
     * following stmts: Object obj; Pet pet = (Pet) obj; Dog dog = (Dog) obj;
     * When pet pack is propagated the obj type is set to Pet and then on dog
     * pack its type is changed to Dog. So that Dog obj satisfies all casts.
     *
     * However if order is Object obj; Dog dog = (Dog) obj; Pet pet = (Pet) obj;
     * then when dog pack is propagated the obj type is set to Dog and then on
     * pet pack its type is not changed as downgrading the type to Pet will make
     * the statement Dog dog = (Dog) obj; invalid. So, higher Dog is retained.
     * The isAssignble() ensure only upgrading but not downgrading. In other
     * words, the max of cast types is assigned to link vars.
     *
     * @param pack
     * @param packList
     * @param ePack
     * @param targetType
     */
    public void propogate(final Pack pack, final Pack ePack,
            final Type targetType, final Heap heap) {
        List<Pack> linkedPacks = linkedPack.getLinkedVarPacks(ePack, heap);
        for (Pack linkPack : linkedPacks) {
            if (nonNull(linkPack.getVar())) {
                IVar linkVar = linkPack.getVar();
                /*
                 * String (targetType) is subclass of Object (linkType), change
                 * link var type from Object to String.
                 *
                 * Object (targetType) is not subclass of String (linkType),
                 * don't change link var type from String to Object;
                 */
                if (isSubclass(targetType, linkVar.getType())) {
                    linkPack.getVar().setType(targetType);
                    linkPack.getVar()
                            .setTypeBinding(pack.getVar().getTypeBinding());
                }
            }
        }
    }

    /**
     * Is targetType is subclass of type.
     *
     * String (targetType) is subclass Object (type)
     *
     * Object (targetType) is not subclass String (type)
     *
     * Useful to allot highest possible type to a var. Ex: Dog is subclass of
     * Pet which is subclass of Object. Suppose we want to assign highest
     * possible type to a link var Object obj. If its present type is Object
     * then we can assign Dog or Pet; if present type is Pet then we can assign
     * Dog but not Object; if present type is Dog then we can't assign either
     * Pet or Object.
     *
     * Note: it is a best effort method and when it is not possible to ascertain
     * the assignability, true is returned so that calling operation goes
     * through.
     *
     * @param pack
     * @param linkPack
     * @return
     */
    private boolean isSubclass(final Type targetType, final Type type) {
        try {
            String targetClzName = types.getClzName(targetType);
            String typeClzName = types.getClzName(type);

            Class<?> targetClz = Class.forName(targetClzName);
            Class<?> typeClz = Class.forName(typeClzName);

            // is targetClz is subclass of typeClz
            boolean isSubClass = typeClz.isAssignableFrom(targetClz);

            return isSubClass;
        } catch (ClassNotFoundException | NullPointerException e) {
        }
        return true;
    }
}
