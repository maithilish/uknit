package org.codetab.uknit.core.zap.make.method.visit;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.node.Expressions;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Resolver;
import org.codetab.uknit.core.zap.make.method.stage.VarExpStager;
import org.codetab.uknit.core.zap.make.method.stage.VarStager;
import org.codetab.uknit.core.zap.make.method.stage.VerifyStager;
import org.codetab.uknit.core.zap.make.method.stage.WhenStager;
import org.codetab.uknit.core.zap.make.model.ExpReturnType;
import org.codetab.uknit.core.zap.make.model.ExpVar;
import org.codetab.uknit.core.zap.make.model.Heap;
import org.codetab.uknit.core.zap.make.model.IVar;
import org.codetab.uknit.core.zap.make.model.Invoke;
import org.codetab.uknit.core.zap.make.model.ModelFactory;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclaration;

public class InvokeProcessor {

    @Inject
    private Nodes nodes;
    @Inject
    private Methods methods;
    @Inject
    private Resolver resolver;
    @Inject
    private VarStager varStager;
    @Inject
    private WhenStager whenStager;
    @Inject
    private VerifyStager verifyStager;
    @Inject
    private ModelFactory modelFactory;
    @Inject
    private Patcher patcher;
    @Inject
    private Expressions expressions;
    @Inject
    private VarExpStager varExpStager;
    @Inject
    private InternalCallProcessor internalCallProcessor;

    public Invoke process(final MethodInvocation mi, final Heap heap) {
        MethodInvocation patchedMi = patcher.copyAndPatch(mi, heap);
        Expression patchedExp = patchedMi.getExpression();
        // TODO make var in Invoke optional
        IVar var = null;
        if (nonNull(patchedExp)) {
            try {
                var = heap.findVar(expressions.getName(patchedExp));
            } catch (IllegalStateException e) {
            }
        }
        Optional<ExpReturnType> expReturnType = resolver.getExpReturnType(mi);
        Invoke invoke = modelFactory.createInvoke(var, expReturnType, mi);

        IMethodBinding methodBinding = resolver.resolveMethodBinding(mi);

        if (methodBinding.getExceptionTypes().length > 0) {
            heap.setTestThrowsException(true);
        }

        /*
         * The MI foo.bar(zoo) is exp.name(exp). If leading exp is null then MI
         * may be internal or static call.
         */
        if (isNull(patchedExp) && !methods.isStaticCall(mi)) {
            List<Expression> arguments = methods.getArguments(mi);
            Optional<IVar> retVar = internalCallProcessor.process(methodBinding,
                    arguments, heap);
            invoke.setReturnVar(retVar);
        }
        return invoke;
    }

    /**
     * Process super method invocation.
     * @param smi
     * @param heap
     * @return IVar returnVar
     */
    public Invoke process(final SuperMethodInvocation smi, final Heap heap) {
        Optional<ExpReturnType> expReturnType = resolver.getExpReturnType(smi);
        IVar var = null; // no call var
        Invoke invoke = modelFactory.createInvoke(var, expReturnType, smi);

        IMethodBinding methodBinding = resolver.resolveMethodBinding(smi);
        List<Expression> arguments = methods.getArguments(smi);
        Optional<IVar> retVar =
                internalCallProcessor.process(methodBinding, arguments, heap);
        invoke.setReturnVar(retVar);

        return invoke;
    }

    public Optional<IVar> stageInferVar(final Invoke invoke, final Heap heap) {
        Optional<IVar> inferVar = varStager.stageInferVar(invoke, heap);
        invoke.setReturnVar(inferVar);

        if (inferVar.isPresent()) {
            ExpVar varExp = varExpStager.stage(null, invoke.getExp(), heap);
            varExp.setLeftVar(inferVar.get());
        }

        return inferVar;
    }

    public void stageInferVarWhen(final Invoke invoke, final Heap heap) {
        Optional<IVar> inferVar = invoke.getReturnVar();
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
                Optional<Invoke> invoke = heap.findInvoke(mi);
                if (invoke.isPresent() && invoke.get().isWhen()) {
                    whenStager.stageWhen(invoke.get(), localVar, heap);
                }
            }
        }
    }

    public void stageVerify(final Expression exp, final Heap heap) {
        if (nodes.is(exp, MethodInvocation.class)) {
            MethodInvocation mi = nodes.as(exp, MethodInvocation.class);
            heap.findInvoke(mi).ifPresent(invoke -> {
                IVar callVar = invoke.getCallVar();
                // method invoked on mock - exclude disabled fields
                if (nonNull(callVar) && callVar.isMock()
                        && callVar.isEnable()) {
                    verifyStager.stageVerify(mi, heap);
                }
            });
        }
    }

    /**
     * Invokes such as internal call arg in internal call escapes both the
     * stageInfer() and stageWhen(). For such invokes, stage expVar.
     * <p>
     * Example: itest/internal/CallInternalWithInternalArg. The call to
     * internalB(date), creates infer var apple and maps it for its statement
     * date.toInstant(). Additionally, map time.instant returned by
     * internalB(date) to apple so that the instant1 in down the line
     * instant1.getNano() is resolved to apple.
     * @param invoke
     * @param heap
     */
    public void stageExpVar(final Invoke invoke, final Heap heap) {
        Expression exp = invoke.getExp();
        if (heap.findByRightExp(exp).isEmpty()) {
            ExpVar varExp = varExpStager.stage(null, exp, heap);
            invoke.getReturnVar().ifPresent(v -> varExp.setLeftVar(v));
        }
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
        heap.findInvoke(mi).ifPresent(invoke -> {
            Type type = ioe.getRightOperand();
            invoke.getReturnVar().ifPresent(i -> i.setType(type));
        });
    }

    /**
     * either of: final/private/equals()/hashCode() methods or methods cannot be
     * stubbed/verified or methods declared on non-public parent classes is not
     * supported.
     * <p>
     * yet to fully implement.
     * @return
     */
    public boolean nonStubable(final Invoke invoke) {
        int modifier =
                resolver.resolveMethodBinding(invoke.getExp()).getModifiers();
        return resolver.hasModifier(modifier, Modifier.FINAL);
    }
}