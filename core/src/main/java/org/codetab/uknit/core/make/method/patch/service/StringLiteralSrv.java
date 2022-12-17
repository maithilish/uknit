package org.codetab.uknit.core.make.method.patch.service;

import static com.google.common.base.Preconditions.checkState;

import java.util.Map;

import org.codetab.uknit.core.make.model.IVar;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.StringLiteral;

public class StringLiteralSrv implements PatchService {

    @Override
    public void patch(final Expression node, final Expression copy,
            final Map<Expression, IVar> patches) {

        checkState(node instanceof StringLiteral);
        checkState(copy instanceof StringLiteral);

        // REVIEW - implement this after var patch
    }
}
