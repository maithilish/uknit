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
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;

public class SuperMethodInvocationSrv implements PatchService {

    @Inject
    private Arguments arguments;
    @Inject
    private Patchers patchers;
    @Inject
    private Wrappers wrappers;

    @Override
    public void patch(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {

        checkState(node instanceof SuperMethodInvocation);
        checkState(copy instanceof SuperMethodInvocation);

        SuperMethodInvocation smi = (SuperMethodInvocation) node;
        SuperMethodInvocation smiCopy = (SuperMethodInvocation) copy;

        Expression exp = wrappers.unpack(smi.getName());
        Expression expCopy = wrappers.unpack(smiCopy.getName());
        patchers.patchExpWithName(pack, exp, expCopy, heap,
                (name) -> smiCopy.setName((SimpleName) name));

        List<Expression> args = arguments.getArgs(smi);
        List<Expression> argsCopy = arguments.getArgs(smiCopy);
        patchers.patchExpsWithName(pack, args, argsCopy, heap);
    }

    @Override
    public void patchName(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {
        checkState(node instanceof SuperMethodInvocation);
        checkState(copy instanceof SuperMethodInvocation);

        SuperMethodInvocation smi = (SuperMethodInvocation) node;
        SuperMethodInvocation smiCopy = (SuperMethodInvocation) copy;

        final List<Patch> patches = pack.getPatches();

        int index = 0;
        Expression exp = wrappers.unpack(smi.getName());
        Expression expCopy = wrappers.unpack(smiCopy.getName());
        patchers.patchExpWithPackPatches(pack, exp, expCopy, patches, index,
                (name) -> smiCopy.setName((SimpleName) name));

        int offset = 1;
        List<Expression> args = arguments.getArgs(smi);
        List<Expression> argsCopy = arguments.getArgs(smiCopy);
        patchers.patchExpsWithPackPatches(pack, args, argsCopy, patches, offset,
                heap);
    }

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

    // REVIEW - write test
    @Override
    public void patchValue(final Expression node, final Expression copy,
            final Heap heap) {
        // TODO Auto-generated method stub

    }
}
