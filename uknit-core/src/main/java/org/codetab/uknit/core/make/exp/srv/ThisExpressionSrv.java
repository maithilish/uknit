package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ThisExpression;

public class ThisExpressionSrv implements ExpService {

    @Inject
    private Wrappers wrappers;
    @Inject
    private NodeFactory factory;

    @Override
    public List<Expression> getExps(final Expression node) {
        checkState(node instanceof ThisExpression);

        List<Expression> exps = new ArrayList<>();

        ThisExpression te = (ThisExpression) node;
        exps.add(wrappers.strip(te.getQualifier()));

        return exps;
    }

    @Override
    public Expression unparenthesize(final Expression node) {
        checkState(node instanceof ThisExpression);
        ThisExpression copy = (ThisExpression) factory.copyNode(node);
        // parenthesise is not allowed for name
        return copy;
    }

    @Override
    public Expression getValue(final Expression node, final Expression copy,
            final Pack pack, final boolean createValue, final Heap heap) {
        checkState(node instanceof ThisExpression);
        return node;
    }

    @Override
    public <T extends Expression> T rejig(final T node, final Heap heap) {
        checkState(node instanceof ThisExpression);
        return node;
    }
}
