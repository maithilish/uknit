package org.codetab.uknit.core.make.method.patch.service;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Expression;

public class AssignmentSrv implements PatchService {

    @Override
    public void patch(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {
        checkState(node instanceof Assignment);
        checkState(copy instanceof Assignment);
    }

    @Override
    public void patchName(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {
        checkState(node instanceof Assignment);
        checkState(copy instanceof Assignment);
    }

    @Override
    public List<Expression> getExps(final Expression node) {
        checkState(node instanceof Assignment);

        List<Expression> exps = new ArrayList<>();
        Assignment as = (Assignment) node;
        exps.add(as.getLeftHandSide());
        exps.add(as.getRightHandSide());

        return exps;
    }

    @Override
    public void patchValue(final Expression node, final Expression copy,
            final Heap heap) {
        checkState(node instanceof ArrayAccess);
        checkState(copy instanceof ArrayAccess);
    }
}
