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
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;

public class ClassInstanceCreationSrv implements PatchService {

    @Inject
    private Patchers patchers;
    @Inject
    private Arguments arguments;
    @Inject
    private Wrappers wrappers;

    @Override
    public void patch(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {

        checkState(node instanceof ClassInstanceCreation);
        checkState(copy instanceof ClassInstanceCreation);

        ClassInstanceCreation cic = (ClassInstanceCreation) node;
        ClassInstanceCreation cicCopy = (ClassInstanceCreation) copy;

        List<Expression> args = arguments.getArgs(cic);
        List<Expression> argsCopy = arguments.getArgs(cicCopy);
        patchers.patchExpsWithName(pack, args, argsCopy, heap);
    }

    @Override
    public void patchName(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {
        checkState(node instanceof ClassInstanceCreation);
        checkState(copy instanceof ClassInstanceCreation);

        ClassInstanceCreation cic = (ClassInstanceCreation) node;
        ClassInstanceCreation cicCopy = (ClassInstanceCreation) copy;

        final List<Patch> patches = pack.getPatches();

        int offset = 0;
        List<Expression> args = arguments.getArgs(cic);
        List<Expression> argsCopy = arguments.getArgs(cicCopy);
        patchers.patchExpsWithPackPatches(args, argsCopy, patches, offset,
                heap);
    }

    @Override
    public List<Expression> getExps(final Expression node) {
        checkState(node instanceof ClassInstanceCreation);

        ClassInstanceCreation cic = (ClassInstanceCreation) node;

        List<Expression> exps = new ArrayList<>();

        List<Expression> args = arguments.getArgs(cic);
        args.forEach(a -> exps.add(wrappers.strip(a)));

        return exps;
    }
}
