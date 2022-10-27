package org.codetab.uknit.core.make.method.process;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.Pack;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;

public class Processor {

    @Inject
    private InferProcessor inferProcessor;
    @Inject
    private PatchProcessor patchProcessor;
    @Inject
    private VarProcessor varProcessor;
    @Inject
    private WhenProcessor whenProcessor;
    @Inject
    private VerifyProcessor verifyProcessor;

    public void process(final Heap heap) {

        inferProcessor.createInfers(heap);
        inferProcessor.createInferForReturn(heap);

        patchProcessor.createPatches(heap);

        varProcessor.markCreation(heap);

        whenProcessor.createWhens(heap);

        verifyProcessor.createVerifys(heap);
    }
}

class InferProcessor {

    @Inject
    private InferCreator inferCreator;
    @Inject
    private Packs packs;

    public void createInfers(final Heap heap) {
        List<Pack> miPacks =
                packs.filterPacks(heap.getPacks(), MethodInvocation.class);
        miPacks.forEach(pack -> inferCreator.createInfer(pack, heap));
    }

    public void createInferForReturn(final Heap heap) {
        Optional<Pack> returnPackO = packs.getReturnPack(heap.getPacks());
        returnPackO.ifPresent(p -> inferCreator.createInferForReturn(p,
                p.isInCtlPath(), heap));
    }
}

class PatchProcessor {
    @Inject
    private PatchCreator patchCreator;
    @Inject
    private Packs packs;

    public void createPatches(final Heap heap) {
        List<Pack> packList = packs.filterPacks(heap.getPacks(),
                MethodInvocation.class, ClassInstanceCreation.class,
                ArrayCreation.class, InfixExpression.class);
        packList.forEach(pack -> patchCreator.createInvokePatch(pack, heap));
    }
}

class VarProcessor {

    @Inject
    private VarMarker varMarker;

    public void markCreation(final Heap heap) {
        heap.getPacks().forEach(pack -> varMarker.markCreation(pack));
    }
}

class WhenProcessor {

    @Inject
    private WhenCreator whenCreator;
    @Inject
    private Packs packs;

    public void createWhens(final Heap heap) {
        List<Invoke> invokes = packs.filterInvokes(heap.getPacks());
        invokes.forEach(invoke -> whenCreator.createWhen(invoke, heap));
    }
}

class VerifyProcessor {

    @Inject
    private VerifyCreator verifyCreator;
    @Inject
    private Packs packs;

    public void createVerifys(final Heap heap) {
        List<Invoke> invokes = packs.filterInvokes(heap.getPacks());
        invokes.forEach(invoke -> verifyCreator.createVerify(invoke, heap));
    }
}
