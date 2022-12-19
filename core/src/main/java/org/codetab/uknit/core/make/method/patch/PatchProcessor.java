package org.codetab.uknit.core.make.method.patch;

import static java.util.Objects.nonNull;

import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;

public class PatchProcessor {

    @Inject
    private Packs packs;

    /**
     * If var is renamed on conflict, then create patch (Kind.VAR) to patch
     * occurrences of var name in exp and its expressions. Create patch only for
     * packs that come after the renamedPack in heap pack list.
     *
     * @param heap
     */
    public void createVarPatches(final Heap heap) {

        Patcher patcher = heap.getPatcher();

        List<Pack> renamedPacks = packs.filterIsVarRenamed(heap.getPacks());
        /*
         * for each renamedPack create patches for packs (if the renamed var is
         * used in packs exp) that come after it.
         */
        for (Pack renamedPack : renamedPacks) {
            // process packs that comes after renamedPack
            List<Pack> tailList = packs.tailList(renamedPack, heap.getPacks());
            tailList = packs.filterPacks(tailList, p -> nonNull(p.getExp()));

            tailList.forEach(pack -> patcher.addVarRenamePatch(pack,
                    renamedPack.getVar(), heap));
        }
    }
}
