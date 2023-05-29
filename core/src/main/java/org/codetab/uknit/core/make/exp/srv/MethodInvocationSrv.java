package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.ResolveException;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Arguments;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;

public class MethodInvocationSrv implements ExpService {

    @Inject
    private Arguments arguments;
    @Inject
    private Wrappers wrappers;
    @Inject
    private Methods methods;
    @Inject
    private Packs packs;
    @Inject
    private NodeFactory factory;
    @Inject
    private ExpServiceLoader serviceLoader;
    @Inject
    private Initializers initializers;

    @Override
    public List<Expression> getExps(final Expression node) {
        checkState(node instanceof MethodInvocation);
        MethodInvocation mi = (MethodInvocation) node;

        List<Expression> exps = new ArrayList<>();
        exps.add(wrappers.strip(mi.getExpression()));

        List<Expression> args = arguments.getArgs(mi);
        args.forEach(a -> exps.add(wrappers.strip(a)));
        return exps;
    }

    @Override
    public Expression unparenthesize(final Expression node) {
        checkState(node instanceof MethodInvocation);
        MethodInvocation copy = (MethodInvocation) factory.copyNode(node);

        Expression exp = wrappers.strip(copy.getExpression());
        if (nonNull(exp)) {
            exp = serviceLoader.loadService(exp).unparenthesize(exp);
            copy.setExpression(factory.copyNode(exp));
        }

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
            final Pack pack, final boolean createValue, final Heap heap) {
        checkState(node instanceof MethodInvocation);

        /*
         * If value is static call then return it. Ex: boolean[] a =
         * {Boolean.valueOf(true)}; value of a[0] is Boolean.valueOf(true). The
         * node is patched expression and can't be resolved!
         */
        try {
            if (methods.isStaticCall(node)) {
                return node;
            }
        } catch (ResolveException e) {
        }

        Expression value = initializers.getInitializerAsExpression(node,
                createValue, heap);
        if (isNull(value)) {
            // if var exists then value is var name
            IVar var = packs.getVar(packs.findByExp(node, heap.getPacks()));
            if (nonNull(var)) {
                value = node.getAST().newSimpleName(var.getName());
            }
        }
        return value;
    }
}
