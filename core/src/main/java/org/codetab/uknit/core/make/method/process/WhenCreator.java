package org.codetab.uknit.core.make.method.process;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.patch.Patcher;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.When;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Type;

public class WhenCreator {

    @Inject
    private ModelFactory modelFactory;
    @Inject
    private AnonymousProcessor anonymousProcessor;
    @Inject
    private LambdaProcessor lambdaProcessor;
    @Inject
    private VerifyCreator verifyCreator;
    @Inject
    private Methods methods;
    @Inject
    private Patcher patcher;
    @Inject
    private Nodes nodes;
    @Inject
    private Excludes excludes;

    public void createWhen(final Invoke invoke, final Heap heap) {

        // IMC return may replace invoke exp MI with return var name
        if (!nodes.is(invoke.getExp(), MethodInvocation.class)) {
            return;
        }

        if (excludes.exclude(invoke)) {
            return;
        }

        IVar whenReturnVar = invoke.getVar();
        Optional<IVar> callVarO = invoke.getCallVar();

        /*
         * As when replaces anon and lambda with arg matchers and verify
         * replaces it with captures, we need two copies of mi. Source mi is
         * untouched and used to resolve type binding of lambda. The copy
         * doesn't resolve bindings!
         */
        MethodInvocation mi = nodes.as(invoke.getExp(), MethodInvocation.class);
        MethodInvocation patchedMi =
                (MethodInvocation) patcher.copyAndPatch(invoke, heap);

        boolean anonReplaced =
                anonymousProcessor.patchAnonymousArgs(patchedMi, heap);
        if (anonReplaced) {
            verifyCreator.createVerify(invoke, heap);
        }

        boolean lambdaReplaced =
                lambdaProcessor.patchLambdaArgs(patchedMi, mi, heap);
        if (lambdaReplaced) {
            verifyCreator.createVerify(invoke, heap);
        }

        /*
         * if we are inCtlFlowPath then stage when else stage verify to create
         * verify with never().
         */
        if (invoke.isInCtlPath()) {
            String methodSignature = patchedMi.toString();
            if (invoke.getWhen().isEmpty()) {
                When when = modelFactory.createWhen(methodSignature,
                        callVarO.get());
                invoke.setWhen(Optional.of(when));
            }

            When when = invoke.getWhen().get();
            when.getReturnVars().add(whenReturnVar);

            List<String> usedNames = methods.getNames(patchedMi);
            when.getNames().addAll(usedNames);

        } else {
            verifyCreator.createVerify(invoke, heap);
        }
    }

    static class Excludes {

        @Inject
        private Types types;

        public boolean exclude(final Invoke invoke) {
            Optional<IVar> callVarO = invoke.getCallVar();
            if (callVarO.isEmpty()) {
                return true;
            }

            IVar callVar = callVarO.get();
            // exclude if created
            if (callVar.isCreated()) {
                return true;
            }
            // exclude if real
            if (!callVar.isMock()) {
                return true;
            }

            // returnType void, exclude
            if (invoke.getReturnType().isPresent()) {
                Type type = invoke.getReturnType().get().getType();
                if (!types.capableToReturnValue(type)) {
                    return true;
                }
            }
            return false;
        }
    }
}
