package org.codetab.uknit.core.make.method.visit;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.stage.VarStager;
import org.codetab.uknit.core.make.model.ExpVar;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.InferVar;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Patch;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;

/**
 * Patch expression with corresponding infer var.
 * @author Maithilish
 *
 */
public class Patcher {

    @Inject
    private VarStager varStager;
    @Inject
    private ModelFactory modelFactory;
    @Inject
    private Patchers patchers;

    public void stageInferPatch(final ASTNode node, final List<Expression> exps,
            final Heap heap) {
        for (Expression exp : exps) {
            Optional<Invoke> o = heap.findInvoke(exp);
            if (o.isPresent() && o.get().getReturnVar().isPresent()) {
                Invoke invoke = o.get();
                MethodInvocation mi = invoke.getMi();

                boolean patchable = patchers.patchable(mi, invoke);
                if (patchable) {
                    // if when returns inferVar, then replace
                    String name = invoke.getReturnVar().get().getName();
                    int argIndex = patchers.getArgIndex(node, exp);
                    Patch patch = modelFactory.createPatch(node, invoke.getMi(),
                            name, argIndex);
                    heap.getPatches().add(patch);
                }
            }
        }
    }

    /**
     * Stage replacer for initializers. Ex: return new Date();
     * @param node
     * @param exps
     * @param heap
     */
    public void stageInitializerPatch(final ASTNode node,
            final List<Expression> exps, final Heap heap) {
        for (Expression exp : exps) {
            Optional<ExpVar> o = heap.findByRightExp(exp);
            if (o.isPresent()) {
                ExpVar expVar = o.get();
                /*
                 * ex: return new String(); the var is null so stage a new one
                 */

                if (expVar.getLeftVar().isEmpty()) {
                    InferVar inferVar =
                            varStager.stageInferVar(expVar.getRightExp(), heap);
                    expVar.setLeftVar(inferVar);
                }
                Optional<IVar> leftVar = expVar.getLeftVar();
                if (leftVar.isPresent()) {
                    int argIndex = -1;
                    Patch patch =
                            modelFactory.createPatch(node, expVar.getRightExp(),
                                    leftVar.get().getName(), argIndex);
                    heap.getPatches().add(patch);
                }
            }
        }
    }

    public <T extends ASTNode> T copyAndPatch(final T node, final Heap heap) {
        @SuppressWarnings("unchecked")
        T nodeCopy = (T) ASTNode.copySubtree(node.getAST(), node);
        List<Expression> exps = patchers.getExps(node);
        List<Expression> expCopies = patchers.getExps(nodeCopy);
        for (int i = 0; i < exps.size(); i++) {
            /*
             * for groups.size() in new String[groups.size()][groups.size()]
             */
            Expression exp = exps.get(i);
            Optional<Patch> patch = heap.findPatch(node, exp);
            if (patch.isPresent()) {
                patchers.patchExpWithVar(nodeCopy, patch.get());
            }

            /*
             * for file.getName().toLowerCase() in
             * s2.append(file.getName().toLowerCase());
             */
            Expression expCopy = expCopies.get(i);
            List<Patch> replacerList = heap.findPatches(exp);
            replacerList.forEach(r -> patchers.patchExpWithVar(expCopy, r));
        }
        return nodeCopy;
    }
}
