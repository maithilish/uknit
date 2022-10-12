package org.codetab.uknit.core.make.method.visit;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.ExpVar;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Patch;
import org.codetab.uknit.core.make.model.ReturnVar;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Resolver;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.Type;

public class ReturnProcessor {

    @Inject
    private Nodes nodes;
    @Inject
    private ModelFactory modelFactory;
    @Inject
    private Resolver resolver;

    public Optional<IVar> process(final ReturnStatement rs, final Heap heap) {

        ReturnVar returnVar = null;

        Expression exp = rs.getExpression();
        if (isNull(exp)) {
            return Optional.ofNullable(returnVar);
        }

        IVar var = null;
        Optional<Patch> patch = heap.getPatches().stream()
                .filter(r -> r.getNode().equals(rs)).findFirst();
        if (patch.isPresent()) {
            var = heap.findVar(patch.get().getName());
            if (nodes.isCreation(patch.get().getExp())) {
                var.setCreated(true);
            }
        } else {

            if (nodes.is(exp, SimpleName.class)) {
                var = heap.findVar(nodes.getName(exp));
            }

            if (nodes.is(exp, FieldAccess.class)) {
                String name = nodes
                        .getName(nodes.as(exp, FieldAccess.class).getName());
                Optional<IVar> field = heap.findField(name);
                if (field.isPresent()) {
                    var = field.get();
                }
            }
        }
        if (nonNull(var)) {
            // one of the localVar acts as returnVar so don't add again to heap

            returnVar = modelFactory.createReturnVar(var.getName(),
                    var.getType(), var.isMock());
            /*
             * field var is not created for real types, but getter may return it
             * so create returnVar and add it to heap
             */
            if (var.isField() && !var.isMock()) {
                var.setEnable(true);
                heap.getVars().add(returnVar);
            }

            returnVar.setEnable(var.isEnable());
            returnVar.setCreated(var.isCreated());
        }

        // statement - return this;
        if (nodes.is(exp, ThisExpression.class)) {
            String name = heap.getSelfFieldName();
            Optional<Type> type = resolver.getVarClass(exp);
            if (type.isPresent()) {
                returnVar =
                        modelFactory.createReturnVar(name, type.get(), false);
                returnVar.setSelfField(true);
                heap.getVars().add(returnVar);
            }
        }

        return Optional.ofNullable(returnVar);
    }

    public boolean isReturnable(final Optional<IVar> expectedVar,
            final Heap heap) {
        boolean returnable = true;
        if (expectedVar.isPresent()) {
            String name = expectedVar.get().getName();
            Optional<ExpVar> expVar = heap.findByLeftVar(name);
            if (expVar.isPresent() && nonNull(expVar.get().getRightExp())) {
                if (nodes.isAnonOrLambda(expVar.get().getRightExp())) {
                    returnable = false;
                }
            }
        }
        return returnable;
    }
}
