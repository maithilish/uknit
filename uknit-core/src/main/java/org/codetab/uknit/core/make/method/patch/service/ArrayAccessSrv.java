package org.codetab.uknit.core.make.method.patch.service;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Patch;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.Expression;

public class ArrayAccessSrv implements PatchService {

    @Inject
    private Patchers patchers;
    @Inject
    private Wrappers wrappers;

    @Override
    public void patch(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {

        checkState(node instanceof ArrayAccess);
        checkState(copy instanceof ArrayAccess);

        ArrayAccess aa = (ArrayAccess) node;
        ArrayAccess aaCopy = (ArrayAccess) copy;

        Expression array = wrappers.unpack(aa.getArray());
        Expression arrayCopy = wrappers.unpack(aaCopy.getArray());
        patchers.patchExpWithName(pack, array, arrayCopy, heap,
                aaCopy::setArray);

        Expression arrayIndex = wrappers.unpack(aa.getIndex());
        Expression arrayIndexCopy = wrappers.unpack(aaCopy.getIndex());
        patchers.patchExpWithName(pack, arrayIndex, arrayIndexCopy, heap,
                aaCopy::setIndex);
    }

    @Override
    public void patchName(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {
        checkState(node instanceof ArrayAccess);
        checkState(copy instanceof ArrayAccess);

        ArrayAccess aa = (ArrayAccess) node;
        ArrayAccess aaCopy = (ArrayAccess) copy;

        final List<Patch> patches = pack.getPatches();

        int index = 0;
        Expression array = wrappers.unpack(aa.getArray());
        Expression arrayCopy = wrappers.unpack(aaCopy.getArray());
        patchers.patchExpWithPackPatches(pack, array, arrayCopy, patches, index,
                aaCopy::setArray);

        index = 1;
        Expression arrayIndex = wrappers.unpack(aa.getIndex());
        Expression arrayIndexCopy = wrappers.unpack(aaCopy.getIndex());
        patchers.patchExpWithPackPatches(pack, arrayIndex, arrayIndexCopy,
                patches, index, aaCopy::setIndex);
    }

    @Override
    public List<Expression> getExps(final Expression node) {
        checkState(node instanceof ArrayAccess);

        List<Expression> exps = new ArrayList<>();

        ArrayAccess aa = (ArrayAccess) node;
        exps.add(wrappers.strip(aa.getArray()));
        exps.add(wrappers.strip(aa.getIndex()));

        return exps;
    }

    @Override
    public void patchValue(final Expression node, final Expression copy,
            final Heap heap) {
        checkState(node instanceof ArrayAccess);
        checkState(copy instanceof ArrayAccess);

        ArrayAccess aa = (ArrayAccess) node;
        ArrayAccess aaCopy = (ArrayAccess) copy;

        Expression array = wrappers.unpack(aa.getArray());
        Expression arrayCopy = wrappers.unpack(aaCopy.getArray());
        patchers.patchValue(array, arrayCopy, heap, aaCopy::setArray);

        Expression arrayIndex = wrappers.unpack(aa.getIndex());
        Expression arrayIndexCopy = wrappers.unpack(aaCopy.getIndex());
        patchers.patchValue(arrayIndex, arrayIndexCopy, heap, aaCopy::setIndex);
    }
}
