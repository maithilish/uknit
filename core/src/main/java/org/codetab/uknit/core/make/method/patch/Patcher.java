package org.codetab.uknit.core.make.method.patch;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.CodeException;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.Patches;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Patch;
import org.codetab.uknit.core.node.Expressions;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
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
    private Packs packs;
    @Inject
    private Nodes nodes;
    @Inject
    private Patches patches;
    @Inject
    private ModelFactory modelFactory;
    @Inject
    private Patchers patchers;
    @Inject
    private Expressions expressions;

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
     * @return
     */
    public List<Patch> creatInvokePatches(final ASTNode node,
            final List<Expression> exps, final Heap heap) {
        List<Patch> patchList = new ArrayList<>();

        final Map<Expression, Invoke> patchables = new HashMap<>();
        for (Expression exp : exps) {
            /*
             * Ex: sb.append(file.getName().toLowerCase()), if file.getName()
             * returns inferVar apple then when should be
             * when(sb.append(apple.toLowerCase())).thenReturn(...). Create a
             * patch for exp file.getName() to apple.
             *
             * When exp is CastExp strip it to find invoke. Ex: a[(int)foo.obj];
             * the exp is (int) foo.obj() and find invoke for foo.obj().
             */
            Optional<Invoke> invokeO = packs.findInvokeByExp(
                    expressions.stripWraperExpression(exp), heap.getPacks());
            if (invokeO.isPresent() && nonNull(invokeO.get().getVar())) {
                if (patchers.patchable(invokeO.get())) {
                    patchables.put(exp, invokeO.get());
                }
            }
        }

        for (Expression exp : patchables.keySet()) {
            /*
             * Ex: sb.append(file.getName().toLowerCase()), if file.getName()
             * returns inferVar apple then when should be
             * when(sb.append(apple.toLowerCase())).thenReturn(...). Create a
             * patch for exp file.getName() to apple.
             */
            Invoke invoke = patchables.get(exp);
            // if when returns inferVar, then replace
            String name = invoke.getVar().getName();
            int expIndex = patchers.getExpIndex(node, exp);
            Patch patch = modelFactory.createPatch(node, invoke.getExp(), name,
                    expIndex);
            patchList.add(patch);
        }
        return patchList;
    }

    /**
     * Copy and Patches MI or SMI and returns the calling exp.
     * <p>
     * foo.bar() - Optional.of(foo) (calling exp is foo and bar is method name)
     *
     * super.bar() - Optional.empty (IMC)
     *
     * this.bar() - Optional.empty (IMC)
     *
     * bar() - Optional.empty (IMC)
     *
     * @param invocationExp
     * @param heap
     * @return
     */
    public Optional<Expression> getPatchedCallExp(
            final Expression invocationExp, final Heap heap) {

        Optional<Expression> patchedExpO;
        if (nodes.is(invocationExp, MethodInvocation.class)) {
            // exp is null for IMC with or without super or this keyword
            patchedExpO = Optional.ofNullable(copyAndPatch(
                    nodes.as(invocationExp, MethodInvocation.class), heap)
                            .getExpression());
        } else if (nodes.is(invocationExp, SuperMethodInvocation.class)) {
            patchedExpO = Optional.empty();
        } else {
            throw new CodeException(
                    nodes.noImplmentationMessage(invocationExp));
        }
        return patchedExpO;
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
             *
             * When exp is CastExp strip it to find invoke. Ex: a[(int)foo.obj];
             * the exp is (int) foo.obj() and find invoke for foo.obj().
             */
            Expression patchExp =
                    expressions.stripWraperExpression(exps.get(i));
            Optional<Patch> patch =
                    patches.findPatch(node, patchExp, heap.getPacks());
            if (patch.isPresent()) {
                patchers.patchExpWithVar(nodeCopy, patch.get());
            }

            /*
             * for file.getName().toLowerCase() in
             * s2.append(file.getName().toLowerCase());
             */
            Expression expCopy = expCopies.get(i);
            List<Patch> replacerList =
                    patches.findPatches(patchExp, heap.getPacks());
            replacerList.forEach(r -> patchers.patchExpWithVar(expCopy, r));
        }
        return nodeCopy;
    }
}
