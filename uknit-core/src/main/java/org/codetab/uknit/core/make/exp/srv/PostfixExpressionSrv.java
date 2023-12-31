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
import org.eclipse.jdt.core.dom.PostfixExpression;

public class PostfixExpressionSrv implements ExpService {

    @Inject
    private Wrappers wrappers;
    @Inject
    private NodeFactory factory;
    @Inject
    private ExpServiceLoader serviceLoader;
    @Inject
    private Rejigs rejigs;

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
            final Pack pack, final boolean createValue, final Heap heap) {
        checkState(node instanceof PostfixExpression);
        return node;
    }

    @Override
    public <T extends Expression> T rejig(final T node, final Heap heap) {
        checkState(node instanceof PostfixExpression);
        if (rejigs.needsRejig(node)) {
            T copy = factory.copyExp(node);
            PostfixExpression wc = (PostfixExpression) copy;
            // replace any ref to this to CUT name
            rejigs.rejigThisExp(wc::getOperand, wc::setOperand, heap);
            return copy;
        } else {
            return node;
        }
    }
}
