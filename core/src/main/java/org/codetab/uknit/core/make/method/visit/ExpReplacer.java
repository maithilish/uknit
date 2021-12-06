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
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;

public class ExpReplacer {

    @Inject
    private Replacers replacers;
    @Inject
    private VarStager varStager;
    @Inject
    private Methods methods;
    @Inject
    private Nodes nodes;

    public void replaceExpWithInfer(final ASTNode node,
            final List<Expression> exps, final Heap heap) {
        for (Expression exp : exps) {
            Optional<Invoke> o = heap.getInvoke(exp);
            if (o.isPresent() && o.get().getInferVar().isPresent()) {
                Invoke invoke = o.get();
                MethodInvocation mi = invoke.getMi();
                boolean replace = true;
                /*
                 * static calls: return Byte.valueOf("100"); replace
                 * date.compareTo(LocalDate.now()) == 1; don't replace
                 */
                if (methods.isStaticCall(mi)
                        && nodes.is(mi.getParent(), MethodInvocation.class)) {
                    replace = false;
                }
                if (replace) {
                    // if when returns inferVar, then replace
                    String name = invoke.getInferVar().get().getName();
                    replacers.replaceExpWithVar(node, invoke.getMi(), name);
                }
            }
        }
    }

    public void replaceExpWithInitializer(final ASTNode node,
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
                    replacers.replaceExpWithVar(node, expVar.getRightExp(),
                            leftVar.get().getName());
                }
            }
        }
    }
}
