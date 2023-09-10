package org.codetab.uknit.core.make.method.patch.service;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Patch;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.ThisExpression;

public class ThisExpressionSrv implements PatchService {

    @Inject
    private Patchers patchers;
    @Inject
    private Wrappers wrappers;

    @Override
    public void patch(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {

        checkState(node instanceof ThisExpression);
        checkState(copy instanceof ThisExpression);

        ThisExpression te = (ThisExpression) node;
        ThisExpression teCopy = (ThisExpression) copy;

        Expression qualifer = wrappers.unpack(te.getQualifier());
        Expression qualifierCopy = wrappers.unpack(teCopy.getQualifier());
        patchers.patchExpWithName(pack, qualifer, qualifierCopy, heap,
                n -> teCopy.setQualifier((Name) n));

    }

    @Override
    public void patchName(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {
        checkState(node instanceof ThisExpression);
        checkState(copy instanceof ThisExpression);

        ThisExpression te = (ThisExpression) node;
        ThisExpression teCopy = (ThisExpression) copy;

        final List<Patch> patches = pack.getPatches();

        int index = 0;
        Expression qualifer = wrappers.unpack(te.getQualifier());
        Expression qualifierCopy = wrappers.unpack(teCopy.getQualifier());
        patchers.patchExpWithPackPatches(pack, qualifer, qualifierCopy, patches,
                index, n -> teCopy.setQualifier((Name) n));
    }

    @Override
    public List<Expression> getExps(final Expression node) {
        checkState(node instanceof ThisExpression);

        List<Expression> exps = new ArrayList<>();

        ThisExpression te = (ThisExpression) node;
        exps.add(wrappers.strip(te.getQualifier()));

        return exps;
    }

    @Override
    public void patchValue(final Expression node, final Expression copy,
            final Heap heap) {
        checkState(node instanceof ThisExpression);
        checkState(copy instanceof ThisExpression);
    }
}
