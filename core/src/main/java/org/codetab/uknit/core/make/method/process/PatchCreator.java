package org.codetab.uknit.core.make.method.process;

import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.patch.Patcher;
import org.codetab.uknit.core.make.method.patch.Patchers;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Patch;
import org.eclipse.jdt.core.dom.Expression;

public class PatchCreator {

    @Inject
    private Patcher patcher;
    @Inject
    private Patchers patchers;

    /**
     * Patch to replace MIs in exp such as MI, ArrayAccess,
     * ClassInstanceCreateion, Infix etc., with var.
     *
     * @param packList
     * @param heap
     */
    public void createInvokePatch(final Pack pack, final Heap heap) {
        Expression exp = pack.getExp();
        List<Patch> patches =
                patcher.creatInvokePatches(exp, patchers.getExps(exp), heap);
        pack.getPatches().addAll(patches);
    }

    /**
     * Stage patch to replace entire super method invocation with return var.
     * <p>
     * Example: return super.foo(bar); to return orange;
     * @param node
     * @param retVar
     * @param heap
     */
    // public void stageSuperMiPatch(final SuperMethodInvocation node,
    // final Optional<IVar> retVar, final Heap heap) {
    // if (retVar.isPresent()) {
    // patcher.stageSuperPatch(node, retVar.get(), heap);
    // }
    // }

    // public void stageReturnStmtPatches(final ReturnStatement rs,
    // final Heap heap) {
    // List<Expression> exps = patchers.getExps(rs);
    // patcher.stageInferPatch(rs, exps, heap);
    // patcher.stageInternalPatch(rs, exps, heap);
    // // FIXME - analyze can we drop this
    // patcher.stageInitializerPatch(rs, exps, heap);
    //
    // }
    //

    // public void stageAssignmentPatches(final Assignment assign,
    // final Heap heap) {
    // List<Expression> exps = patchers.getExps(assign);
    // patcher.stageInferPatch(assign, exps, heap);
    // patcher.stageInternalPatch(assign, exps, heap);
    // }
}
