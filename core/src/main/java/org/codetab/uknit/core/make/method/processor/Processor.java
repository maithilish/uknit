package org.codetab.uknit.core.make.method.processor;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;

public class Processor {

    @Inject
    private InferProcessor inferProcessor;
    @Inject
    private CreationMarker creationMarker;
    @Inject
    private Packs packs;

    public void processInfers(final Heap heap) {
        List<Pack> inferPacks = packs.filterInferPacks(heap.getPacks());
        inferProcessor.createInfers(inferPacks, heap);

        Optional<Pack> returnPackO = packs.getReturnPack(heap.getPacks());
        returnPackO
                .ifPresent(p -> inferProcessor.createInferForReturn(p, heap));

        creationMarker.markCreation(heap.getPacks());
    }
}
