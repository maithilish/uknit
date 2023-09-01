package org.codetab.uknit.core.make.method.process;

import static java.util.Objects.nonNull;

import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.CriticalException;
import org.codetab.uknit.core.make.exp.ExpManager;
import org.codetab.uknit.core.make.method.imc.Merger;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.StringLiteral;

public class PostProcessor {

    @Inject
    private Processor processor;
    @Inject
    private Merger merger;
    @Inject
    private ExpManager expMan;
    @Inject
    private Nodes nodes;

    // REVIEW - remove this
    private void display(final Expression exp) {
        if (!nodes.is(exp, SimpleName.class, StringLiteral.class)) {
            Expression uExp = expMan.unparenthesize(exp);
            System.out.println(exp + "          " + uExp);
        }
    }

    public void process(final Heap heap) {

        // REVIEW remove this
        boolean show = false;
        if (show) {
            for (Pack pack : heap.getPacks()) {
                Expression exp = pack.getExp();
                if (nonNull(exp) && nodes.is(exp, MethodInvocation.class)) {
                    MethodInvocation mi = (MethodInvocation) exp;
                    // display(mi);
                    display(mi.getExpression());
                    for (Object e : mi.arguments()) {
                        display((Expression) e);
                    }
                }
            }
            throw new CriticalException("");
        } else {

            processor.processInfers(heap);

            List<IVar> reassignedVars = processor.processVarReassign(heap);
            processor.updateVarReassign(reassignedVars, heap);

            processor.processVarPatches(heap);

            processor.processIM(heap);

            processor.processVars(heap);
            processor.processInvokes(heap);

            processor.processEnhancedFor(heap);
            processor.processLoads(heap);

            processor.processOfflimits(heap);

            processor.processInitializer(heap);

            processor.processWhenVerify(heap);

            processor.processVarState(heap);
        }
    }

    public void processInternalMethod(final Invoke invoke,
            final Heap internalHeap, final Heap heap) {
        /*
         * process infer, returnInfer, IMC etc., but when, verify and var state
         * are not processed here and these are later processed by caller.
         */
        processor.processInfers(internalHeap);

        List<IVar> reassignedVars = processor.processVarReassign(internalHeap);
        processor.updateVarReassign(reassignedVars, internalHeap);

        processor.processVarPatches(internalHeap);

        processor.processIM(internalHeap);

        processor.processVars(internalHeap);

        // merge packs of internal heap to the caller heap.
        merger.merge(invoke, heap, internalHeap);

        merger.processInvokes(internalHeap);
        processor.processEnhancedFor(internalHeap);
        merger.mergeLoader(heap, internalHeap);

        // after merge, resolve any var name conflict
        processor.processVarPatches(internalHeap);
        merger.mergePatcher(heap, internalHeap);
    }
}
