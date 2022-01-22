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

    public void stageWhen(final Invoke invoke, final IVar var,
            final Heap heap) {
        /*
         * As when replaces anon and lambda with arg matchers and verify
         * replaces it with captures, we need two copies of mi. Source mi is
         * untouched and used to resolve type binding of lambda. The copy
         * doesn't resolve bindings!
         */
        MethodInvocation mi = invoke.getMi();
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

        Optional<When> w = heap.findWhen(patchedMi.toString());
        When when = null;
        if (w.isPresent()) {
            when = w.get();
        } else {
            when = modelFactory.createWhen(patchedMi.toString());
            heap.getWhens().add(when);
        }
        when.getReturnVars().add(var);

        List<String> usedNames = methods.getNames(patchedMi);
        when.getNames().addAll(usedNames);
    }
}
