package org.codetab.uknit.core.make.method.patch.service;

import static com.google.common.base.Preconditions.checkState;

import java.util.Map;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;

public class FieldAccessSrv implements PatchService {

    @Inject
    private Patchers patchers;
    @Inject
    private Wrappers wrappers;

    @Override
    public void patch(final Expression node, final Expression copy,
            final Map<Expression, IVar> patches) {

        checkState(node instanceof FieldAccess);
        checkState(copy instanceof FieldAccess);

        FieldAccess fa = (FieldAccess) node;
        FieldAccess faCopy = (FieldAccess) copy;

        Expression exp = wrappers.unpack(fa.getExpression());
        Expression expCopy = wrappers.unpack(faCopy.getExpression());
        patchers.patchExpWithName(exp, expCopy, patches, faCopy::setExpression);

        // REVIEW what about FieldAccess.getName()
    }
}
