package org.codetab.uknit.core.make.method.process;

import static com.google.common.base.Preconditions.checkState;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.patch.Patcher;
import org.codetab.uknit.core.make.model.ArgCapture;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Verify;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.MethodInvocation;

public class VerifyCreator {

    @Inject
    private ModelFactory modelFactory;
    @Inject
    private AnonymousProcessor anonymousProcessor;
    @Inject
    private LambdaProcessor lambdaProcessor;
    @Inject
    private Patcher patcher;
    @Inject
    private Nodes nodes;
    @Inject
    private Excludes excludes;

    public void createVerify(final Invoke invoke, final Heap heap) {

        checkState(nodes.is(invoke.getExp(), MethodInvocation.class));

        if (excludes.exclude(invoke)) {
            return;
        }

        MethodInvocation mi = nodes.as(invoke.getExp(), MethodInvocation.class);
        MethodInvocation patchedMi = patcher.copyAndPatch(mi, heap);

        boolean inCtlPath = invoke.isInCtlPath();

        List<ArgCapture> anonCaptures = anonymousProcessor
                .patchAnonymousArgsWithCaptures(patchedMi, heap);
        List<ArgCapture> lambdaCaptures = lambdaProcessor
                .patchLambdaArgsWithCaptures(patchedMi, mi, heap);

        Verify verify = modelFactory.createVerify(patchedMi, inCtlPath);
        verify.getArgCaptures().addAll(anonCaptures);
        verify.getArgCaptures().addAll(lambdaCaptures);

        invoke.setVerify(Optional.of(verify));

    }

    static class Excludes {

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

            // when is present, exclude verify
            if (invoke.getWhen().isPresent()) {
                return true;
            }
            return false;
        }
    }
}
