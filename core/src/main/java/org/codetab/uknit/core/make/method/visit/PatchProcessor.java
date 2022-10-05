package org.codetab.uknit.core.make.method.visit;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.node.Methods;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;

import com.google.common.collect.Lists;

public class PatchProcessor {

    @Inject
    private Methods methods;
    @Inject
    private Patcher patcher;

    /**
     * Stage patches for method (exp and args) and super method invocation (only
     * args).
     * @param mi
     * @param heap
     */
    public void stageMiPatches(final MethodInvocation mi, final Heap heap) {
        List<Expression> exps = new ArrayList<>();
        exps.add(mi.getExpression());
        exps.addAll(methods.getArguments(mi));
        patcher.stageInferPatch(mi, exps, heap);
        patcher.stageInternalPatch(mi, exps, heap);
    }

    /**
     * Stage patches for method (exp and args) and super method invocation (only
     * args).
     * @param exp
     * @param heap
     */
    public void stageSuperMiPatches(final SuperMethodInvocation smi,
            final Heap heap) {
        List<Expression> exps = new ArrayList<>();
        exps.addAll(methods.getArguments(smi));
        patcher.stageInferPatch(smi, exps, heap);
        patcher.stageInternalPatch(smi, exps, heap);
    }

    /**
     * Stage patch to replace entire super method invocation with return var.
     * <p>
     * Example: return super.foo(bar); to return orange;
     * @param node
     * @param retVar
     * @param heap
     */
    public void stageReplaceSuperMiPatch(final SuperMethodInvocation node,
            final Optional<IVar> retVar, final Heap heap) {
        if (retVar.isPresent()) {
            patcher.stageSuperPatch(node, retVar.get(), heap);
        }
    }

    public void stageInstanceCreationPatches(final ClassInstanceCreation cic,
            final Heap heap) {
        @SuppressWarnings("unchecked")
        List<Expression> exps = new ArrayList<>(cic.arguments());
        patcher.stageInferPatch(cic, exps, heap);
        patcher.stageInternalPatch(cic, exps, heap);
    }

    public void stageArrayCreationPatches(final ArrayCreation ac,
            final Heap heap) {
        @SuppressWarnings("unchecked")
        List<Expression> exps = new ArrayList<>(ac.dimensions());
        patcher.stageInferPatch(ac, exps, heap);
        patcher.stageInternalPatch(ac, exps, heap);
    }

    public void stageReturnStmtPatches(final ReturnStatement rs,
            final Heap heap) {
        Expression exp = rs.getExpression();
        if (nonNull(exp)) {
            List<Expression> exps = Lists.newArrayList(exp);
            patcher.stageInferPatch(rs, exps, heap);
            patcher.stageInternalPatch(rs, exps, heap);
            patcher.stageInitializerPatch(rs, exps, heap);
        }
    }

    public void stageInfixPatches(final InfixExpression infix,
            final Heap heap) {
        List<Expression> exps = new ArrayList<>();

        exps.add(infix.getLeftOperand());
        exps.add(infix.getRightOperand());

        @SuppressWarnings("unchecked")
        List<Expression> extOperands = infix.extendedOperands();
        exps.addAll(extOperands);

        patcher.stageInferPatch(infix, exps, heap);
        patcher.stageInternalPatch(infix, exps, heap);
        // TODO H - add static call, init and super patch
    }

}
