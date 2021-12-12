package org.codetab.uknit.core.make.method.visit;

import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.stage.VarStager;
import org.codetab.uknit.core.make.model.ExpReturnType;
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
            Optional<Invoke> o = heap.findInvoke(exp);
            if (o.isPresent() && o.get().getReturnVar().isPresent()) {
                Invoke invoke = o.get();
                MethodInvocation mi = invoke.getMi();
                boolean replace = true;

                // TODO - move this to Replacers
                if (nodes.is(mi.getParent(), MethodInvocation.class)) {
                    /*
                     * static calls: return Byte.valueOf("100"); replace
                     * date.compareTo(LocalDate.now()) == 1; don't replace
                     */

                    if (methods.isStaticCall(mi)) {
                        replace = false;
                    }

                    /*
                     * return s2.append(file.getName().toLowerCase());
                     * toLowerCase on string is real returning real, don't
                     * replace it.
                     */
                    Optional<ExpReturnType> ro = invoke.getExpReturnType();
                    if (ro.isPresent()) {
                        boolean returnReal = !ro.get().isMock();
                        IVar var = invoke.getCallVar();
                        boolean varReal = !(nonNull(var) && var.isMock());
                        if (varReal && returnReal) {
                            replace = false;
                        }
                    }
                }

                if (replace) {
                    // if when returns inferVar, then replace
                    String name = invoke.getReturnVar().get().getName();
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
