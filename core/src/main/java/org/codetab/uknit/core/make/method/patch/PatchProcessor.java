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
     * If var is renamed on conflict or reassign, then create patch (Kind.VAR)
     * to patch occurrences of var name in expression and its expressions.
     * Create patch only for packs in scope; from renamedPack up to the next
     * reassign in the pack list.
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
            /**
             * <code>
             *  P1 id = "foo2";         // id 3
             *  P2 id = foo.format(id); // id 4
             *  P3 foo.append(id);
             *  P4 id = foo.format(id); // id 5
             * </code>
             *
             * For renamed pack P2, the tailList is P2,P3 and P4. As name is
             * changed again in P4, the scope list is P2 and P3.
             */
            List<Pack> tailList = packs.filterPacks(
                    packs.tailList(renamedPack, heap.getPacks()),
                    p -> nonNull(p.getExp()));
            List<Pack> scopeList =
                    packs.filterScopePacks(renamedPack, tailList);

            /**
             * <code>
             *  P1 id = "foo2";         // id 3
             *  P2 id = foo.format(id); // id 4
             *  P3 foo.append(id);
             *  P4 id = foo.format(id); // id 5
             * </code>
             *
             * The foo.format(id) should be patched to id3 to output
             * when(foo.format(id3)).thenReturn(id4). In foo.append(id) patched
             * to id4 to output verify(foo).append(id4);
             *
             * For the renamed pack P2, the scope list contains P2 and P3 as
             * name is again changed in P4. Now, the var name is changed in LHS
             * of P2 so for RHS use previous packs var name (id3) as patch. For
             * all other items in the scope list (in this case only P3), use LHS
             * of P2 var name (id4) as patch.
             */
            if (scopeList.size() > 0) {
                List<Pack> reassignedPacks =
                        packs.filterByDefinedName(scopeList.get(0), heap);
                int index = reassignedPacks.indexOf(scopeList.get(0));

                if (index > 0) {
                    /*
                     * The previous pack exists, hence use its var as patch for
                     * the first pack in scope list. As patch is created here
                     * for the first pack, remove it.
                     */
                    Pack firstPack = scopeList.remove(0);
                    Pack previousPack = reassignedPacks.get(index - 1);
                    patcher.addVarRenamePatch(renamedPack, firstPack,
                            previousPack.getVar(), heap);
                }
                /*
                 * For other packs in scope list use renamed var as patch. In
                 * case patch is not created for the first pack above then use
                 * renamed var for it also.
                 */
                for (Pack pack : scopeList) {
                    patcher.addVarRenamePatch(renamedPack, pack,
                            renamedPack.getVar(), heap);
                }
            }
        }
    }

}
