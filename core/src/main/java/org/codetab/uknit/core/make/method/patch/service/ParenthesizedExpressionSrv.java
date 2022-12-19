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
import org.eclipse.jdt.core.dom.ParenthesizedExpression;

public class ParenthesizedExpressionSrv implements PatchService {

    @Inject
    private Patchers patchers;
    @Inject
    private Wrappers wrappers;

    @Override
    public void patch(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {

        checkState(node instanceof ParenthesizedExpression);
        checkState(copy instanceof ParenthesizedExpression);

        ParenthesizedExpression ce = (ParenthesizedExpression) node;
        ParenthesizedExpression ceCopy = (ParenthesizedExpression) copy;

        Expression exp = wrappers.unpack(ce.getExpression());
        Expression expCopy = wrappers.unpack(ceCopy.getExpression());
        patchers.patchExpWithName(pack, exp, expCopy, heap,
                ceCopy::setExpression);
    }

    @Override
    public void patchName(final Pack pack, final Expression node,
            final Expression copy) {
        checkState(node instanceof ParenthesizedExpression);
        checkState(copy instanceof ParenthesizedExpression);

        ParenthesizedExpression ce = (ParenthesizedExpression) node;
        ParenthesizedExpression ceCopy = (ParenthesizedExpression) copy;

        final List<Patch> patches = pack.getPatches();

        int index = 0;
        Expression exp = wrappers.unpack(ce.getExpression());
        Expression expCopy = wrappers.unpack(ceCopy.getExpression());
        patchers.patchExpWithName(exp, expCopy, patches, index,
                ceCopy::setExpression);
    }

    @Override
    public List<Expression> getExps(final Expression node) {
        checkState(node instanceof ParenthesizedExpression);

        ParenthesizedExpression pe = (ParenthesizedExpression) node;

        List<Expression> exps = new ArrayList<>();

        exps.add(wrappers.strip(pe.getExpression()));

        return exps;
    }
}
