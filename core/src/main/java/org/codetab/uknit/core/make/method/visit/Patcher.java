package org.codetab.uknit.core.make.method.visit;

import static java.util.Objects.nonNull;

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
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;

/**
 * Direct modification to src node affects the subsequent internal calls
 * (private calls). Instead, keep the source node unmodified and create patch,
 * in visit, for later replacement and collect them in heap. Defer actual
 * patching to statements generate phase.
 * <p>
 * To generate statements - when, verify and initializer - the copyAndPatch()
 * method creates copy of the source node and patch its infer expressions and
 * copy is used.
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
    @Inject
    private Nodes nodes;

    /**
     * When inner expressions such as MethodInvocation returns InferVar then in
     * outer node replace it with infer var. Patches map expression to
     * corresponding var.
     *
     * <code>
     *   sb.append(file.getName().toLowerCase())
     *   emits
     *   when(file.getName()).thenReturn(apple);
     *   when(sb.append(apple.toLowerCase())).thenReturn(stringBuilder);
     * </code>
     *
     * For outer MI stage patch to replace file.getName() with apple.
     *
     * @param node
     * @param exps
     * @param heap
     */
    public void stageInferPatch(final ASTNode node, final List<Expression> exps,
            final Heap heap) {
        for (Expression exp : exps) {
            Optional<Invoke> o = heap.findInvoke(exp);
            /*
             * Ex: sb.append(file.getName().toLowerCase()), if file.getName()
             * returns inferVar apple then when should be
             * when(sb.append(apple.toLowerCase())).thenReturn(...). Create a
             * patch for exp file.getName() to apple.
             */
            if (o.isPresent() && o.get().getReturnVar().isPresent()) {
                Invoke invoke = o.get();
                boolean patchable = patchers.patchable(invoke);
                if (patchable) {
                    // if when returns inferVar, then replace
                    String name = invoke.getReturnVar().get().getName();
                    int argIndex = patchers.getArgIndex(node, exp);
                    Patch patch = modelFactory.createPatch(node,
                            invoke.getExp(), name, argIndex);
                    heap.getPatches().add(patch);
                }
            }
            /*
             * IMC - if calling arg name is different from parameter name then
             * stage patch. Ex: if calling arg is inferVar apple and parameter
             * is fruit, then fruit.pie() becomes apple.pie().
             */
            if (nonNull(exp) && nodes.is(exp, SimpleName.class)) {
                String paramName = nodes.getName(exp);
                if (heap.useArgVar(paramName)) {
                    String argName = heap.getArgName(paramName);
                    if (!argName.equals(paramName)) {
                        int argIndex = patchers.getArgIndex(node, exp);
                        Patch patch = modelFactory.createPatch(node, exp,
                                argName, argIndex);
                        heap.getPatches().add(patch);
                    }
                }
            }
        }
    }

    /**
     * Stage patch for super method invocation (smi). Patch patches the smi exp
     * in parent with return var name.
     * <p>
     * Example: return super.foo(bar); if super call returns var named orange
     * then the return statement becomes return orange;, after patch in
     * ReturnStatement visit.
     * @param node
     * @param retVar
     * @param heap
     */
    public void stageSuperPatch(final SuperMethodInvocation node,
            final IVar retVar, final Heap heap) {
        ASTNode parent = node.getParent();
        int argIndex = patchers.getArgIndex(parent, node);
        Patch patch = modelFactory.createPatch(parent, node, retVar.getName(),
                argIndex);
        heap.getPatches().add(patch);
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

                if (!expVar.getLeftVar().isPresent()) {
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

    /**
     * Direct modification messes the src node and subsequent internal calls
     * (private calls). To generate statements - when, verify and initializer -
     * create copy of the source node, patch its infer expressions and return
     * patched copy. The copy is not resolvable!
     *
     * @param <T>
     * @param node
     * @param heap
     * @return copy of node.
     */
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
