package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Arguments;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.Expression;

public class ArrayCreationSrv implements ExpService {

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
        checkState(node instanceof ArrayCreation);

        List<Expression> exps = new ArrayList<>();

        ArrayCreation ac = (ArrayCreation) node;

        List<Expression> dims = arguments.getDims(ac);
        dims.forEach(d -> exps.add(wrappers.strip(d)));

        if (nonNull(ac.getInitializer())) {
            List<Expression> inExps = arguments.getExps(ac.getInitializer());
            inExps.forEach(i -> exps.add(wrappers.strip(i)));
        }
        return exps;
    }

    @Override
    public Expression unparenthesize(final Expression node) {
        checkState(node instanceof ArrayCreation);
        ArrayCreation copy = (ArrayCreation) factory.copyNode(node);

        List<Expression> dims = arguments.getDims(copy);
        for (int i = 0; i < dims.size(); i++) {
            Expression dim = wrappers.strip(dims.get(i));
            dim = serviceLoader.loadService(dim).unparenthesize(dim);
            dims.remove(i);
            dims.add(i, factory.copyNode(dim));
        }

        if (nonNull(copy.getInitializer())) {
            List<Expression> exps = arguments.getExps(copy.getInitializer());
            for (int i = 0; i < exps.size(); i++) {
                Expression exp = wrappers.strip(exps.get(i));
                exp = serviceLoader.loadService(exp).unparenthesize(exp);
                exps.remove(i);
                exps.add(i, factory.copyNode(exp));
            }
        }
        return copy;
    }

    @Override
    public Expression getValue(final Expression node, final Expression copy,
            final Pack pack, boolean createValue, final Heap heap) {
        checkState(node instanceof ArrayCreation);
        ArrayCreation ac = (ArrayCreation) node;
        return ac.getInitializer();
    }
}
