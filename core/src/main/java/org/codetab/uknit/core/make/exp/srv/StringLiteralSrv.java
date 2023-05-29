package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;

import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.NodeFactory;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.StringLiteral;

public class StringLiteralSrv implements ExpService {

    @Inject
    private NodeFactory factory;

    @Override
    public List<Expression> getExps(final Expression exp) {
        checkState(exp instanceof StringLiteral);
        return List.of(exp);
    }

    @Override
    public Expression unparenthesize(final Expression node) {
        checkState(node instanceof StringLiteral);
        return factory.copyNode(node);
    }

    @Override
    public Expression getValue(final Expression node, final Expression copy,
            final Pack pack, boolean createValue, final Heap heap) {
        checkState(node instanceof StringLiteral);
        return node;
    }
}
