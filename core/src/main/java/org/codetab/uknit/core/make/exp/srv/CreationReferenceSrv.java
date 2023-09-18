package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.eclipse.jdt.core.dom.CreationReference;
import org.eclipse.jdt.core.dom.Expression;

public class CreationReferenceSrv implements ExpService {

    @Override
    public List<Expression> getExps(final Expression node) {
        checkState(node instanceof CreationReference);
        List<Expression> exps = new ArrayList<>();
        return exps;
    }

    @Override
    public Expression unparenthesize(final Expression node) {
        checkState(node instanceof CreationReference);
        return node;
    }

    @Override
    public Expression getValue(final Expression node, final Expression copy,
            final Pack pack, final boolean createValue, final Heap heap) {
        checkState(node instanceof CreationReference);
        return node;
    }

    @Override
    public <T extends Expression> T rejig(final T node, final Heap heap) {
        checkState(node instanceof CreationReference);
        return node;
    }

}
