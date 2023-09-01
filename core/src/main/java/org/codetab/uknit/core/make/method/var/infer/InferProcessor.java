package org.codetab.uknit.core.make.method.var.infer;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;

public class InferProcessor {

    @Inject
    private InferCreator inferCreator;
    @Inject
    private Packs packs;

    public void createInfers(final Heap heap) {
        List<Pack> miPacks =
                packs.filterPacks(heap.getPacks(), MethodInvocation.class,
                        SuperMethodInvocation.class, ArrayAccess.class);
        for (Pack pack : miPacks) {
            inferCreator.createInfer(pack, heap);
        }
    }

    public void createInferForReturn(final Heap heap) {
        Optional<Pack> returnPackO = packs.getReturnPack(heap.getPacks());
        returnPackO.ifPresent(p -> inferCreator.createInferForReturn(p,
                p.isInCtlPath(), heap));
    }
}
