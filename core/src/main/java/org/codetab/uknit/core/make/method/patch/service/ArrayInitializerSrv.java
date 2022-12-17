package org.codetab.uknit.core.make.method.patch.service;

import static com.google.common.base.Preconditions.checkState;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.node.Arguments;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Expression;

public class ArrayInitializerSrv implements PatchService {

    @Inject
    private Patchers patchers;
    @Inject
    private Arguments arguments;

    @Override
    public void patch(final Expression node, final Expression copy,
            final Map<Expression, IVar> patches) {

        checkState(node instanceof ArrayInitializer);
        checkState(copy instanceof ArrayInitializer);

        ArrayInitializer ai = (ArrayInitializer) node;
        ArrayInitializer aiCopy = (ArrayInitializer) copy;

        List<Expression> exps = arguments.getExps(ai);
        List<Expression> expsCopy = arguments.getExps(aiCopy);
        patchers.patchExpsWithName(exps, expsCopy, patches);
    }
}
