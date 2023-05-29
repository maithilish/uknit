package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InstanceofExpression;

public class InstanceofExpressionSrv implements ExpService {

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
        checkState(node instanceof InstanceofExpression);

        InstanceofExpression infix = (InstanceofExpression) node;

        List<Expression> exps = new ArrayList<>();

        exps.add(wrappers.strip(infix.getLeftOperand()));

        return exps;
    }

    @Override
    public Expression unparenthesize(final Expression node) {
        checkState(node instanceof InstanceofExpression);
        InstanceofExpression copy =
                (InstanceofExpression) factory.copyNode(node);

        Expression leftOp = wrappers.strip(copy.getLeftOperand());
        leftOp = serviceLoader.loadService(leftOp).unparenthesize(leftOp);
        copy.setLeftOperand(factory.copyNode(leftOp));

        return copy;
    }

    @Override
    public Expression getValue(final Expression node, final Expression copy,
            final Pack pack, final boolean createValue, final Heap heap) {
        checkState(node instanceof InstanceofExpression);
        Expression value = initializers.getInitializerAsExpression(node,
                createValue, heap);
        if (isNull(value)) {
            value = node;
        }
        return value;
    }
}
