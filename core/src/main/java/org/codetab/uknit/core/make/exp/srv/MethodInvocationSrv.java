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
import org.codetab.uknit.core.node.Methods;
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
    public Expression getValue(final Expression node, final Pack pack,
            final Heap heap) {
        checkState(node instanceof MethodInvocation);

        /*
         * If value is static call then return it. Ex: boolean[] a =
         * {Boolean.valueOf(true)}; value of a[0] is Boolean.valueOf(true).
         */
        if (methods.isStaticCall(node)) {
            return node;
        }

        Optional<Pack> miPackO = packs.findByExp(node, heap.getPacks());
        IVar var = packs.getVar(miPackO);

        // if var has expression initializer return it else return var name exp
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
