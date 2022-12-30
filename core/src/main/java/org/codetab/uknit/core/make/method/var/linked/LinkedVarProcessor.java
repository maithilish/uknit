package org.codetab.uknit.core.make.method.var.linked;

import static java.util.Objects.isNull;
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
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Expressions;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Resolver;
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
     * Marks var as created when exp in any of the linkedPack of the pack is
     * creation, anon, lambda or static call.
     *
     * @param pack
     * @param packList
     */
    public void markAndPropagateCreation(final Pack pack, final Heap heap) {
        Expression exp = pack.getExp();
        boolean created = false;
        if (nonNull(exp)) {
            final List<Pack> packList = heap.getPacks();

            /*
             * if any exp isCreation in linkedPacks then var is created.
             */
            List<Pack> linkedPacks =
                    linkedPack.getLinkedVarPacks(pack, packList);

            for (Pack linkPack : linkedPacks) {
                exp = linkPack.getExp();
                if (nonNull(exp)) {
                    if (expressions.isCreation(exp)) {
                        created = true;
                    }
                    if (expressions.isAnonOrLambda(exp)) {
                        created = true;
                    }
                    if (methods.isStaticCall(exp)) {
                        created = true;
                    }

                    /*
                     * In a chained call whether the leading exp is created. In
                     * the patchedExp leading exp is replaced with name. Ex:
                     * super.staticGetSuperField().getRealJobInfo(), the
                     * staticGetSuperField() returns var payload so patch exp is
                     * payload.getRealJobInfo() and top name is payload.
                     */
                    Expression patchedExp =
                            patcher.copyAndPatch(linkPack, heap);
                    Optional<String> topVarNameO =
                            methods.getTopVarName(patchedExp);
                    if (topVarNameO.isPresent() && vars.isCreated(
                            topVarNameO.get(), packs.asVars(packList))) {
                        created = true;
                    }
                }
            }

            if (nonNull(pack.getVar())) {
                pack.getVar().setCreated(created);
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
    public void propogateCastType(final Pack pack, final List<Pack> packList) {
        if (nonNull(pack.getExp())) {
            Expression exp = pack.getExp();
            IVar var = pack.getVar();

            if (nonNull(var) && nonNull(exp) && expressions.isCastedExp(exp)) {
                /*
                 * propagate exp cast type to linked vars.
                 */
                Type type = var.getType();
                castPropagator.propogate(pack, packList, pack, type);
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
                                castPropagator.propogate(pack, packList,
                                        ePackO.get(), type);
                            }
                        } else if (nodes.isName(stripedExp)) {
                            ePackO = packs.findByVarName(
                                    nodes.getName(stripedExp), packList);
                            if (ePackO.isPresent()) {
                                Type type = resolver.getReturnType(eexp);
                                castPropagator.propogate(pack, packList,
                                        ePackO.get(), type);
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
     * @param type
     */
    public void propogate(final Pack pack, final List<Pack> packList,
            final Pack ePack, final Type type) {
        List<Pack> linkedPacks = linkedPack.getLinkedVarPacks(ePack, packList);
        for (Pack linkPack : linkedPacks) {
            if (nonNull(linkPack.getVar()) && isAssignable(pack, linkPack)) {
                linkPack.getVar().setType(type);
                linkPack.getVar()
                        .setTypeBinding(pack.getVar().getTypeBinding());
            }
        }
    }

    /**
     * Is pack var is assignable to link var.
     *
     * Useful allot highest possible type to a var. Ex: Dog is subclass of Pet
     * which is subclass of Object. Suppose we want to assign highest possible
     * type to a link var Object obj. If its present type is Object then we can
     * assign Dog or Pet; if present type is Pet then we can assign Dog but not
     * Object; if present type is Dog then we can't assign either Pet or Object.
     *
     * Note: it is a best effort method and when it is not possible to ascertain
     * the assignability, true is returned so that calling operation goes
     * through.
     *
     * @param pack
     * @param linkPack
     * @return
     */
    public boolean isAssignable(final Pack pack, final Pack linkPack) {

        IVar packVar = pack.getVar();
        IVar linkVar = linkPack.getVar();

        if ((nonNull(packVar) && isNull(packVar.getTypeBinding()))
                || (nonNull(linkVar) && isNull(linkVar.getTypeBinding()))) {
            return true;
        }

        try {
            Class<?> packVarClass =
                    Class.forName(packVar.getTypeBinding().getBinaryName());
            Class<?> linkVarClass =
                    Class.forName(linkVar.getTypeBinding().getBinaryName());

            /*
             * is pack var is assignable to link var. Ex: link var obj (Object)
             * and pack var date (Date).
             *
             * if obj is link and date is pack var returns true as obj = date;
             *
             * if date is link and obj is pack var returns false as date = obj
             * is not allowed
             */
            return linkVarClass.isAssignableFrom(packVarClass);

        } catch (ClassNotFoundException e) {
        }

        return true;
    }

}
