package org.codetab.uknit.core.make.method.visit;

import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Call;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.node.Mocks;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;

import com.google.common.collect.Lists;

public class ReturnProcessor {

    @Inject
    private ExpReplacer expReplacer;
    @Inject
    private Nodes nodes;
    @Inject
    private Mocks mocks;
    @Inject
    private ModelFactory modelFactory;

    public void replaceExpression(final ReturnStatement rs, final Heap heap) {
        Expression exp = rs.getExpression();
        if (nonNull(exp)) {
            List<Expression> exps = Lists.newArrayList(exp);
            expReplacer.replaceExpWithInfer(rs, exps, heap);
            expReplacer.replaceExpWithInitializer(rs, exps, heap);
        }
    }

    public Optional<IVar> getExpectedVar(final ReturnStatement rs,
            final Heap heap) {
        Expression exp = rs.getExpression();
        IVar expectedVar = null;
        if (nodes.is(exp, SimpleName.class)) {
            String name = nodes.getName(exp);
            try {
                /*
                 * one of the localVar acts as returnVar so don't add again to
                 * heap
                 */
                IVar var = heap.findVar(name);

                if (nodes.isAnonOrLambda(exp)) {
                    var.setHidden(true);
                }

                // if (types.isBoolean(var.getType())) {
                // var.setUsed(false);
                // } else {
                // boolean cascade = true;
                // useMarker.mark(var, cascade, heap);
                // }
                expectedVar = modelFactory.createReturnVar(var.getName(),
                        var.getType(), var.isMock());

                expectedVar.setHidden(var.isHidden());
            } catch (IllegalStateException e) {
                /*
                 * field var is not created for real types, but getter may
                 * return it so create returnVar and add it to heap
                 */
                Call call = heap.getCall();
                Type type = call.getReturnType();
                boolean mock = mocks.isMockable(type);
                expectedVar = modelFactory.createReturnVar(name, type, mock);
                heap.getVars().add(expectedVar);
            }
        }
        return Optional.ofNullable(expectedVar);
    }
}
