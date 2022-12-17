package org.codetab.uknit.core.make.method.patch.service;

import static com.google.common.base.Preconditions.checkState;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.node.Arguments;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;

public class ClassInstanceCreationSrv implements PatchService {

    @Inject
    private Patchers patchers;
    @Inject
    private Arguments arguments;

    @Override
    public void patch(final Expression node, final Expression copy,
            final Map<Expression, IVar> patches) {

        checkState(node instanceof ClassInstanceCreation);
        checkState(copy instanceof ClassInstanceCreation);

        ClassInstanceCreation cic = (ClassInstanceCreation) node;
        ClassInstanceCreation cicCopy = (ClassInstanceCreation) copy;

        List<Expression> args = arguments.getArgs(cic);
        List<Expression> argsCopy = arguments.getArgs(cicCopy);
        patchers.patchExpsWithName(args, argsCopy, patches);
    }
}
