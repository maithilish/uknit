package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Arguments;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Expression;

public class ArrayInitializerSrv implements ExpService {

    @Inject
    private Arguments arguments;
    @Inject
    private Wrappers wrappers;

    @Override
    public List<Expression> getExps(final Expression node) {
        checkState(node instanceof ArrayInitializer);

        ArrayInitializer ai = (ArrayInitializer) node;

        List<Expression> exps = new ArrayList<>();

        List<Expression> aiExps = arguments.getExps(ai);
        aiExps.forEach(e -> exps.add(wrappers.strip(e)));

        return exps;
    }

    @Override
    public Expression getValue(final Expression node, final Pack pack,
            final Heap heap) {
        // TODO Auto-generated method stub
        return null;
    }
}
