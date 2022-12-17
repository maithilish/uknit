package org.codetab.uknit.core.make.method.patch.service;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.node.Arguments;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.Expression;

public class ArrayCreationSrv implements PatchService {

    @Inject
    private Patchers patchers;
    @Inject
    private Arguments arguments;

    @Override
    public void patch(final Expression node, final Expression copy,
            final Map<Expression, IVar> patches) {

        checkState(node instanceof ArrayCreation);
        checkState(copy instanceof ArrayCreation);

        ArrayCreation ac = (ArrayCreation) node;
        ArrayCreation acCopy = (ArrayCreation) copy;

        // patch dims
        List<Expression> dims = arguments.getDims(ac);
        List<Expression> dimsCopy = arguments.getDims(acCopy);
        patchers.patchExpsWithName(dims, dimsCopy, patches);

        // patch initializer exps
        if (nonNull(ac.getInitializer())) {
            List<Expression> inExps = arguments.getExps(ac.getInitializer());
            List<Expression> inExpsCopy =
                    arguments.getExps(acCopy.getInitializer());
            patchers.patchExpsWithName(inExps, inExpsCopy, patches);
        }
    }
}
