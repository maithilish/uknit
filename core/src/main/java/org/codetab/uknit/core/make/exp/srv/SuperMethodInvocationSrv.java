package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.CodeException;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Arguments;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;

public class SuperMethodInvocationSrv implements ExpService {

    @Inject
    private Arguments arguments;
    @Inject
    private Wrappers wrappers;
    @Inject
    private NodeFactory factory;
    @Inject
    private ExpServiceLoader serviceLoader;
    @Inject
    private Nodes nodes;

    @Override
    public List<Expression> getExps(final Expression node) {
        checkState(node instanceof SuperMethodInvocation);
        SuperMethodInvocation smi = (SuperMethodInvocation) node;
        List<Expression> exps = new ArrayList<>();

        exps.add(wrappers.strip(smi.getName()));
        List<Expression> args = arguments.getArgs(smi);
        args.forEach(a -> exps.add(wrappers.strip(a)));
        return exps;
    }

    @Override
    public Expression unparenthesize(final Expression node) {
        checkState(node instanceof SuperMethodInvocation);
        SuperMethodInvocation copy =
                (SuperMethodInvocation) factory.copyNode(node);

        // parenthesise is not allowed for name and class name

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
        checkState(node instanceof SuperMethodInvocation);
        throw new CodeException(
                nodes.exMessage("getValue() not implemented", node));
    }
}
