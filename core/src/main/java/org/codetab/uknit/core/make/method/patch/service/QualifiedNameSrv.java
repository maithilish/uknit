package org.codetab.uknit.core.make.method.patch.service;

import static com.google.common.base.Preconditions.checkState;

import java.util.List;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.QualifiedName;

public class QualifiedNameSrv implements PatchService {

    @Override
    public void patch(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {

        checkState(node instanceof QualifiedName);
        checkState(copy instanceof QualifiedName);

        // QualifiedName fa = (QualifiedName) node;
        // QualifiedName faCopy = (QualifiedName) copy;

        // Expression exp = wrappers.unpack(fa.getExpression());
        // Expression expCopy = wrappers.unpack(faCopy.getExpression());
        // patchers.patchExpWithName(exp, expCopy, patches,
        // faCopy::setExpression);

        // REVIEW what about QualifiedName.getName() and getQualifer
    }

    @Override
    public List<Expression> getExps(final Expression exp) {
        return List.of(exp);
    }

    @Override
    public void patchName(final Pack pack, final Expression node,
            final Expression copy) {
        // TODO Auto-generated method stub

    }
}
