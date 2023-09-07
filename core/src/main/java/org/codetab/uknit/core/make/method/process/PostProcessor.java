package org.codetab.uknit.core.make.method.process;

import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.imc.Merger;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Invoke;

public class PostProcessor {

    @Inject
    private Processor processor;
    @Inject
    private Merger merger;

    public void process(final Heap heap) {

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
        processor.processOfflimitLoads(heap);

        processor.processInitializer(heap);

        processor.processWhenVerify(heap);

        processor.processVarState(heap);
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
