package org.codetab.uknit.core.make.method.patch.service;

import static com.google.common.base.Preconditions.checkState;

import java.util.Map;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.PrefixExpression;

public class PrefixExpressionSrv implements PatchService {

    @Inject
    private Patchers patchers;
    @Inject
    private Wrappers wrappers;

    @Override
    public void patch(final Expression node, final Expression copy,
            final Map<Expression, IVar> patches) {

        checkState(node instanceof PrefixExpression);
        checkState(copy instanceof PrefixExpression);

        PrefixExpression pfix = (PrefixExpression) node;
        PrefixExpression pfixCopy = (PrefixExpression) copy;

        Expression oper = wrappers.unpack(pfix.getOperand());
        Expression operCopy = wrappers.unpack(pfixCopy.getOperand());
        patchers.patchExpWithName(oper, operCopy, patches,
                pfixCopy::setOperand);
    }
}
