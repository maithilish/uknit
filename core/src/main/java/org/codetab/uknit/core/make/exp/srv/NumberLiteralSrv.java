package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;

import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.NodeFactory;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.NumberLiteral;

public class NumberLiteralSrv implements ExpService {

    @Inject
    private NodeFactory factory;

    @Override
    public List<Expression> getExps(final Expression exp) {
        checkState(exp instanceof NumberLiteral);
        return List.of(exp);
    }

    @Override
    public Expression unparenthesize(final Expression node) {
        checkState(node instanceof NumberLiteral);
        return factory.copyNode(node);
    }

    @Override
    public Expression getValue(final Expression node, final Expression copy,
            final Pack pack, final boolean createValue, final Heap heap) {
        checkState(node instanceof NumberLiteral);
        return node;
    }
}
