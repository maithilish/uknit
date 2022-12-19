package org.codetab.uknit.core.make.method.patch.service;

import static com.google.common.base.Preconditions.checkState;

import java.util.List;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.NumberLiteral;

public class NumberLiteralSrv implements PatchService {

    @Override
    public void patch(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {
        checkState(node instanceof NumberLiteral);
        checkState(copy instanceof NumberLiteral);
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
