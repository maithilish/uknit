package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;

import java.util.List;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.Expression;

public class BooleanLiteralSrv implements ExpService {

    @Override
    public List<Expression> getExps(final Expression exp) {
        checkState(exp instanceof BooleanLiteral);
        return List.of(exp);
    }

    @Override
    public Expression getValue(final Expression node, final Pack pack,
            final Heap heap) {
        checkState(node instanceof BooleanLiteral);
        return node;
    }
}
