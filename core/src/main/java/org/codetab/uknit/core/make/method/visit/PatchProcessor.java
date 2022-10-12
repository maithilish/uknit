package org.codetab.uknit.core.make.method.visit;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;

public class PatchProcessor {

    @Inject
    private Patcher patcher;
    @Inject
    private Patchers patchers;

    /**
     * Stage patches for method (exp and args) and super method invocation (only
     * args).
     * @param mi
     * @param heap
     */
    public void stageMiPatches(final MethodInvocation mi, final Heap heap) {
        List<Expression> exps = patchers.getExps(mi);
        patcher.stageInferPatch(mi, exps, heap);
        patcher.stageInternalPatch(mi, exps, heap);
    }

    /**
     * Stage patch to replace entire super method invocation with return var.
     * <p>
     * Example: return super.foo(bar); to return orange;
     * @param node
     * @param retVar
     * @param heap
     */
    public void stageSuperMiPatch(final SuperMethodInvocation node,
            final Optional<IVar> retVar, final Heap heap) {
        if (retVar.isPresent()) {
            patcher.stageSuperPatch(node, retVar.get(), heap);
        }
    }

    /**
     * Replace new instance creation exp with var.
     * @param cic
     * @param heap
     */
    public void stageInstanceCreationPatches(final ClassInstanceCreation cic,
            final Heap heap) {
        List<Expression> exps = patchers.getExps(cic);
        patcher.stageInferPatch(cic, exps, heap);
        patcher.stageInternalPatch(cic, exps, heap);
    }

    public void stageArrayCreationPatches(final ArrayCreation ac,
            final Heap heap) {
        List<Expression> exps = patchers.getExps(ac);
        patcher.stageInferPatch(ac, exps, heap);
        patcher.stageInternalPatch(ac, exps, heap);
    }

    public void stageReturnStmtPatches(final ReturnStatement rs,
            final Heap heap) {
        List<Expression> exps = patchers.getExps(rs);
        patcher.stageInferPatch(rs, exps, heap);
        patcher.stageInternalPatch(rs, exps, heap);
        // FIXME - analyze can we drop this
        patcher.stageInitializerPatch(rs, exps, heap);

    }

    public void stageInfixPatches(final InfixExpression infix,
            final Heap heap) {
        List<Expression> exps = patchers.getExps(infix);
        patcher.stageInferPatch(infix, exps, heap);
        patcher.stageInternalPatch(infix, exps, heap);
        // TODO H - add static call, init and super patch
    }

    public void stageAssignmentPatches(final Assignment assign,
            final Heap heap) {
        List<Expression> exps = patchers.getExps(assign);
        patcher.stageInferPatch(assign, exps, heap);
        patcher.stageInternalPatch(assign, exps, heap);
    }
}
