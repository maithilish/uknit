package org.codetab.uknit.core.make.method.patch.service;

import static com.google.common.base.Preconditions.checkState;

import java.util.List;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.Expression;

public class BooleanLiteralSrv implements PatchService {

    @Override
    public void patch(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {
        checkState(node instanceof BooleanLiteral);
        checkState(copy instanceof BooleanLiteral);
    }

    @Override
    public List<Expression> getExps(final Expression exp) {
        return List.of(exp);
    }

    @Override
    public void patchName(final Pack pack, final Expression node,
            final Expression copy) {
    }
}
