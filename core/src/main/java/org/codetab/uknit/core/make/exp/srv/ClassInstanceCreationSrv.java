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
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;

public class ClassInstanceCreationSrv implements ExpService {

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
        checkState(node instanceof ClassInstanceCreation);

        ClassInstanceCreation cic = (ClassInstanceCreation) node;

        List<Expression> exps = new ArrayList<>();

        List<Expression> args = arguments.getArgs(cic);
        args.forEach(a -> exps.add(wrappers.strip(a)));

        return exps;
    }

    @Override
    public Expression unparenthesize(final Expression node) {
        checkState(node instanceof ClassInstanceCreation);
        ClassInstanceCreation copy =
                (ClassInstanceCreation) factory.copyNode(node);

        List<Expression> args = arguments.getArgs(copy);
        for (int i = 0; i < args.size(); i++) {
            Expression arg = wrappers.strip(args.get(i));
            arg = serviceLoader.loadService(arg).unparenthesize(arg);
            args.remove(i);
            args.add(i, factory.copyNode(arg));
        }
        return copy;
    }

    @Override
    public Expression getValue(final Expression node, final Expression copy,
            final Pack pack, boolean createValue, final Heap heap) {
        checkState(node instanceof ClassInstanceCreation);
        /*
         * If exp is new String("foo") then value is new String("foo") as uKnit
         * doesn't evaluate the exp.
         */
        return node;
    }
}
