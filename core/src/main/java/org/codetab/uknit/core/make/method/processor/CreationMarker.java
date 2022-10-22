package org.codetab.uknit.core.make.method.processor;

import static java.util.Objects.nonNull;

import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Expressions;
import org.codetab.uknit.core.node.Methods;
import org.eclipse.jdt.core.dom.Expression;

public class CreationMarker {

    @Inject
    private Expressions expressions;
    @Inject
    private Methods methods;

    public void markCreation(final List<Pack> packs) {
        for (Pack pack : packs) {

            Expression exp = pack.getExp();

            boolean created = false;
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
                // FIXME Pack - IMC top name left out, see VarStager localVar.
                if (nonNull(pack.getVar())) {
                    pack.getVar().setCreated(created);
                }
            }

        }
    }
}
