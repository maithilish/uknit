package org.codetab.uknit.core.make.method.verify;

import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Invoke;

public class VerifyProcessor {

    @Inject
    private VerifyCreator verifyCreator;
    @Inject
    private Packs packs;

    public void createVerifys(final Heap heap) {
        List<Invoke> invokes = packs.filterInvokes(heap.getPacks());
        for (Invoke invoke : invokes) {
            verifyCreator.createVerify(invoke, heap);
        }
    }
}
