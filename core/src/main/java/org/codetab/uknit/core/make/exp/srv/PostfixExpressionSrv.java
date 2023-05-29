package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.CodeException;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.PostfixExpression;

public class PostfixExpressionSrv implements ExpService {

    @Inject
    private Wrappers wrappers;
    @Inject
    private NodeFactory factory;
    @Inject
    private ExpServiceLoader serviceLoader;
    @Inject
    private Nodes nodes;

    @Override
    public List<Expression> getExps(final Expression node) {
        checkState(node instanceof PostfixExpression);

        PostfixExpression pfix = (PostfixExpression) node;

        List<Expression> exps = new ArrayList<>();

        exps.add(wrappers.strip(pfix.getOperand()));

        return exps;
    }

    @Override
    public Expression unparenthesize(final Expression node) {
        checkState(node instanceof PostfixExpression);
        PostfixExpression copy = (PostfixExpression) factory.copyNode(node);

        Expression exp = wrappers.strip(copy.getOperand());
        exp = serviceLoader.loadService(exp).unparenthesize(exp);
        copy.setOperand(factory.copyNode(exp));

        return copy;
    }

    @Override
    public Expression getValue(final Expression node, final Expression copy,
            final Pack pack, boolean createValue, final Heap heap) {
        checkState(node instanceof PostfixExpression);
        throw new CodeException(
                nodes.exMessage("getValue() not implemented", node));
    }
}
