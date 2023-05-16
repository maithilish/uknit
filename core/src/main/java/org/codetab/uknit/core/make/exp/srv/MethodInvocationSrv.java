package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Arguments;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;

public class MethodInvocationSrv implements ExpService {

    @Inject
    private Arguments arguments;
    @Inject
    private Wrappers wrappers;
    @Inject
    private Packs packs;

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
    public Expression getValue(final Expression node, final Heap heap) {
        checkState(node instanceof MethodInvocation);
        Optional<Pack> pack = packs.findByExp(node, heap.getPacks());
        IVar var = packs.getVar(pack);
        if (nonNull(var) && var.getInitializer().isPresent()) {
            Object ini = var.getInitializer().get().getInitializer();
            if (ini instanceof Expression) {
                return (Expression) ini;
            }
        } else if (nonNull(var)) {
            return node.getAST().newSimpleName(var.getName());
        }
        return null;
    }
}
