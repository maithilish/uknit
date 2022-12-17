package org.codetab.uknit.core.make.method.patch.service;

import static com.google.common.base.Preconditions.checkState;

import java.util.Map;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;

public class ParenthesizedExpressionSrv implements PatchService {

    @Inject
    private Patchers patchers;
    @Inject
    private Wrappers wrappers;

    @Override
    public void patch(final Expression node, final Expression copy,
            final Map<Expression, IVar> patches) {

        checkState(node instanceof ParenthesizedExpression);
        checkState(copy instanceof ParenthesizedExpression);

        ParenthesizedExpression ce = (ParenthesizedExpression) node;
        ParenthesizedExpression ceCopy = (ParenthesizedExpression) copy;

        Expression exp = wrappers.unpack(ce.getExpression());
        Expression expCopy = wrappers.unpack(ceCopy.getExpression());
        patchers.patchExpWithName(exp, expCopy, patches, ceCopy::setExpression);
    }
}
