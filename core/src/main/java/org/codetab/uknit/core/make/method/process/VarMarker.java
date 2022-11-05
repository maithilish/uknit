package org.codetab.uknit.core.make.method.process;

import static java.util.Objects.nonNull;

import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Expressions;
import org.codetab.uknit.core.node.Methods;
import org.eclipse.jdt.core.dom.Expression;

public class VarMarker {

    @Inject
    private Expressions expressions;
    @Inject
    private Methods methods;
    @Inject
    private LinkedPack linkedPack;

    /**
     * Marks var as created when exp in any of the linkedPack of the pack is
     * creation, anon, lambda or static call.
     *
     * @param pack
     * @param packs
     */
    public void markCreation(final Pack pack, final List<Pack> packs) {
        Expression exp = pack.getExp();
        boolean created = false;
        if (nonNull(exp)) {
            /*
             * if any exp isCreation in linkedPacks then var is created.
             */
            List<Pack> linkedPacks = linkedPack.getLinkedVarPacks(pack, packs);

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
}
