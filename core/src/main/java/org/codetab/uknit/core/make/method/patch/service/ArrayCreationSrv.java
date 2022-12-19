package org.codetab.uknit.core.make.method.patch.service;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Patch;
import org.codetab.uknit.core.node.Arguments;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.Expression;

public class ArrayCreationSrv implements PatchService {

    @Inject
    private Patchers patchers;
    @Inject
    private Arguments arguments;
    @Inject
    private Wrappers wrappers;

    @Override
    public void patch(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {

        checkState(node instanceof ArrayCreation);
        checkState(copy instanceof ArrayCreation);

        ArrayCreation ac = (ArrayCreation) node;
        ArrayCreation acCopy = (ArrayCreation) copy;

        // patch dims
        List<Expression> dims = arguments.getDims(ac);
        List<Expression> dimsCopy = arguments.getDims(acCopy);
        patchers.patchExpsWithName(pack, dims, dimsCopy, heap);

        // patch initializer exps
        if (nonNull(ac.getInitializer())) {
            List<Expression> inExps = arguments.getExps(ac.getInitializer());
            List<Expression> inExpsCopy =
                    arguments.getExps(acCopy.getInitializer());
            patchers.patchExpsWithName(pack, inExps, inExpsCopy, heap);
        }
    }

    @Override
    public void patchName(final Pack pack, final Expression node,
            final Expression copy) {
        checkState(node instanceof ArrayCreation);
        checkState(copy instanceof ArrayCreation);

        ArrayCreation ac = (ArrayCreation) node;
        ArrayCreation acCopy = (ArrayCreation) copy;

        final List<Patch> patches = pack.getPatches();

        // patch dims
        int offset = 0;
        List<Expression> dims = arguments.getDims(ac);
        List<Expression> dimsCopy = arguments.getDims(acCopy);
        patchers.patchExpsWithName(dims, dimsCopy, patches, offset);

        // patch initializer exps
        offset = dims.size();
        if (nonNull(ac.getInitializer())) {
            List<Expression> inExps = arguments.getExps(ac.getInitializer());
            List<Expression> inExpsCopy =
                    arguments.getExps(acCopy.getInitializer());
            patchers.patchExpsWithName(inExps, inExpsCopy, patches, offset);
        }
    }

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
}
