package org.codetab.uknit.core.make.method.process;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.body.initializer.InitializerProcessor;
import org.codetab.uknit.core.make.method.imc.IMCProcessor;
import org.codetab.uknit.core.make.method.invoke.InvokeProcessor;
import org.codetab.uknit.core.make.method.load.LoadProcessor;
import org.codetab.uknit.core.make.method.patch.PatchProcessor;
import org.codetab.uknit.core.make.method.var.VarProcessor;
import org.codetab.uknit.core.make.method.var.VarStateProcessor;
import org.codetab.uknit.core.make.method.var.infer.InferProcessor;
import org.codetab.uknit.core.make.method.verify.VerifyProcessor;
import org.codetab.uknit.core.make.method.when.WhenProcessor;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;

/**
 * Post visit routines to process packs collected by method visitor.
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
    @Inject
    private LoadProcessor loadProcessor;
    @Inject
    private InitializerProcessor initializerProcessor;

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
    }

    public void processIM(final Heap heap) {
        imcProcessor.process(heap);
    }

    public void processVars(final Heap heap) {
        varProcessor.markCreation(heap);
        varProcessor.propagateCreationForLinkedVars(heap);
        varProcessor.propagateRealish(heap);
        varProcessor.processCastType(heap);
    }

    public void processInvokes(final Heap heap) {
        // set call var
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
     * Called only after the MUT visit and not called for IM visit to ensure
     * that var state processed only once in the end.
     *
     * @param heap
     */
    public void processVarState(final Heap heap) {
        Set<String> usedNames = varStateProcessor.process(heap);
        varStateProcessor.processStandinVars(usedNames, heap);
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
    public void processVarPatches(final Heap heap) {
        patchProcessor.createVarPatches(heap);
    }

    /**
     * Rename reassigned vars and update old names.
     *
     * @param heap
     * @return
     */
    public List<IVar> processVarReassign(final Heap heap) {
        return varProcessor.processReassign(heap);
    }

    /**
     * Update any references of reassigned vars in RHS name exps.
     *
     * @param reassignedVars
     * @param heap
     */
    public void updateVarReassign(final List<IVar> reassignedVars,
            final Heap heap) {
        varProcessor.updateReassign(reassignedVars, heap);
    }

    public void processEnhancedFor(final Heap heap) {
        loadProcessor.processEnhancedFor(heap);
    }

    public void processLoads(final Heap heap) {
        loadProcessor.processLoadableVars(heap);
    }

    public void processInitializer(final Heap heap) {
        initializerProcessor.processExps(heap);
        initializerProcessor.processReals(heap);
        initializerProcessor.processMocks(heap);
        initializerProcessor.processSensibles(heap);
        initializerProcessor.processStepins(heap);
    }

    public void processOfflimits(final Heap heap) {
        varProcessor.processOfflimits(heap);
    }
}
