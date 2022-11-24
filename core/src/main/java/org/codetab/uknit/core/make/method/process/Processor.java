package org.codetab.uknit.core.make.method.process;

import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.Vars;
import org.codetab.uknit.core.make.method.imc.InternalCallProcessor;
import org.codetab.uknit.core.make.method.imc.InternalCalls;
import org.codetab.uknit.core.make.method.patch.Patcher;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
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
    public void processInfers(final Heap heap) {
        inferProcessor.createInfers(heap);
        inferProcessor.createInferForReturn(heap);
        patchProcessor.createInvokePatches(heap);
    }

    public void processIM(final Heap heap) {
        imcProcessor.process(heap);
    }

    public void postProcessIM(final Heap heap) {
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

    public void processVarReassign(final Heap heap) {
        varProcessor.processReassign(heap);
    }
}

class InvokeProcessor {

    @Inject
    private Packs packs;
    @Inject
    private Invokes invokes;
    @Inject
    private Methods methods;

    /**
     * Set call var. The call var of MI is expression and its var is known only
     * after infer var and patch is created for it in visit post process.
     *
     * @param heap
     */
    public void process(final Heap heap) {

        // process all invokes where callVar is not yet set
        List<Invoke> invokeList = packs.filterInvokes(heap.getPacks()).stream()
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

        /*
         * disable vars that are not used in when, verify and return
         */
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
    private Patcher patcher;
    @Inject
    private Packs packs;

    /**
     * Create patch of Kind.INVOKE to patch invoke exp with infer var.
     *
     * @param heap
     */
    public void createInvokePatches(final Heap heap) {
        List<Pack> packList = patcher.getInvokePatchables(heap);
        packList.forEach(pack -> patchCreator.createInvokePatch(pack, heap));
    }

    /**
     * If var is renamed on conflict, then create patch (Kind.VAR) to patch
     * occurrences of var name in exp and its expressions. Create patch only for
     * packs that come after the renamedPack in heap pack list.
     *
     * @param heap
     */
    public void createVarPatches(final Heap heap) {
        List<Pack> renamedPacks = packs.filterIsVarRenamed(heap.getPacks());
        /*
         * for each renamedPack create patches for packs (if the renamed var is
         * used in packs exp) that come after it.
         */
        for (Pack renamedPack : renamedPacks) {
            // packs that comes after renamedPack
            List<Pack> tailList = packs.tailList(renamedPack, heap.getPacks());
            tailList.forEach(
                    pack -> patchCreator.createVarPatch(pack, renamedPack));
        }
    }

    /**
     * On conflict if var name is changed, then set the patch name to new name
     * if any patch uses that name. The invokes patches holds the original name
     * as they are created in process() before processVarNameChange().
     *
     * @param heap
     */
    public void updateNamesInPatches(final Heap heap) {
        List<Pack> renamedPacks = packs.filterIsVarRenamed(heap.getPacks());
        for (Pack renamedPack : renamedPacks) {
            List<Pack> tailList = packs.tailList(renamedPack, heap.getPacks());
            tailList.forEach(
                    pack -> patcher.updatePatchName(pack, renamedPack, heap));
        }
    }
}

class VarProcessor {

    @Inject
    private LinkedVarProcessor linkedVarProcessor;
    @Inject
    private VarAssignor varAssignor;
    @Inject
    private Vars vars;

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
     * Renames reassigned vars and update any reference.
     *
     * @param heap
     */
    public void processReassign(final Heap heap) {
        List<IVar> reassignedVars = vars.filterVars(heap,
                v -> nonNull(v) && v.getName().endsWith("-reassigned"));
        reassignedVars.stream()
                .forEach(v -> varAssignor.renameAssigns(v, heap));
        reassignedVars.stream()
                .forEach(v -> varAssignor.updateAssigns(v, heap));
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
