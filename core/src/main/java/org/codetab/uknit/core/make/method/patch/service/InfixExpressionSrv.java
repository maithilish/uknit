package org.codetab.uknit.core.make.method.patch.service;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Patch;
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
    public void patch(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {
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
        patchers.patchExpWithName(pack, leftOper, leftOperCopy, heap,
                infixCopy::setLeftOperand);

        Expression rightOper = wrappers.unpack(infix.getRightOperand());
        Expression rightOperCopy = wrappers.unpack(infixCopy.getRightOperand());
        patchers.patchExpWithName(pack, rightOper, rightOperCopy, heap,
                infixCopy::setRightOperand);

        List<Expression> exOpers = arguments.getExtendedOperands(infix);
        List<Expression> exOpersCopy = arguments.getExtendedOperands(infixCopy);
        patchers.patchExpsWithName(pack, exOpers, exOpersCopy, heap);
    }

    @Override
    public void patchName(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {
        checkState(node instanceof InfixExpression);
        checkState(copy instanceof InfixExpression);

        InfixExpression infix = (InfixExpression) node;
        InfixExpression infixCopy = (InfixExpression) copy;

        final List<Patch> patches = pack.getPatches();

        /*
         * If leftOper exp is simple that directly maps to a var then patch
         * infixCopy leftOper with var name else if leftOper is complex exp that
         * doesn't map to a var then process it in an appropriate patch service.
         * Similarly right and extended operands.
         */
        int index = 0;
        Expression leftOper = wrappers.unpack(infix.getLeftOperand());
        Expression leftOperCopy = wrappers.unpack(infixCopy.getLeftOperand());
        patchers.patchExpWithPackPatches(pack, leftOper, leftOperCopy, patches,
                index, infixCopy::setLeftOperand);

        index = 1;
        Expression rightOper = wrappers.unpack(infix.getRightOperand());
        Expression rightOperCopy = wrappers.unpack(infixCopy.getRightOperand());
        patchers.patchExpWithPackPatches(pack, rightOper, rightOperCopy,
                patches, index, infixCopy::setRightOperand);

        int offset = 2;
        List<Expression> exOpers = arguments.getExtendedOperands(infix);
        List<Expression> exOpersCopy = arguments.getExtendedOperands(infixCopy);
        patchers.patchExpsWithPackPatches(pack, exOpers, exOpersCopy, patches,
                offset, heap);
    }

    @Override
    public List<Expression> getExps(final Expression node) {
        checkState(node instanceof InfixExpression);

        InfixExpression infix = (InfixExpression) node;

        List<Expression> exps = new ArrayList<>();

        exps.add(wrappers.strip(infix.getLeftOperand()));
        exps.add(wrappers.strip(infix.getRightOperand()));

        List<Expression> exOpers = arguments.getExtendedOperands(infix);
        exOpers.forEach(eo -> exps.add(wrappers.strip(eo)));
        return exps;
    }

    // REVIEW - write test
    @Override
    public void patchValue(final Expression node, final Expression copy,
            final Heap heap) {
        checkState(node instanceof InfixExpression);
        checkState(copy instanceof InfixExpression);

        InfixExpression infix = (InfixExpression) node;
        InfixExpression infixCopy = (InfixExpression) copy;

        Expression leftOper = wrappers.unpack(infix.getLeftOperand());
        Expression leftOperCopy = wrappers.unpack(infixCopy.getLeftOperand());
        patchers.patchValue(leftOper, leftOperCopy, heap,
                infixCopy::setLeftOperand);

        Expression rightOper = wrappers.unpack(infix.getRightOperand());
        Expression rightOperCopy = wrappers.unpack(infixCopy.getRightOperand());
        patchers.patchValue(rightOper, rightOperCopy, heap,
                infixCopy::setRightOperand);

        List<Expression> exOpers = arguments.getExtendedOperands(infix);
        List<Expression> exOpersCopy = arguments.getExtendedOperands(infixCopy);
        patchers.patchValue(exOpers, exOpersCopy, heap);
    }
}
