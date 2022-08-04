package org.codetab.uknit.core.make.method.stage;

import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.visit.AnonymousProcessor;
import org.codetab.uknit.core.make.method.visit.LambdaProcessor;
import org.codetab.uknit.core.make.method.visit.Patcher;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Verify;
import org.codetab.uknit.core.node.ArgCapture;
import org.eclipse.jdt.core.dom.MethodInvocation;

public class VerifyStager {

    @Inject
    private ModelFactory modelFactory;
    @Inject
    private AnonymousProcessor anonymousProcessor;
    @Inject
    private LambdaProcessor lambdaProcessor;
    @Inject
    private Patcher patcher;

    public void stageVerify(final MethodInvocation mi, final Heap heap) {

        MethodInvocation patchedMi = patcher.copyAndPatch(mi, heap);

        List<ArgCapture> anonCaptures = anonymousProcessor
                .patchAnonymousArgsWithCaptures(patchedMi, heap);
        List<ArgCapture> lambdaCaptures = lambdaProcessor
                .patchLambdaArgsWithCaptures(patchedMi, mi, heap);

        Verify verify =
                modelFactory.createVerify(patchedMi, heap.isInCtlFlowPath());
        verify.getArgCaptures().addAll(anonCaptures);
        verify.getArgCaptures().addAll(lambdaCaptures);
        heap.getVerifies().add(verify);
    }
}
