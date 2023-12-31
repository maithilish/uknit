package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;

import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionMethodReference;
import org.eclipse.jdt.core.dom.SimpleName;

public class ExpressionMethodReferenceSrv implements ExpService {

    @Inject
    private Wrappers wrappers;
    @Inject
    private NodeFactory factory;
    @Inject
    private ExpServiceLoader serviceLoader;

    @Override
    public List<Expression> getExps(final Expression exp) {
        checkState(exp instanceof ExpressionMethodReference);
        return List.of(); // has no exps
    }

    @Override
    public Expression unparenthesize(final Expression node) {
        checkState(node instanceof ExpressionMethodReference);
        ExpressionMethodReference copy =
                (ExpressionMethodReference) factory.copyNode(node);

        Expression exp = wrappers.strip(copy.getExpression());
        exp = serviceLoader.loadService(exp).unparenthesize(exp);
        copy.setExpression(factory.copyNode(exp));

        SimpleName name = (SimpleName) wrappers.strip(copy.getName());
        name = (SimpleName) serviceLoader.loadService(name)
                .unparenthesize(name);
        copy.setName(factory.copyNode(name));

        return copy;
    }

    @Override
    public Expression getValue(final Expression node, final Expression copy,
            final Pack pack, final boolean createValue, final Heap heap) {
        checkState(node instanceof ExpressionMethodReference);
        return node;
    }

    @Override
    public <T extends Expression> T rejig(final T node, final Heap heap) {
        checkState(node instanceof ExpressionMethodReference);
        return node;
    }
}
