package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Arguments;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Expression;

public class ArrayInitializerSrv implements ExpService {

    @Inject
    private Arguments arguments;
    @Inject
    private Wrappers wrappers;
    @Inject
    private NodeFactory factory;
    @Inject
    private ExpServiceLoader serviceLoader;

    @Override
    public List<Expression> getExps(final Expression node) {
        checkState(node instanceof ArrayInitializer);

        ArrayInitializer ai = (ArrayInitializer) node;

        List<Expression> exps = new ArrayList<>();

        List<Expression> aiExps = arguments.getExps(ai);
        aiExps.forEach(e -> exps.add(wrappers.strip(e)));

        return exps;
    }

    @Override
    public Expression unparenthesize(final Expression node) {
        checkState(node instanceof ArrayInitializer);
        ArrayInitializer copy = (ArrayInitializer) factory.copyNode(node);

        List<Expression> exps = arguments.getExps(copy);
        for (int i = 0; i < exps.size(); i++) {
            Expression exp = wrappers.strip(exps.get(i));
            exp = serviceLoader.loadService(exp).unparenthesize(exp);
            exps.remove(i);
            exps.add(i, factory.copyNode(exp));
        }
        return copy;
    }

    @Override
    public Expression getValue(final Expression node, final Expression copy,
            final Pack pack, boolean createValue, final Heap heap) {
        checkState(node instanceof ArrayInitializer);
        return node;
    }
}
