package org.codetab.uknit.core.make.method.patch.service;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.CreationReference;
import org.eclipse.jdt.core.dom.Expression;

public class CreationReferenceSrv implements PatchService {

    @Override
    public void patch(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {
        checkState(node instanceof CreationReference);
        checkState(copy instanceof CreationReference);
    }

    @Override
    public void patchName(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {
        checkState(node instanceof CreationReference);
        checkState(copy instanceof CreationReference);
    }

    @Override
    public List<Expression> getExps(final Expression node) {
        checkState(node instanceof CreationReference);
        List<Expression> exps = new ArrayList<>();
        return exps;
    }

    @Override
    public void patchValue(final Expression node, final Expression copy,
            final Heap heap) {
        checkState(node instanceof ArrayAccess);
        checkState(copy instanceof ArrayAccess);
    }
}
