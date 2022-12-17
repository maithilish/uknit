package org.codetab.uknit.core.make.method.patch.service;

import static com.google.common.base.Preconditions.checkState;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.node.Arguments;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;

public class InfixExpressionSrv implements PatchService {

    @Inject
    private Patchers patchers;
    @Inject
    private Arguments arguments;
    @Inject
    private Wrappers wrappers;

    @Override
    public void patch(final Expression node, final Expression copy,
            final Map<Expression, IVar> patches) {

        checkState(node instanceof InfixExpression);
        checkState(copy instanceof InfixExpression);

        InfixExpression infix = (InfixExpression) node;
        InfixExpression infixCopy = (InfixExpression) copy;

        /*
         * If leftOper exp is simple that directly maps to a var then patch
         * infixCopy leftOper with var name else if leftOper is complex exp that
         * doesn't map to a var then process it in an appropriate patch service.
         * Similarly right and extended operands.
         */
        Expression leftOper = wrappers.unpack(infix.getLeftOperand());
        Expression leftOperCopy = wrappers.unpack(infixCopy.getLeftOperand());
        patchers.patchExpWithName(leftOper, leftOperCopy, patches,
                infixCopy::setLeftOperand);

        Expression rightOper = wrappers.unpack(infix.getRightOperand());
        Expression rightOperCopy = wrappers.unpack(infixCopy.getRightOperand());
        patchers.patchExpWithName(rightOper, rightOperCopy, patches,
                infixCopy::setRightOperand);

        List<Expression> exOpers = arguments.getExtendedOperands(infix);
        List<Expression> exOpersCopy = arguments.getExtendedOperands(infixCopy);
        patchers.patchExpsWithName(exOpers, exOpersCopy, patches);
    }
}
