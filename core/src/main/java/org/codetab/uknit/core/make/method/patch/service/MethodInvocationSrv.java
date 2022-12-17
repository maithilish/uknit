package org.codetab.uknit.core.make.method.patch.service;

import static com.google.common.base.Preconditions.checkState;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.node.Arguments;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;

public class MethodInvocationSrv implements PatchService {

    @Inject
    private Arguments arguments;
    @Inject
    private Patchers patchers;
    @Inject
    private Wrappers wrappers;

    @Override
    public void patch(final Expression node, final Expression copy,
            final Map<Expression, IVar> patches) {

        checkState(node instanceof MethodInvocation);
        checkState(copy instanceof MethodInvocation);

        MethodInvocation mi = (MethodInvocation) node;
        MethodInvocation miCopy = (MethodInvocation) copy;

        Expression exp = wrappers.unpack(mi.getExpression());
        Expression expCopy = wrappers.unpack(mi.getExpression());
        patchers.patchExpWithName(exp, expCopy, patches, miCopy::setExpression);

        List<Expression> args = arguments.getArgs(mi);
        List<Expression> argsCopy = arguments.getArgs(miCopy);
        patchers.patchExpsWithName(args, argsCopy, patches);
    }
}
