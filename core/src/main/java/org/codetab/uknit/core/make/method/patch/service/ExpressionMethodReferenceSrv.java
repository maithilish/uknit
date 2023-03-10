package org.codetab.uknit.core.make.method.patch.service;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;

import org.codetab.uknit.core.exception.CodeException;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionMethodReference;

public class ExpressionMethodReferenceSrv implements PatchService {

    @Override
    public void patch(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {
        checkState(node instanceof ExpressionMethodReference);
        checkState(copy instanceof ExpressionMethodReference);

        if (pack.getPatches().size() > 0) {
            throw new CodeException(
                    "ExpressionMethodRef has patches, not implemented");
        }
    }

    @Override
    public void patchName(final Pack pack, final Expression node,
            final Expression copy) {

        checkState(node instanceof ExpressionMethodReference);
        checkState(copy instanceof ExpressionMethodReference);

        if (pack.getPatches().size() > 0) {
            throw new CodeException(
                    "ExpressionMethodRef has patches, not implemented");
        }
    }

    @Override
    public List<Expression> getExps(final Expression exp) {
        checkState(exp instanceof ExpressionMethodReference);

        ExpressionMethodReference emr = (ExpressionMethodReference) exp;
        List<Expression> exps = new ArrayList<>();
        exps.add(emr.getName());
        exps.add(emr.getExpression());
        return List.of(exp);
    }

}
