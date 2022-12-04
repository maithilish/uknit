package org.codetab.uknit.core.make.method.var;

import static java.util.Objects.nonNull;

import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Vars;
import org.codetab.uknit.core.make.method.var.linked.LinkedVarProcessor;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;

public class VarProcessor {

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
