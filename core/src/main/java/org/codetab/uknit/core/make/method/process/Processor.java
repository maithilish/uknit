package org.codetab.uknit.core.make.method.process;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.imc.InternalCallProcessor;
import org.codetab.uknit.core.make.method.imc.InternalCalls;
import org.codetab.uknit.core.make.method.patch.Patcher;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Methods;
import org.eclipse.jdt.core.dom.MethodInvocation;

/**
 * Routines to post process packs collected during method visit.
 *
 * @author Maithilish
 *
 */
public class Processor {

    @Inject
    private IMCProcessor imcProcessor;
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
    @Inject
    private VarStateProcessor varStateProcessor;
    @Inject
    private InvokeProcessor invokeProcessor;

    /**
     * Call after MUT and each IM visit. The ImcProcessor.process() call
     * suspends the processing to visit and process IMC recursively. On return
     * processing resumes.
     * <p>
     * Infer, Infer for Return and Patches are processed before processing IMC
     * so that infer names are alloted in a specific order.
     *
     * @param heap
     */
    public void process(final Heap heap) {

        inferProcessor.createInfers(heap);
        inferProcessor.createInferForReturn(heap);

        patchProcessor.createInvokePatches(heap);

        imcProcessor.process(heap);

        varProcessor.markCreation(heap);
        varProcessor.processCastType(heap);
    }

    public void processInvokes(final Heap heap) {
        invokeProcessor.process(heap);
    }

    /**
     * Called only after the MUT visit and not called for IM visit. This ensures
     * that when and verify are processed only once in the end.
     *
     * @param heap
     */
    public void processWhenVerify(final Heap heap) {
        whenProcessor.createWhens(heap);
        verifyProcessor.createVerifys(heap);
    }

    /**
     * Called only after the MUT visit and not called for IM visit. This ensures
     * that var state processed only once in the end.
     *
     * @param heap
     */
    public void processVarState(final Heap heap) {
        varStateProcessor.process(heap);
    }

    /**
     * On conflict if var name is changed, then create patch of Kind.VAR to
     * patch occurrences of var name in exp and its expressions.
     *
     * The invokes patches holds the original name as they are created in
     * process() before processVarNameChange(). Set the patch name to new name
     * if any patch uses that name.
     *
     * @param heap
     */
    public void processVarNameChange(final Heap internalHeap) {
        patchProcessor.createVarPatches(internalHeap);
        patchProcessor.updateNamesInPatches(internalHeap);
    }
}

class InvokeProcessor {

    @Inject
    private Packs packs;
    @Inject
    private Invokes invokes;
    @Inject
    private Methods methods;

    public void process(final Heap heap) {
        // process empty callVar
        List<Invoke> invokeList = packs.filterInvokes(heap.getPacks());
        invokeList = invokeList.stream()
                .filter(i -> i.getCallVar().isEmpty()
                        && methods.isInvokable(i.getExp()))
                .collect(Collectors.toList());
        for (Invoke invoke : invokeList) {
            invokes.setCallVar(invoke, heap);
        }
    }

}

class VarStateProcessor {

    @Inject
    private VarEnabler varEnabler;

    public void process(final Heap heap) {
        varEnabler.checkEnableState(heap);

        Set<String> usedNames = varEnabler.collectUsedVarNames(heap);
        varEnabler.updateVarEnableState(usedNames, heap);

        varEnabler.enableVarsUsedInInitializers(heap);

        Set<String> linkedNames = varEnabler.collectLinkedVarNames(heap);
        varEnabler.enableVars(linkedNames, heap);

        varEnabler.enableFromEnforce(heap);

        varEnabler.addLocalVarForDisabledField(usedNames, heap);
    }
}

class IMCProcessor {

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
    @Inject
    private Patcher patcher;

    /**
     * Create patch of Kind.INVOKE to patch invoke exp with infer var.
     *
     * @param heap
     */
    public void createInvokePatches(final Heap heap) {
        List<Pack> packList = packs.filterPacks(heap.getPacks(),
                patchCreator.canHaveInvokes());
        packList.forEach(pack -> patchCreator.createInvokePatch(pack, heap));
    }

    /**
     * On conflict if var name is changed, then create patch of Kind.VAR to
     * patch occurrences of var name in exp and its expressions.
     *
     * @param heap
     */
    public void createVarPatches(final Heap heap) {
        Map<String, String> namesMap = patchCreator.getNamesMap(heap);
        heap.getPacks().forEach(
                pack -> patchCreator.createVarPatch(pack, namesMap, heap));
    }

    /**
     * On conflict if var name is changed, then set the patch name to new name
     * if any patch uses that name. The invokes patches holds the original name
     * as they are created in process() before processVarNameChange().
     *
     * @param heap
     */
    public void updateNamesInPatches(final Heap heap) {
        Map<String, String> namesMap = patchCreator.getNamesMap(heap);
        heap.getPacks()
                .forEach(pack -> patcher.updatePatchName(pack, namesMap, heap));
    }
}

class VarProcessor {

    @Inject
    private LinkedVarProcessor linkedVarProcessor;

    /**
     * Propagate create to all linked packs.
     *
     * @param heap
     */
    public void markCreation(final Heap heap) {
        heap.getPacks().forEach(pack -> linkedVarProcessor
                .markAndPropagateCreation(pack, heap.getPacks()));
    }

    /**
     * Propagate cast type to all linked packs.
     *
     * @param heap
     */
    public void processCastType(final Heap heap) {
        heap.getPacks().forEach(pack -> linkedVarProcessor
                .propogateCastType(pack, heap.getPacks()));
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
