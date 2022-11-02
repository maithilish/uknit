package org.codetab.uknit.core.make.method.process;

import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.patch.Patcher;
import org.codetab.uknit.core.make.method.patch.Patchers;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Patch;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;

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
     * List of ASTNode that can contain MethodInvocation.
     * <p>
     * SuperMethodInvocation too can contain MI but it is not considered as we
     * use only its return value not the node itself.
     * <p>
     * Other Super nodes such as SuperConstructorInvocation etc., are yet to be
     * handled so not included for now.
     *
     * @return
     */
    public Class<?>[] canHaveInvokes() {
        Class<?>[] classes = {MethodInvocation.class,
                ClassInstanceCreation.class, ArrayCreation.class,
                ArrayAccess.class, ArrayInitializer.class,
                InfixExpression.class, PostfixExpression.class,
                PrefixExpression.class, ConditionalExpression.class};
        return classes;
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
