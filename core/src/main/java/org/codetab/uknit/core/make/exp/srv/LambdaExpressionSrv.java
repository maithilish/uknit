package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.isNull;

import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.NodeFactory;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.LambdaExpression;

public class LambdaExpressionSrv implements ExpService {

    @Inject
    private NodeFactory factory;
    @Inject
    private Initializers initializers;

    @Override
    public List<Expression> getExps(final Expression exp) {
        checkState(exp instanceof LambdaExpression);
        return List.of(exp);
    }

    @Override
    public Expression unparenthesize(final Expression node) {
        checkState(node instanceof LambdaExpression);
        LambdaExpression copy = (LambdaExpression) factory.copyNode(node);
        /*
         * lambda parameters and body are not expression and can't be
         * parenthesised
         */
        return copy;
    }

    @Override
    public Expression getValue(final Expression node, final Expression copy,
            final Pack pack, final boolean createValue, final Heap heap) {
        checkState(node instanceof LambdaExpression);
        Expression value = initializers.getInitializerAsExpression(node,
                createValue, heap);
        if (isNull(value)) {
            value = node;
        }
        return value;
    }
}
