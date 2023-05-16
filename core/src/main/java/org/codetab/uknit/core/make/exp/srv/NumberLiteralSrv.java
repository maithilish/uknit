package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;

import java.util.List;

import org.codetab.uknit.core.make.model.Heap;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.NumberLiteral;

public class NumberLiteralSrv implements ExpService {

    @Override
    public List<Expression> getExps(final Expression exp) {
        checkState(exp instanceof NumberLiteral);
        return List.of(exp);
    }

    @Override
    public Expression getValue(final Expression node, final Heap heap) {
        return node;
    }
}
