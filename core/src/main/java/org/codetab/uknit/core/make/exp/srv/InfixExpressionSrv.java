package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Arguments;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;

public class InfixExpressionSrv implements ExpService {

    @Inject
    private Arguments arguments;
    @Inject
    private Wrappers wrappers;
    @Inject
    private NodeFactory factory;
    @Inject
    private ExpServiceLoader serviceLoader;
    @Inject
    private Initializers initializers;

    @Override
    public List<Expression> getExps(final Expression node) {
        checkState(node instanceof InfixExpression);

        InfixExpression infix = (InfixExpression) node;

        List<Expression> exps = new ArrayList<>();

        exps.add(wrappers.strip(infix.getLeftOperand()));
        exps.add(wrappers.strip(infix.getRightOperand()));

        List<Expression> exOpers = arguments.getExtendedOperands(infix);
        exOpers.forEach(eo -> exps.add(wrappers.strip(eo)));
        return exps;
    }

    @Override
    public Expression unparenthesize(final Expression node) {
        checkState(node instanceof InfixExpression);
        InfixExpression copy = (InfixExpression) factory.copyNode(node);

        Expression leftOp = wrappers.strip(copy.getLeftOperand());
        leftOp = serviceLoader.loadService(leftOp).unparenthesize(leftOp);
        copy.setLeftOperand(factory.copyNode(leftOp));

        Expression rightOp = wrappers.strip(copy.getRightOperand());
        rightOp = serviceLoader.loadService(rightOp).unparenthesize(rightOp);
        copy.setRightOperand(factory.copyNode(rightOp));

        List<Expression> exOpers = arguments.getExtendedOperands(copy);
        for (int i = 0; i < exOpers.size(); i++) {
            Expression exOpr = wrappers.strip(exOpers.get(i));
            exOpr = serviceLoader.loadService(exOpr).unparenthesize(exOpr);
            exOpers.remove(i);
            exOpers.add(i, factory.copyNode(exOpr));
        }
        return copy;
    }

    @Override
    public Expression getValue(final Expression node, final Expression copy,
            final Pack pack, final boolean createValue, final Heap heap) {
        checkState(node instanceof InfixExpression);
        Expression value = initializers.getInitializerAsExpression(node,
                createValue, heap);
        if (isNull(value)) {
            value = node;
        }
        return value;
    }
}
