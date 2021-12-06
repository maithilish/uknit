package org.codetab.uknit.core.make.method.stage;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.visit.AnonymousProcessor;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.When;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.MethodInvocation;

public class WhenStager {

    @Inject
    private Nodes nodes;
    @Inject
    private NodeFactory nodeFactory;
    @Inject
    private ModelFactory modelFactory;
    @Inject
    private AnonymousProcessor anonymousProcessor;
    @Inject
    private VerifyStager verifyStager;
    @Inject
    private Methods methods;

    public void stageWhen(final Invoke invoke, final IVar var,
            final Heap heap) {
        /*
         * As when replaces anon and lambda with arg matchers and verify
         * replaces it with captures, we need two copies of mi. Source mi is
         * untouched and used to resolve type binding of lambda. The copy
         * doesn't resolve bindings!
         */
        MethodInvocation mi = invoke.getMi();
        MethodInvocation resolvableMi = mi;
        MethodInvocation miWhenCopy = nodeFactory.copyNode(mi);
        MethodInvocation miVerifyCopy = nodeFactory.copyNode(mi);
        boolean anonReplaced =
                anonymousProcessor.replaceAnonymousArgs(miWhenCopy, heap);
        if (anonReplaced) {
            verifyStager.stageVerify(miVerifyCopy, resolvableMi, heap);
        }
        // TODO - Fix this
        // boolean lambdaReplaced =
        // lambdaProcessor.replaceLambdaArgs(miWhenCopy,
        // resolvableMi, heap);
        // if (lambdaReplaced) {
        // verifyStager.stageVerify(miVerifyCopy, resolvableMi, heap);
        // }

        Optional<When> w = heap.findWhen(miWhenCopy.toString());
        When when = null;
        if (w.isPresent()) {
            when = w.get();
        } else {
            when = modelFactory.createWhen(miWhenCopy.toString());
            heap.getWhens().add(when);
        }
        when.getReturnNames().add(var.getName());

        List<String> usedNames = methods.getNames(miWhenCopy);
        when.getNames().addAll(usedNames);
        // var.setUsed(true);
    }
}
