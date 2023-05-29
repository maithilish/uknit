package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.patch.Patcher;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.Expression;

public class ConditionalExpressionSrv implements ExpService {

    @Inject
    private Wrappers wrappers;
    @Inject
    private ExpServiceLoader srvLoader;
    @Inject
    private Nodes nodes;
    @Inject
    private Patcher patcher;
    @Inject
    private NodeFactory factory;
    @Inject
    private ExpServiceLoader serviceLoader;

    @Override
    public List<Expression> getExps(final Expression node) {
        checkState(node instanceof ConditionalExpression);

        ConditionalExpression ce = (ConditionalExpression) node;

        List<Expression> exps = new ArrayList<>();

        exps.add(wrappers.strip(ce.getExpression()));
        exps.add(wrappers.strip(ce.getThenExpression()));
        exps.add(wrappers.strip(ce.getElseExpression()));

        return exps;
    }

    @Override
    public Expression unparenthesize(final Expression node) {
        checkState(node instanceof ConditionalExpression);
        ConditionalExpression copy =
                (ConditionalExpression) factory.copyNode(node);

        Expression exp = wrappers.strip(copy.getExpression());
        exp = serviceLoader.loadService(exp).unparenthesize(exp);
        copy.setExpression(factory.copyNode(exp));

        Expression thenExp = wrappers.strip(copy.getThenExpression());
        thenExp = serviceLoader.loadService(thenExp).unparenthesize(thenExp);
        copy.setThenExpression(factory.copyNode(thenExp));

        Expression elseExp = wrappers.strip(copy.getElseExpression());
        elseExp = serviceLoader.loadService(elseExp).unparenthesize(elseExp);
        copy.setElseExpression(factory.copyNode(elseExp));

        return copy;
    }

    @Override
    public Expression getValue(final Expression node, final Expression copy,
            final Pack pack, final boolean createValue, final Heap heap) {
        checkState(node instanceof ConditionalExpression);

        ConditionalExpression ce = (ConditionalExpression) node;
        ConditionalExpression ceCopy = (ConditionalExpression) copy;

        Expression exp;
        Expression thenExp;
        Expression elseExp;
        if (isNull(ceCopy)) {
            exp = wrappers.strip(ce.getExpression());
            thenExp = wrappers.strip(ce.getThenExpression());
            elseExp = wrappers.strip(ce.getElseExpression());
        } else {
            exp = wrappers.strip(ceCopy.getExpression());
            thenExp = wrappers.strip(ceCopy.getThenExpression());
            elseExp = wrappers.strip(ceCopy.getElseExpression());
        }

        ExpService srv = srvLoader.loadService(exp);
        Expression expValue = srv.getValue(exp,
                patcher.getCopy(exp, true, heap), pack, createValue, heap);

        Expression value;
        if (nodes.is(expValue, BooleanLiteral.class)) {
            if (((BooleanLiteral) expValue).booleanValue()) {
                value = thenExp;
            } else {
                value = elseExp;
            }
        } else {
            value = thenExp;
        }
        return value;
    }
}
