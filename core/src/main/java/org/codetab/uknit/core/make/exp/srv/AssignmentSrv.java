package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Expression;

public class AssignmentSrv implements ExpService {

    @Inject
    private Wrappers wrappers;
    @Inject
    private ExpServiceLoader serviceLoader;
    @Inject
    private NodeFactory factory;

    @Override
    public List<Expression> getExps(final Expression node) {
        checkState(node instanceof Assignment);

        List<Expression> exps = new ArrayList<>();
        Assignment as = (Assignment) node;
        exps.add(as.getLeftHandSide());
        exps.add(as.getRightHandSide());

        return exps;
    }

    @Override
    public Expression unparenthesize(final Expression node) {
        checkState(node instanceof Assignment);
        Assignment copy = (Assignment) factory.copyNode(node);

        Expression lhs = wrappers.strip(copy.getLeftHandSide());
        lhs = serviceLoader.loadService(lhs).unparenthesize(lhs);
        copy.setLeftHandSide(factory.copyNode(lhs));

        Expression rhs = wrappers.strip(copy.getRightHandSide());
        rhs = serviceLoader.loadService(rhs).unparenthesize(rhs);
        copy.setRightHandSide(factory.copyNode(rhs));

        return copy;
    }

    @Override
    public Expression getValue(final Expression node, final Expression copy,
            final Pack pack, final boolean createValue, final Heap heap) {
        checkState(node instanceof Assignment);
        return node;
    }

    @Override
    public <T extends Expression> T rejig(final T node, final Heap heap) {
        checkState(node instanceof Assignment);
        return node;
    }

}
