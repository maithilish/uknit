package org.codetab.uknit.core.make.method.visit;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.make.Variables;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.node.ArgCapture;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Type;

public class LambdaProcessor {

    @Inject
    private Types types;
    @Inject
    private Configs configs;
    @Inject
    private Variables variables;
    @Inject
    private Methods methods;
    @Inject
    private Nodes nodes;
    @Inject
    private ModelFactory modelFactory;

    public List<ArgCapture> replaceLambdaArgsWithCaptures(
            final MethodInvocation mi, final MethodInvocation resolveableMi,
            final Heap heap) {

        List<ArgCapture> argCaptures = new ArrayList<>();

        List<Expression> args = methods.getArguments(mi);
        List<Expression> resolvableArgs = methods.getArguments(resolveableMi);

        for (int i = 0; i < resolvableArgs.size(); i++) {
            Expression resolvableArg = resolvableArgs.get(i);
            if (nodes.is(resolvableArg, LambdaExpression.class)) {
                LambdaExpression lambdaExp =
                        nodes.as(resolvableArg, LambdaExpression.class);
                Type type = types.getType(lambdaExp.resolveTypeBinding(),
                        lambdaExp.getAST());
                String typeName = types.getTypeName(type);
                boolean captureArg = configs
                        .getConfig("uknit.anonymous.class.capture", true);
                String initializer;
                if (captureArg) {
                    String captureVar = variables.getCaptureVarName();
                    String fmt = configs.getConfig(
                            "uknit.anonymous.class.capture.format",
                            "%s.capture()");
                    initializer = String.format(fmt, captureVar);
                    ArgCapture argCapture =
                            modelFactory.createArgCapture(captureVar, type);
                    argCaptures.add(argCapture);
                } else {
                    String fmt = configs
                            .getConfig("uknit.anonymous.class.substitute", "");
                    initializer = String.format(fmt, typeName);
                }
                if (nonNull(initializer)) {
                    // replace in mi (the copy) and in resolvableMi
                    methods.replaceArg(mi, args.get(i), initializer);
                }
            }
        }
        return argCaptures;
    }

    public boolean replaceLambdaArgs(final MethodInvocation mi,
            final MethodInvocation resolveableMi, final Heap heap) {
        boolean replaced = false;
        List<Expression> args = methods.getArguments(mi);
        List<Expression> resolvableArgs = methods.getArguments(resolveableMi);
        for (int i = 0; i < resolvableArgs.size(); i++) {
            Expression resolvableArg = resolvableArgs.get(i);
            if (nodes.is(resolvableArg, LambdaExpression.class)) {
                LambdaExpression lambdaExp =
                        nodes.as(resolvableArg, LambdaExpression.class);
                Type type = types.getType(lambdaExp.resolveTypeBinding(),
                        lambdaExp.getAST());
                String typeName = types.getTypeName(type);
                String fmt = configs
                        .getConfig("uknit.anonymous.class.substitute", "");
                String initializer = String.format(fmt, typeName);
                if (nonNull(initializer)) {
                    // replace in mi (the copy) and in resolvableMi
                    methods.replaceArg(mi, args.get(i), initializer);
                    replaced = true;
                }
            }
        }
        return replaced;
    }
}
