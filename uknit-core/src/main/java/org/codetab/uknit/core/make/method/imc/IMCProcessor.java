package org.codetab.uknit.core.make.method.imc;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.List;
import java.util.stream.Collectors;

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
        // remove any invoke whose var is field
        invokes = invokes.stream()
                .filter(i -> isNull(i.getVar())
                        || (nonNull(i.getVar()) && !i.getVar().isField()))
                .collect(Collectors.toList());
        List<Invoke> imcInvokes =
                internalCalls.filterInternalInvokes(invokes, heap);
        for (Invoke invoke : imcInvokes) {
            imcInvoker.process(invoke, heap);
        }
    }
}
