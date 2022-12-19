package org.codetab.uknit.core.make.method.var;

import static java.util.Objects.nonNull;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.var.linked.LinkedVarProcessor;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Pack;

public class VarProcessor {

    @Inject
    private LinkedVarProcessor linkedVarProcessor;
    @Inject
    private VarAssignor varAssignor;

    /**
     * Propagate create to all linked packs.
     *
     * @param heap
     */
    public void markCreation(final Heap heap) {
        /*
         * IM packs are processed before the merge, so don't process them again
         * while processing the calling heap. While processing the internal heap
         * all its packs are processed as markCreation is called before merge
         * and marking the packs as IM packs.
         *
         * If IM return var is created then internalReturns.updateVar() in
         * Merger has already set the created field of invoke var, exclude such
         * packs as calling heap linked packs doesn't have info about such
         * creation.
         *
         * Ex: internal.CallAndAssign.callAndAssignToSameNameNullInitialized()
         */
        List<Pack> packList = heap.getPacks().stream().filter(p -> !p.isIm())
                .filter(p -> nonNull(p.getVar()) && !p.getVar().isCreated())
                .collect(Collectors.toList());

        packList.forEach(pack -> linkedVarProcessor
                .markAndPropagateCreation(pack, heap));
    }

    /**
     * Renames reassigned vars and update any reference.
     *
     * @param heap
     */
    public void processReassign(final Heap heap) {
        /*
         * IM packs are processed before the merge, so don't process them again
         * while processing the calling heap. While processing the internal heap
         * all its packs are processed as processReassign is called before merge
         * and marking the packs as IM packs.
         */
        List<IVar> reassignedVars = heap.getPacks().stream()
                .filter(p -> !p.isIm()).map(p -> p.getVar())
                .filter(v -> nonNull(v) && v.getName().endsWith("-reassigned"))
                .collect(Collectors.toList());
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
