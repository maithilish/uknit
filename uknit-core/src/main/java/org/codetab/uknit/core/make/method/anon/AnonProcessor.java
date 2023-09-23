package org.codetab.uknit.core.make.method.anon;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.make.method.VarNames;
import org.codetab.uknit.core.make.model.ArgCapture;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Type;

/**
 * Common processor for Anonymous inner class and Lambda expressions.
 *
 * @author Maithilish
 *
 */
public class AnonProcessor {

    @Inject
    private Types types;
    @Inject
    private Configs configs;
    @Inject
    private VarNames varNames;
    @Inject
    private Methods methods;
    @Inject
    private Anons anons;
    @Inject
    private ModelFactory modelFactory;

    /**
     * Replace anonymous/lambda args of verify with Argument Captures/Matchers.
     * If uknit.anonymous.class.capture is true then create ArgCapture and
     * substitute arg with capture var name, else replace arg with argument
     * matchers, any() without creating captures. Other args are replaced with
     * eq() matcher. Args in miCopy are replaced and mi (resolvable) is
     * untouched. Ref itest: lambda.LambdaInArgGeneric.java verify stmts.
     *
     * <code>
     *  calcB.op(6, 3, (a, b) -> a * b) becomes
     *  calcB.op(eq(6), eq(3), captorC.capture());
     * </code>
     *
     * @param miCopy
     * @param heap
     * @return list of captures or empty list
     */
    public List<ArgCapture> processVerifyArgs(final MethodInvocation mi,
            final MethodInvocation miCopy, final Heap heap) {

        List<Expression> args = methods.getArguments(mi);
        List<Expression> argCopies = methods.getArguments(miCopy);

        List<ArgCapture> argCaptures = new ArrayList<>();
        List<Integer> unpatchedArgIndexes = new ArrayList<>();
        boolean patched = false;

        for (int i = 0; i < args.size(); i++) {
            Expression arg = args.get(i);

            Type type = anons.getAnonymousType(arg, heap);
            String initializer = null;
            if (nonNull(type)) {
                String typeName = types.getTypeName(type);
                boolean captureArg = configs
                        .getConfig("uknit.anonymous.class.capture", true);
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
                    String fmt = configs
                            .getConfig("uknit.anonymous.arg.substitute", "");
                    initializer = String.format(fmt, typeName);
                }
            }
            if (nonNull(initializer)) {
                Expression argCopy = argCopies.get(i);
                methods.replaceArg(miCopy, argCopy, initializer);
                patched = true;
            } else {
                unpatchedArgIndexes.add(i);
            }
        }

        // if any one arg is patched, then substitute others with matchers.
        if (patched) {
            for (Integer i : unpatchedArgIndexes) {
                String fmt = configs
                        .getConfig("uknit.anonymous.other.arg.substitute", "");
                String initializer = String.format(fmt, args.get(i));
                if (nonNull(initializer)) {
                    methods.replaceArg(miCopy, argCopies.get(i), initializer);
                }
            }
        }
        return argCaptures;
    }

    /**
     * Replaces anonymous/lambda args of when with matchers. Captures are not
     * used in when stmt. The anonymous (ClassInstanceCreation) or lambda is
     * replaced with any(type of arg). Other args are replaced with eq(). Args
     * in miCopy are replaced and mi (resolvable) is untouched. Ref itest:
     * lambda.LambdaInArgGeneric.java when stmts.
     *
     * <code>
     *  calcB.op(6, 3, (a, b) -> a * b) becomes
     *  calcB.op(eq(1), eq(2), any(OperationB.class))
     * </code>
     *
     * @param miCopy
     * @param heap
     * @return true if substituted
     */
    public boolean processWhenArgs(final MethodInvocation mi,
            final MethodInvocation miCopy, final Heap heap) {

        List<Integer> unpatchedArgIndexes = new ArrayList<>();
        boolean patched = false;

        List<Expression> args = methods.getArguments(mi);
        List<Expression> argCopies = methods.getArguments(miCopy);

        for (int i = 0; i < args.size(); i++) {
            Expression arg = args.get(i);
            Type type = anons.getAnonymousType(arg, heap);
            String initializer = null;
            if (nonNull(type)) {
                String typeName = types.getTypeName(type);
                String fmt =
                        configs.getConfig("uknit.anonymous.arg.substitute", "");
                initializer = String.format(fmt, typeName);
            }
            if (nonNull(initializer)) {
                Expression argCopy = argCopies.get(i);
                methods.replaceArg(miCopy, argCopy, initializer);
                patched = true;
            } else {
                unpatchedArgIndexes.add(i);
            }
        }
        // if any one arg is patched, then substitute others with matchers.
        if (patched) {
            for (Integer i : unpatchedArgIndexes) {
                String fmt = configs
                        .getConfig("uknit.anonymous.other.arg.substitute", "");
                String initializer = String.format(fmt, args.get(i));
                if (nonNull(initializer)) {
                    methods.replaceArg(miCopy, argCopies.get(i), initializer);
                }
            }
        }
        return patched;
    }
}
