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
import org.eclipse.jdt.core.dom.ParenthesizedExpression;

public class ParenthesizedExpressionSrv implements ExpService {

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
        checkState(node instanceof ParenthesizedExpression);

        ParenthesizedExpression pe = (ParenthesizedExpression) node;

        List<Expression> exps = new ArrayList<>();

        exps.add(wrappers.strip(pe.getExpression()));

        return exps;
    }

    @Override
    public Expression unparenthesize(final Expression node) {
        checkState(node instanceof ParenthesizedExpression);
        ParenthesizedExpression copy =
                (ParenthesizedExpression) factory.copyNode(node);

        Expression exp = wrappers.strip(copy.getExpression());
        exp = serviceLoader.loadService(exp).unparenthesize(exp);
        copy.setExpression(factory.copyNode(exp));

        return copy;
    }

    @Override
    public Expression getValue(final Expression node, final Expression copy,
            final Pack pack, boolean createValue, final Heap heap) {
        checkState(node instanceof ParenthesizedExpression);
        throw new CodeException(
                nodes.exMessage("getValue() not implemented", node));
    }
}
