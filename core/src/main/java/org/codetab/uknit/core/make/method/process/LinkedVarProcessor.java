package org.codetab.uknit.core.make.method.process;

import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.patch.Patchers;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Expressions;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Resolver;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
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
    private Patchers patchers;
    @Inject
    private Packs packs;
    @Inject
    private Resolver resolver;

    /**
     * Marks var as created when exp in any of the linkedPack of the pack is
     * creation, anon, lambda or static call.
     *
     * @param pack
     * @param packList
     */
    public void markAndPropagateCreation(final Pack pack,
            final List<Pack> packList) {
        Expression exp = pack.getExp();
        boolean created = false;
        if (nonNull(exp)) {
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
                }
            }

            // FIXME Pack - IMC top name left out, see VarStager localVar.
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
            if (nonNull(exp) && nonNull(var)
                    && (nodes.is(exp, CastExpression.class) || nodes
                            .is(exp.getParent(), CastExpression.class))) {
                Type type = var.getType();
                List<Pack> linkedPacks =
                        linkedPack.getLinkedVarPacks(pack, packList);
                for (Pack linkPack : linkedPacks) {
                    if (nonNull(linkPack.getVar())) {
                        linkPack.getVar().setType(type);
                    }
                }
            } else {
                // REVIEW - casted exps
                List<Expression> exps = patchers.getExps(exp);
                for (Expression eexp : exps) {
                    if (nodes.is(eexp, ParenthesizedExpression.class)) {
                        eexp = nodes.as(eexp, ParenthesizedExpression.class)
                                .getExpression();
                    }
                    if (nonNull(eexp) && nonNull(var)
                            && (nodes.is(eexp, CastExpression.class)
                                    || nodes.is(eexp.getParent(),
                                            CastExpression.class))) {
                        Expression stripedEExp =
                                expressions.stripWraperExpression(eexp);
                        Optional<Pack> ePackO =
                                packs.findByExp(stripedEExp, packList);
                        if (ePackO.isPresent()) {
                            if (nonNull(ePackO.get().getVar())) {
                                Type type = ePackO.get().getVar().getType();
                                List<Pack> linkedPacks =
                                        linkedPack.getLinkedVarPacks(
                                                ePackO.get(), packList);
                                for (Pack linkPack : linkedPacks) {
                                    if (nonNull(linkPack.getVar())) {
                                        linkPack.getVar().setType(type);
                                    }
                                }
                            }
                        } else if (nodes.isName(stripedEExp)) {
                            ePackO = packs.findByVarName(
                                    nodes.getName(stripedEExp), packList);
                            if (ePackO.isPresent()) {
                                Type type = resolver.getReturnType(eexp);
                                List<Pack> linkedPacks =
                                        linkedPack.getLinkedVarPacks(
                                                ePackO.get(), packList);
                                for (Pack linkPack : linkedPacks) {
                                    if (nonNull(linkPack.getVar())) {
                                        linkPack.getVar().setType(type);
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}
