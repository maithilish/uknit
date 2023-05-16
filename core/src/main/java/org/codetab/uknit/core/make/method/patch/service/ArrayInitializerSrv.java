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
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Expression;

public class ArrayInitializerSrv implements PatchService {

    @Inject
    private Patchers patchers;
    @Inject
    private Arguments arguments;
    @Inject
    private Wrappers wrappers;

    @Override
    public void patch(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {

        checkState(node instanceof ArrayInitializer);
        checkState(copy instanceof ArrayInitializer);

        ArrayInitializer ai = (ArrayInitializer) node;
        ArrayInitializer aiCopy = (ArrayInitializer) copy;

        List<Expression> exps = arguments.getExps(ai);
        List<Expression> expsCopy = arguments.getExps(aiCopy);
        patchers.patchExpsWithName(pack, exps, expsCopy, heap);
    }

    @Override
    public void patchName(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {
        checkState(node instanceof ArrayInitializer);
        checkState(copy instanceof ArrayInitializer);

        ArrayInitializer ai = (ArrayInitializer) node;
        ArrayInitializer aiCopy = (ArrayInitializer) copy;

        final List<Patch> patches = pack.getPatches();

        List<Expression> exps = arguments.getExps(ai);
        List<Expression> expsCopy = arguments.getExps(aiCopy);
        int offset = 0;
        patchers.patchExpsWithPackPatches(pack, exps, expsCopy, patches, offset,
                heap);
    }

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
    public void patchValue(final Expression node, final Expression copy,
            final Heap heap) {
        checkState(node instanceof ArrayInitializer);
        checkState(copy instanceof ArrayInitializer);

        ArrayInitializer ai = (ArrayInitializer) node;
        ArrayInitializer aiCopy = (ArrayInitializer) copy;

        List<Expression> exps = arguments.getExps(ai);
        List<Expression> expsCopy = arguments.getExps(aiCopy);
        patchers.patchValue(exps, expsCopy, heap);
    }
}
