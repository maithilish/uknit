package org.codetab.uknit.core.make.method.patch.old;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Patch;

public class PatchProcessor {
    @Inject
    private PatchCreator patchCreator;
    @Inject
    private PatcherOld patcherOld;
    @Inject
    private Packs packs;

    /**
     * Create patch of Kind.INVOKE to patch invoke exp with infer var.
     *
     * @param heap
     */
    public void createInvokePatches(final Heap heap) {
        List<Pack> packList = patcherOld.getInvokePatchables(heap);
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
            String oldName = renamedPack.getVar().getOldName();
            String newName = renamedPack.getVar().getName();
            List<Pack> tailList = packs.tailList(renamedPack, heap.getPacks());
            tailList.forEach(pack -> patcherOld.updatePatchName(pack, oldName,
                    newName, heap));
        }
    }

    /**
     * The return var of IM is assigned as exp to the pack in calling heap.
     * Assign the patches of return var to the calling pack. Ex: <code>
     *
     * return getInternalDate(factory.getDate());
     *
     * private Date getInternalDate(final Date d1) {
     *   return d1;
     * } </code>
     *
     * The IM Pack [var:date, exp: factory.getDate()] holds Patch [exp: d1,
     * name: date]. On IM merge the Pack [name: date2, exp: getInternalDate()]
     * of calling heap becomes Pack [name: date2, exp: d1]. Assign the patch
     * from IM pack to calling pack so that the calling pack becomes Pack [name:
     * date2, exp: date]. Ref itest: superclass.BlendVar.invokeInternalMock().
     *
     * @param heap
     * @param internalHeap
     */
    public void assignInternalReturnPatches(final Heap heap,
            final Heap internalHeap) {
        Optional<Pack> returnPackO =
                packs.getReturnPack(internalHeap.getPacks());
        if (returnPackO.isPresent()) {
            List<Patch> patches = returnPackO.get().getPatches();
            for (Patch patch : patches) {
                Optional<Pack> packO =
                        packs.findByExp(patch.getExp(), heap.getPacks());
                if (packO.isPresent()) {
                    packO.get().getPatches().add(patch);
                }
            }
        }
    }

}
