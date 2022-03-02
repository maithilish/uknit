package org.codetab.uknit.core.make.method.visit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.body.Initializers;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Verify;
import org.codetab.uknit.core.make.model.When;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.SimpleName;

public class UseMarkers {

    @Inject
    private Types types;
    @Inject
    private Nodes nodes;
    @Inject
    private Methods methods;
    @Inject
    private Initializers initializers;

    public void markVarsUsedInWhens(final Heap heap) {
        for (When when : heap.getWhens()) {
            for (IVar var : when.getReturnVars()) {
                var.setUsed(true);
            }
            for (String name : when.getNames()) {
                heap.findVar(name).setUsed(true);
            }
        }
    }

    public void markVarsUsedInVerify(final Heap heap) {
        for (Verify verify : heap.getVerifies()) {
            MethodInvocation mi = verify.getMi();
            for (String name : methods.getNames(mi)) {
                heap.findVar(name).setUsed(true);
            }
        }
    }

    public void markVarsUsedInReturn(final Heap heap) {
        Optional<IVar> expectedVar = heap.getExpectedVar();
        expectedVar.ifPresent(v -> {
            if (!types.isBoolean(v.getType())) {
                IVar localVar = heap.findVar(v.getName());
                localVar.setUsed(true);
            }
        });
    }

    public void markExpVars(final List<Expression> exps, final Heap heap) {
        for (Expression exp : exps) {
            List<String> names = new ArrayList<>();
            if (nodes.is(exp, PrefixExpression.class)) {
                Expression e =
                        nodes.as(exp, PrefixExpression.class).getOperand();
                if (nodes.is(e, SimpleName.class)) {
                    names.add(nodes.getName(e));
                }
            } else if (nodes.is(exp, PostfixExpression.class)) {
                Expression e =
                        nodes.as(exp, PostfixExpression.class).getOperand();
                if (nodes.is(e, SimpleName.class)) {
                    names.add(nodes.getName(e));
                }
            } else if (nodes.is(exp, InfixExpression.class)) {
                Expression e =
                        nodes.as(exp, InfixExpression.class).getLeftOperand();
                if (nodes.is(e, SimpleName.class)) {
                    names.add(nodes.getName(e));
                }
                e = nodes.as(exp, InfixExpression.class).getRightOperand();
                if (nodes.is(e, SimpleName.class)) {
                    names.add(nodes.getName(e));
                }
            }
            for (String name : names) {
                heap.findVar(name).setUsed(true);
            }
        }
    }

    public List<Expression> getInitializers(final Heap heap) {
        List<Expression> list = new ArrayList<>();
        List<IVar> usedVars = heap.getVars(
                v -> (v.isInferVar() || v.isLocalVar() || v.isReturnVar())
                        && v.isUsed());
        for (IVar usedVar : usedVars) {
            initializers.getInitializerExp(usedVar, heap)
                    .ifPresent(e -> list.add(e));
        }
        return list;
    }
}
