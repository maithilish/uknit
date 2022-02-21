package org.codetab.uknit.core.make.method.visit;

import static java.util.Objects.nonNull;

import java.util.List;
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

import com.google.common.collect.Lists;

public class ReturnProcessor {

    @Inject
    private Patcher patcher;
    @Inject
    private Nodes nodes;
    @Inject
    private ModelFactory modelFactory;
    @Inject
    private Resolver resolver;

    public void stagePatches(final ReturnStatement rs, final Heap heap) {
        Expression exp = rs.getExpression();
        if (nonNull(exp)) {
            List<Expression> exps = Lists.newArrayList(exp);
            patcher.stageInferPatch(rs, exps, heap);
            patcher.stageInitializerPatch(rs, exps, heap);
        }
    }

    public Optional<IVar> getExpectedVar(final ReturnStatement rs,
            final Heap heap) {
        Expression exp = rs.getExpression();
        ReturnVar expectedVar = null;

        IVar var = null;
        Optional<Patch> patch = heap.getPatches().stream()
                .filter(r -> r.getNode().equals(rs)).findFirst();
        if (patch.isPresent()) {
            var = heap.findVar(patch.get().getName());
        }

        if (nodes.is(exp, SimpleName.class)) {
            var = heap.findVar(nodes.getName(exp));
        }

        if (nodes.is(exp, FieldAccess.class)) {
            String name =
                    nodes.getName(nodes.as(exp, FieldAccess.class).getName());
            Optional<IVar> field = heap.findField(name);
            if (field.isPresent()) {
                var = field.get();
            }
        }

        if (nonNull(var)) {
            // one of the localVar acts as returnVar so don't add again to heap

            /*
             * If var is created by static call, it is staged but hidden in
             * VarStager.stageLocalVar(). When such vars are returned they are
             * shown if creation exp is not anon or lambda.
             */
            if (var.isCreated()) {
                Optional<ExpVar> expVar = heap.findByLeftVar(var.getName());
                if (expVar.isPresent()) {
                    if (!nodes.isAnonOrLambda(expVar.get().getRightExp())) {
                        var.setHidden(false);
                    }
                }
            }

            if (nodes.isAnonOrLambda(exp)) {
                var.setHidden(true);
            }
            expectedVar = modelFactory.createReturnVar(var.getName(),
                    var.getType(), var.isMock());
            /*
             * field var is not created for real types, but getter may return it
             * so create returnVar and add it to heap
             */
            if (var.isField() && !var.isMock()) {
                var.setHidden(false);
                heap.getVars().add(expectedVar);
            }

            expectedVar.setHidden(var.isHidden());
        }

        // statement - return this;
        if (nodes.is(exp, ThisExpression.class)) {
            String name = heap.getSelfFieldName();
            Optional<Type> type = resolver.getVarClass(exp);
            if (type.isPresent()) {
                expectedVar =
                        modelFactory.createReturnVar(name, type.get(), false);
                expectedVar.setHidden(false);
                expectedVar.setSelfField(true);
                heap.getVars().add(expectedVar);
            }
        }

        return Optional.ofNullable(expectedVar);
    }
}
