package org.codetab.uknit.core.make.method.imc;

import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Invoke;

public class IMCProcessor {

    @Inject
    private InternalCallProcessor imcInvoker;
    @Inject
    private InternalCalls internalCalls;
    @Inject
    private Packs packs;

    public void process(final Heap heap) {
        List<Invoke> invokes = packs.filterInvokes(heap.getPacks());
        List<Invoke> imcInvokes =
                internalCalls.filterInternalInvokes(invokes, heap);
        imcInvokes.forEach(invoke -> imcInvoker.process(invoke, heap));
    }
}
