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
import org.eclipse.jdt.core.dom.PostfixExpression;

public class PostfixExpressionSrv implements PatchService {

    @Inject
    private Patchers patchers;
    @Inject
    private Wrappers wrappers;

    @Override
    public void patch(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {

        checkState(node instanceof PostfixExpression);
        checkState(copy instanceof PostfixExpression);

        PostfixExpression pfix = (PostfixExpression) node;
        PostfixExpression pfixCopy = (PostfixExpression) copy;

        Expression oper = wrappers.unpack(pfix.getOperand());
        Expression operCopy = wrappers.unpack(pfixCopy.getOperand());
        patchers.patchExpWithName(pack, oper, operCopy, heap,
                pfixCopy::setOperand);
    }

    @Override
    public void patchName(final Pack pack, final Expression node,
            final Expression copy) {
        checkState(node instanceof PostfixExpression);
        checkState(copy instanceof PostfixExpression);

        PostfixExpression pfix = (PostfixExpression) node;
        PostfixExpression pfixCopy = (PostfixExpression) copy;

        final List<Patch> patches = pack.getPatches();

        int index = 0;
        Expression oper = wrappers.unpack(pfix.getOperand());
        Expression operCopy = wrappers.unpack(pfixCopy.getOperand());
        patchers.patchExpWithName(oper, operCopy, patches, index,
                pfixCopy::setOperand);
    }

    @Override
    public List<Expression> getExps(final Expression node) {
        checkState(node instanceof PostfixExpression);

        PostfixExpression pfix = (PostfixExpression) node;

        List<Expression> exps = new ArrayList<>();

        exps.add(wrappers.strip(pfix.getOperand()));

        return exps;
    }

}
