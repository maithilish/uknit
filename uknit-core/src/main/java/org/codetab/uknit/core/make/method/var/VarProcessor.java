package org.codetab.uknit.core.make.method.var;

import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.var.linked.LinkedVarProcessor;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.Pack;
import org.eclipse.jdt.core.dom.MethodInvocation;

public class VarProcessor {

    @Inject
    private LinkedVarProcessor linkedVarProcessor;
    @Inject
    private Offlimits offlimits;
    @Inject
    private StandinVarCreator standinVarCreator;

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
                .filter(p -> nonNull(p.getVar()) && !p.getVar().isCreated()
                        && nonNull(p.getExp()))
                .collect(Collectors.toList());

        packList.forEach(pack -> linkedVarProcessor.markCreation(pack, heap));
    }

    public void propagateCreationForLinkedVars(final Heap heap) {
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
                .filter(p -> nonNull(p.getVar()) && !p.getVar().isCreated()
                        && nonNull(p.getExp()))
                .collect(Collectors.toList());

        packList.forEach(pack -> linkedVarProcessor
                .propagateCreationForLinkedVars(pack, heap));
    }

    /**
     * For each Invoke whose var is mock propagate Nature.REALISH.
     *
     * @param heap
     */
    public void propagateRealish(final Heap heap) {
        List<Invoke> packList = heap.getPacks().stream()
                .filter(p -> nonNull(p.getVar()) && p.getVar().isMock())
                .filter(p -> p instanceof Invoke && nonNull(p.getExp())
                        && p.getExp() instanceof MethodInvocation)
                .map(p -> (Invoke) p).collect(Collectors.toList());

        packList.forEach(
                invoke -> linkedVarProcessor.propagateRealish(invoke, heap));
    }

    /**
     * Propagate cast type to all linked packs.
     *
     * @param heap
     */
    public void processCastType(final Heap heap) {
        for (Pack pack : heap.getPacks()) {
            linkedVarProcessor.propogateCastType(pack, heap);
        }
    }

    /**
     * Add Nature.OFFLIMIT to the vars that are internal to CUT and not visible
     * to test class.
     *
     * @param heap
     */
    public void processOfflimits(final Heap heap) {
        List<Pack> packList = heap.getPacks().stream()
                .filter(p -> nonNull(p.getVar())).collect(Collectors.toList());
        for (Pack pack : packList) {
            offlimits.addNature(pack, heap);
        }
    }

    public List<Pack> processStandinVars(final Set<String> usedNames,
            final Heap heap) {
        return standinVarCreator.addStandinVarsForUsedFields(usedNames, heap);
    }
}
