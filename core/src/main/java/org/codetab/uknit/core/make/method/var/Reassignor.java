package org.codetab.uknit.core.make.method.var;

import static java.util.Objects.nonNull;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;

/**
 * Manages var reassigns.
 *
 * @author Maithilish
 *
 */
public class Reassignor {

    @Inject
    private Reassignors reassignors;

    /**
     * Rename reassigned vars and update old names.
     *
     * @param heap
     * @return
     */
    public List<IVar> processReassign(final Heap heap) {
        List<IVar> reassignedVars = heap.getPacks().stream()
                .map(p -> p.getVar())
                .filter(v -> nonNull(v) && v.getName().endsWith("-reassigned"))
                .collect(Collectors.toList());
        for (IVar v : reassignedVars) {
            reassignors.renameAssigns(v, heap);
        }

        reassignors.updateOldNames(reassignedVars, heap);

        return reassignedVars;
    }

    /**
     * Update any references of reassigned vars in RHS name exps.
     *
     * @param heap
     */
    public void updateReassign(final List<IVar> reassignedVars,
            final Heap heap) {
        for (IVar var : reassignedVars) {
            reassignors.updateReferredRHSExps(var, heap);
        }
    }

}
