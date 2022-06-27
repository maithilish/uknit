package org.codetab.uknit.core.make.method.stage;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.visit.AnonymousProcessor;
import org.codetab.uknit.core.make.method.visit.LambdaProcessor;
import org.codetab.uknit.core.make.method.visit.Patcher;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.When;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.MethodInvocation;

public class WhenStager {

    @Inject
    private ModelFactory modelFactory;
    @Inject
    private AnonymousProcessor anonymousProcessor;
    @Inject
    private LambdaProcessor lambdaProcessor;
    @Inject
    private VerifyStager verifyStager;
    @Inject
    private Methods methods;
    @Inject
    private Patcher patcher;
    @Inject
    private Nodes nodes;

    public void stageWhen(final Invoke invoke, final IVar var,
            final Heap heap) {

        // don't stage when if call var is real - not mock or by creation node.
        IVar callVar = invoke.getCallVar();
        if (callVar.isCreated() || !callVar.isMock()) {
            return;
        }
        // don't stage if not mi
        if (!nodes.is(invoke.getExp(), MethodInvocation.class)) {
            return;
        }
        /*
         * As when replaces anon and lambda with arg matchers and verify
         * replaces it with captures, we need two copies of mi. Source mi is
         * untouched and used to resolve type binding of lambda. The copy
         * doesn't resolve bindings!
         */
        MethodInvocation mi = nodes.as(invoke.getExp(), MethodInvocation.class);
        MethodInvocation patchedMi = patcher.copyAndPatch(mi, heap);

        boolean anonReplaced =
                anonymousProcessor.patchAnonymousArgs(patchedMi, heap);
        if (anonReplaced) {
            verifyStager.stageVerify(mi, heap);
        }

        boolean lambdaReplaced =
                lambdaProcessor.patchLambdaArgs(patchedMi, mi, heap);
        if (lambdaReplaced) {
            verifyStager.stageVerify(mi, heap);
        }

        String methodSignature = patchedMi.toString();
        Optional<When> w = heap.findWhen(methodSignature);
        When when = null;
        if (w.isPresent()) {
            when = w.get();
        } else {
            when = modelFactory.createWhen(methodSignature);
            heap.getWhens().add(when);
        }
        when.getReturnVars().add(var);

        List<String> usedNames = methods.getNames(patchedMi);
        when.getNames().addAll(usedNames);
    }
}
