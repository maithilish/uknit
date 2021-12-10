package org.codetab.uknit.core.make.method.visit;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.stage.VarExpStager;
import org.codetab.uknit.core.make.method.stage.VarStager;
import org.codetab.uknit.core.make.method.stage.VerifyStager;
import org.codetab.uknit.core.make.method.stage.WhenStager;
import org.codetab.uknit.core.make.model.ExpReturnType;
import org.codetab.uknit.core.make.model.ExpVar;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.InferVar;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.TypeResolver;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclaration;

public class InvokeProcessor {

    @Inject
    private Nodes nodes;
    @Inject
    private Methods methods;
    @Inject
    private TypeResolver resolver;
    @Inject
    private VarStager varStager;
    @Inject
    private WhenStager whenStager;
    @Inject
    private VerifyStager verifyStager;
    @Inject
    private ModelFactory modelFactory;
    @Inject
    private ExpReplacer expReplacer;
    @Inject
    private VarExpStager varExpStager;

    public Invoke process(final MethodInvocation mi, final Heap heap) {
        Expression exp = mi.getExpression();
        // TODO make var in Invoke optional
        IVar var = null;
        if (nonNull(exp) && nodes.is(exp, SimpleName.class)) {
            try {
                var = heap.findVar(nodes.getName(exp));
            } catch (IllegalStateException e) {
            }
        }
        Optional<ExpReturnType> expReturnType = resolver.getExpReturnType(mi);
        return modelFactory.createInvoke(var, expReturnType, mi);
    }

    public Optional<InferVar> stageInferVar(final Invoke invoke,
            final Heap heap) {
        Optional<InferVar> inferVar = varStager.stageInferVar(invoke, heap);
        invoke.setInferVar(inferVar);

        if (inferVar.isPresent()) {
            ExpVar varExp = varExpStager.stage(null, invoke.getMi(), heap);
            varExp.setLeftVar(inferVar.get());
        }

        return inferVar;
    }

    public void stageInferVarWhen(final Invoke invoke, final Heap heap) {
        Optional<InferVar> inferVar = invoke.getInferVar();
        if (inferVar.isPresent()) {
            whenStager.stageWhen(invoke, inferVar.get(), heap);
        }
    }

    public void stageLocalVarWhen(
            final Map<IVar, VariableDeclaration> fragments, final Heap heap) {
        for (IVar localVar : fragments.keySet()) {
            VariableDeclaration vdf = fragments.get(localVar);
            Expression exp = vdf.getInitializer();
            if (nonNull(exp) && nodes.is(exp, MethodInvocation.class)) {
                MethodInvocation mi = nodes.as(exp, MethodInvocation.class);
                Optional<Invoke> invoke = heap.getInvoke(mi);
                if (invoke.isPresent() && invoke.get().isWhen()) {
                    whenStager.stageWhen(invoke.get(), localVar, heap);
                }
            }
        }
    }

    public void stageVerify(final Expression exp, final Heap heap) {
        if (nodes.is(exp, MethodInvocation.class)) {
            MethodInvocation mi = nodes.as(exp, MethodInvocation.class);
            Optional<Invoke> o = heap.getInvoke(mi);
            if (o.isPresent()) {
                Invoke invoke = o.get();
                // method invoked on mock
                if (nonNull(invoke.getVar()) && invoke.getVar().isMock()) {
                    MethodInvocation resolvableMi = mi;
                    verifyStager.stageVerify(mi, resolvableMi, heap);
                }
            }
        }
    }

    public void replaceExpressions(final MethodInvocation mi, final Heap heap) {
        List<Expression> exps = new ArrayList<>();
        exps.add(mi.getExpression());
        exps.addAll(methods.getArguments(mi));
        expReplacer.replaceExpWithInfer(mi, exps, heap);
    }

    /**
     * Change infer var type to instance type, the expReturnType still points to
     * the actual return type.
     * @param ioe
     * @param mi
     * @param heap
     */
    public void changeToInstanceofType(final InstanceofExpression ioe,
            final MethodInvocation mi, final Heap heap) {
        heap.getInvoke(mi).ifPresent(invoke -> {
            Type type = ioe.getRightOperand();
            invoke.getInferVar().ifPresent(i -> i.setType(type));
        });
    }
}
