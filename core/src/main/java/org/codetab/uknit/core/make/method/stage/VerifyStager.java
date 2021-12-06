package org.codetab.uknit.core.make.method.stage;

import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.visit.AnonymousProcessor;
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
    // @Inject
    // private LambdaProcessor lambdaProcessor;
    // @Inject
    // private UseMarker useMarker;

    public void stageVerify(final MethodInvocation mi,
            final MethodInvocation resolvableMi, final Heap heap) {
        List<ArgCapture> anonCaptures =
                anonymousProcessor.replaceAnonymousArgsWithCaptures(mi, heap);
        // List<ArgCapture> lambdaCaptures = lambdaProcessor
        // .replaceLambdaArgsWithCaptures(mi, resolvableMi, method);

        Verify verify = modelFactory.createVerify(mi);
        verify.addArgCaptures(anonCaptures);
        // verify.addArgCaptures(lambdaCaptures);
        heap.getVerifies().add(verify);
    }
}
