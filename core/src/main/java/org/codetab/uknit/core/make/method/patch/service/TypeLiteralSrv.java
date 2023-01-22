package org.codetab.uknit.core.make.method.patch.service;

import static com.google.common.base.Preconditions.checkState;

import java.util.List;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.TypeLiteral;

public class TypeLiteralSrv implements PatchService {

    @Override
    public void patch(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {
        checkState(node instanceof TypeLiteral);
        checkState(copy instanceof TypeLiteral);
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
