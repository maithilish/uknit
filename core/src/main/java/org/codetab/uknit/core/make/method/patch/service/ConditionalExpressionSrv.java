package org.codetab.uknit.core.make.method.patch.service;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Patch;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.Expression;

public class ConditionalExpressionSrv implements PatchService {

    @Inject
    private Patchers patchers;
    @Inject
    private Wrappers wrappers;

    @Override
    public void patch(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {

        checkState(node instanceof ConditionalExpression);
        checkState(copy instanceof ConditionalExpression);

        ConditionalExpression ce = (ConditionalExpression) node;
        ConditionalExpression ceCopy = (ConditionalExpression) copy;

        Expression exp = wrappers.unpack(ce.getExpression());
        Expression expCopy = wrappers.unpack(ceCopy.getExpression());
        patchers.patchExpWithName(pack, exp, expCopy, heap,
                ceCopy::setExpression);

        Expression thenExp = wrappers.unpack(ce.getThenExpression());
        Expression thenExpCopy = wrappers.unpack(ceCopy.getThenExpression());
        patchers.patchExpWithName(pack, thenExp, thenExpCopy, heap,
                ceCopy::setThenExpression);

        Expression elseExp = wrappers.unpack(ce.getElseExpression());
        Expression elseExpCopy = wrappers.unpack(ceCopy.getElseExpression());
        patchers.patchExpWithName(pack, elseExp, elseExpCopy, heap,
                ceCopy::setElseExpression);

    }

    @Override
    public void patchName(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {
        checkState(node instanceof ConditionalExpression);
        checkState(copy instanceof ConditionalExpression);

        ConditionalExpression ce = (ConditionalExpression) node;
        ConditionalExpression ceCopy = (ConditionalExpression) copy;

        final List<Patch> patches = pack.getPatches();

        final int expIndex = 0;
        Expression exp = wrappers.unpack(ce.getExpression());
        Expression expCopy = wrappers.unpack(ceCopy.getExpression());

        patchers.patchExpWithPackPatches(pack, exp, expCopy, patches, expIndex,
                ceCopy::setExpression);

        final int thenIndex = 1;
        Expression thenExp = wrappers.unpack(ce.getThenExpression());
        Expression thenExpCopy = wrappers.unpack(ceCopy.getThenExpression());
        patchers.patchExpWithPackPatches(pack, thenExp, thenExpCopy, patches,
                thenIndex, ceCopy::setThenExpression);

        final int elseIndex = 3;
        Expression elseExp = wrappers.unpack(ce.getElseExpression());
        Expression elseExpCopy = wrappers.unpack(ceCopy.getElseExpression());
        patchers.patchExpWithPackPatches(pack, elseExp, elseExpCopy, patches,
                elseIndex, ceCopy::setElseExpression);

    }

    @Override
    public List<Expression> getExps(final Expression node) {
        checkState(node instanceof ConditionalExpression);

        ConditionalExpression ce = (ConditionalExpression) node;

        List<Expression> exps = new ArrayList<>();

        exps.add(wrappers.strip(ce.getExpression()));
        exps.add(wrappers.strip(ce.getThenExpression()));
        exps.add(wrappers.strip(ce.getElseExpression()));

        return exps;
    }

    @Override
    public void patchValue(final Expression node, final Expression copy,
            final Heap heap) {
        checkState(node instanceof ConditionalExpression);
        checkState(copy instanceof ConditionalExpression);

        ConditionalExpression ce = (ConditionalExpression) node;
        ConditionalExpression ceCopy = (ConditionalExpression) copy;

        Expression exp = wrappers.unpack(ce.getExpression());
        Expression expCopy = wrappers.unpack(ceCopy.getExpression());
        patchers.patchValue(exp, expCopy, heap, ceCopy::setExpression);

        Expression thenExp = wrappers.unpack(ce.getThenExpression());
        Expression thenExpCopy = wrappers.unpack(ceCopy.getThenExpression());
        patchers.patchValue(thenExp, thenExpCopy, heap,
                ceCopy::setThenExpression);

        Expression elseExp = wrappers.unpack(ce.getElseExpression());
        Expression elseExpCopy = wrappers.unpack(ceCopy.getElseExpression());
        patchers.patchValue(elseExp, elseExpCopy, heap,
                ceCopy::setElseExpression);
    }
}
