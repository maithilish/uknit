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
import org.eclipse.jdt.core.dom.InstanceofExpression;

public class InstanceofExpressionSrv implements PatchService {

    @Inject
    private Patchers patchers;
    @Inject
    private Wrappers wrappers;

    @Override
    public void patch(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {
        checkState(node instanceof InstanceofExpression);
        checkState(copy instanceof InstanceofExpression);

        InstanceofExpression inOf = (InstanceofExpression) node;
        InstanceofExpression inOfCopy = (InstanceofExpression) copy;

        /*
         * If leftOper exp is simple that directly maps to a var then patch
         * infixCopy leftOper with var name else if leftOper is complex exp that
         * doesn't map to a var then process it in an appropriate patch service.
         * Similarly right and extended operands.
         */
        Expression leftOper = wrappers.unpack(inOf.getLeftOperand());
        Expression leftOperCopy = wrappers.unpack(inOfCopy.getLeftOperand());
        patchers.patchExpWithName(pack, leftOper, leftOperCopy, heap,
                inOfCopy::setLeftOperand);
    }

    @Override
    public void patchName(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {
        checkState(node instanceof InstanceofExpression);
        checkState(copy instanceof InstanceofExpression);

        InstanceofExpression infix = (InstanceofExpression) node;
        InstanceofExpression infixCopy = (InstanceofExpression) copy;

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
        patchers.patchExpWithPackPatches(leftOper, leftOperCopy, patches, index,
                infixCopy::setLeftOperand);
    }

    @Override
    public List<Expression> getExps(final Expression node) {
        checkState(node instanceof InstanceofExpression);

        InstanceofExpression infix = (InstanceofExpression) node;

        List<Expression> exps = new ArrayList<>();

        exps.add(wrappers.strip(infix.getLeftOperand()));

        return exps;
    }
}
