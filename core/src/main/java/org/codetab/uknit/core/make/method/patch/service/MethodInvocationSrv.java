package org.codetab.uknit.core.make.method.patch.service;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Patch;
import org.codetab.uknit.core.node.Arguments;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;

public class MethodInvocationSrv implements PatchService {

    @Inject
    private Arguments arguments;
    @Inject
    private Patchers patchers;
    @Inject
    private Wrappers wrappers;

    @Override
    public void patch(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {

        checkState(node instanceof MethodInvocation);
        checkState(node instanceof MethodInvocation);

        MethodInvocation mi = (MethodInvocation) node;
        MethodInvocation miCopy = (MethodInvocation) copy;

        Expression exp = wrappers.unpack(mi.getExpression());
        Expression expCopy = wrappers.unpack(miCopy.getExpression());
        patchers.patchExpWithName(pack, exp, expCopy, heap,
                miCopy::setExpression);

        List<Expression> args = arguments.getArgs(mi);
        List<Expression> argsCopy = arguments.getArgs(miCopy);
        patchers.patchExpsWithName(pack, args, argsCopy, heap);
    }

    @Override
    public void patchName(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {

        checkState(node instanceof MethodInvocation);
        checkState(copy instanceof MethodInvocation);

        MethodInvocation mi = (MethodInvocation) node;
        MethodInvocation miCopy = (MethodInvocation) copy;

        final List<Patch> patches = pack.getPatches();

        final int index = 0;
        Expression exp = wrappers.unpack(mi.getExpression());
        Expression expCopy = wrappers.unpack(miCopy.getExpression());
        patchers.patchExpWithPackPatches(pack, exp, expCopy, patches, index,
                miCopy::setExpression);

        final int offset = 1;
        List<Expression> args = arguments.getArgs(mi);
        List<Expression> argsCopy = arguments.getArgs(miCopy);
        patchers.patchExpsWithPackPatches(pack, args, argsCopy, patches, offset,
                heap);
    }

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
    public void patchValue(final Expression node, final Expression copy,
            final Heap heap) {
        checkState(node instanceof MethodInvocation);
        checkState(node instanceof MethodInvocation);

        MethodInvocation mi = (MethodInvocation) node;
        MethodInvocation miCopy = (MethodInvocation) copy;

        Expression exp = wrappers.unpack(mi.getExpression());
        Expression expCopy = wrappers.unpack(miCopy.getExpression());
        patchers.patchValue(exp, expCopy, heap, miCopy::setExpression);

        List<Expression> args = arguments.getArgs(mi);
        List<Expression> argsCopy = arguments.getArgs(miCopy);
        patchers.patchValue(args, argsCopy, heap);
    }
}
