package org.codetab.uknit.core.make.method.process;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.make.method.VarNames;
import org.codetab.uknit.core.make.model.ArgCapture;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Type;

public class AnonymousProcessor {

    @Inject
    private Types types;
    @Inject
    private Configs configs;
    @Inject
    private VarNames varNames;
    @Inject
    private Methods methods;
    @Inject
    private Nodes nodes;
    @Inject
    private ModelFactory modelFactory;

    public Optional<String> getStepin(final ClassInstanceCreation cic) {
        Optional<String> initializer = Optional.empty();
        if (nonNull(cic.getAnonymousClassDeclaration())) {
            initializer = Optional.of("STEPIN");
        }
        return initializer;
    }

    /**
     * Replace anonymous class in args with Argument Captures. If
     * uknit.anonymous.class.capture is true then create ArgCapture and
     * substitute arg with capture var name, if false then replace arg with
     * argument matchers, any() without creating captures.
     * <p>
     * don't pass source mi instead pass copy of mi
     * @param miCopy
     * @param heap
     * @return list of captures or empty list
     */
    public List<ArgCapture> patchAnonymousArgsWithCaptures(
            final MethodInvocation miCopy, final Heap heap) {
        List<Expression> args = methods.getArguments(miCopy);
        List<ArgCapture> argCaptures = new ArrayList<>();
        for (Expression arg : args) {
            if (nodes.is(arg, ClassInstanceCreation.class)) {
                ClassInstanceCreation cic =
                        nodes.as(arg, ClassInstanceCreation.class);
                if (nonNull(cic.getAnonymousClassDeclaration())) {
                    Type type = cic.getType();
                    String typeName = types.getTypeName(type);
                    boolean captureArg = configs
                            .getConfig("uknit.anonymous.class.capture", true);
                    String initializer;
                    if (captureArg) {
                        String captureVar = varNames.getCaptureVarName();
                        String fmt = configs.getConfig(
                                "uknit.anonymous.class.capture.format",
                                "%s.capture()");
                        initializer = String.format(fmt, captureVar);
                        ArgCapture argCapture =
                                modelFactory.createArgCapture(captureVar, type);
                        argCaptures.add(argCapture);
                    } else {
                        String fmt = configs.getConfig(
                                "uknit.anonymous.class.substitute", "");
                        initializer = String.format(fmt, typeName);
                    }
                    if (nonNull(initializer)) {
                        methods.replaceArg(miCopy, arg, initializer);
                    }
                }
            }
        }
        return argCaptures;
    }

    /**
     * Replace anonymous class without arg capture normally with arg matchers.
     * <p>
     * don't pass source mi instead pass copy of mi
     * @param miCopy
     * @param heap
     * @return true if substituted
     */
    public boolean patchAnonymousArgs(final MethodInvocation miCopy,
            final Heap heap) {
        boolean replaced = false;
        List<Expression> args = methods.getArguments(miCopy);
        for (Expression arg : args) {
            if (nodes.is(arg, ClassInstanceCreation.class)) {
                ClassInstanceCreation cic =
                        nodes.as(arg, ClassInstanceCreation.class);
                if (nonNull(cic.getAnonymousClassDeclaration())) {
                    Type type = cic.getType();
                    String typeName = types.getTypeName(type);
                    String fmt = configs
                            .getConfig("uknit.anonymous.class.substitute", "");
                    String initializer = String.format(fmt, typeName);
                    if (nonNull(initializer)) {
                        methods.replaceArg(miCopy, arg, initializer);
                        replaced = true;
                    }
                }
            }
        }
        return replaced;
    }
}
