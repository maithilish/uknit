package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.exp.SafeExps;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;

public class InfixExpressionSrv implements ExpService {

    @Inject
    private SafeExps safeExps;
    @Inject
    private Wrappers wrappers;
    @Inject
    private NodeFactory factory;
    @Inject
    private SafeExpSetter safeExpSetter;
    @Inject
    private ExpServiceLoader serviceLoader;
    @Inject
    private Initializers initializers;
    @Inject
    private Rejigs rejigs;

    @Override
    public List<Expression> getExps(final Expression node) {
        checkState(node instanceof InfixExpression);

        InfixExpression infix = (InfixExpression) node;

        List<Expression> exps = new ArrayList<>();

        exps.add(wrappers.strip(infix.getLeftOperand()));
        exps.add(wrappers.strip(infix.getRightOperand()));

        List<Expression> exOpers = safeExps.getExtendedOperands(infix);
        exOpers.forEach(eo -> exps.add(wrappers.strip(eo)));
        return exps;
    }

    @Override
    public Expression unparenthesize(final Expression node) {
        checkState(node instanceof InfixExpression);
        InfixExpression copy = (InfixExpression) factory.copyNode(node);

        Expression leftOp = wrappers.strip(copy.getLeftOperand());
        leftOp = serviceLoader.loadService(leftOp).unparenthesize(leftOp);
        safeExpSetter.setExp(copy, copy::getLeftOperand, copy::setLeftOperand,
                leftOp);

        Expression rightOp = wrappers.strip(copy.getRightOperand());
        rightOp = serviceLoader.loadService(rightOp).unparenthesize(rightOp);
        safeExpSetter.setExp(copy, copy::getRightOperand, copy::setRightOperand,
                rightOp);

        List<Expression> exOpers = safeExps.getExtendedOperands(copy);
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

    @Override
    public <T extends Expression> T rejig(final T node, final Heap heap) {
        checkState(node instanceof InfixExpression);
        if (rejigs.needsRejig(node)) {
            T copy = factory.copyExp(node);
            InfixExpression wc = (InfixExpression) copy;
            // replace any ref to this to CUT name
            rejigs.rejigThisExp(wc::getLeftOperand, wc::setLeftOperand, heap);
            rejigs.rejigThisExp(wc::getRightOperand, wc::setRightOperand, heap);
            rejigs.rejigThisExp(safeExps.getExtendedOperands(wc), heap);
            return copy;
        } else {
            return node;
        }
    }
}
