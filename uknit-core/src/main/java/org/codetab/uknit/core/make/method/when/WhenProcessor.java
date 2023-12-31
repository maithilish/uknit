package org.codetab.uknit.core.make.method.when;

import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Invoke;

public class WhenProcessor {

    @Inject
    private WhenCreator whenCreator;
    @Inject
    private Packs packs;

    public void createWhens(final Heap heap) {
        List<Invoke> whenInvokes = whenCreator
                .filterWhenInvokes(packs.filterInvokes(heap.getPacks()), heap);
        for (Invoke invoke : whenInvokes) {
            whenCreator.createWhen(invoke, whenInvokes, heap);
        }
    }
}
