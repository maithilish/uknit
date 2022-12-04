package org.codetab.uknit.core.make.method.patch;

import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;

public class PatchProcessor {
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
