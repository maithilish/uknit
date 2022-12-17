package org.codetab.uknit.core.make.method.patch.service;

import static com.google.common.base.Preconditions.checkState;

import java.util.Map;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.Expression;

public class ConditionalExpressionSrv implements PatchService {

    @Inject
    private Patchers patchers;
    @Inject
    private Wrappers wrappers;

    @Override
    public void patch(final Expression node, final Expression copy,
            final Map<Expression, IVar> patches) {

        checkState(node instanceof ConditionalExpression);
        checkState(copy instanceof ConditionalExpression);

        ConditionalExpression ce = (ConditionalExpression) node;
        ConditionalExpression ceCopy = (ConditionalExpression) copy;

        Expression exp = wrappers.unpack(ce.getExpression());
        Expression expCopy = wrappers.unpack(ceCopy.getExpression());
        patchers.patchExpWithName(exp, expCopy, patches, ceCopy::setExpression);

        Expression thenExp = wrappers.unpack(ce.getThenExpression());
        Expression thenExpCopy = wrappers.unpack(ceCopy.getThenExpression());
        patchers.patchExpWithName(thenExp, thenExpCopy, patches,
                ceCopy::setThenExpression);

        Expression elseExp = wrappers.unpack(ce.getElseExpression());
        Expression elseExpCopy = wrappers.unpack(ceCopy.getElseExpression());
        patchers.patchExpWithName(elseExp, elseExpCopy, patches,
                ceCopy::setElseExpression);

    }
}
