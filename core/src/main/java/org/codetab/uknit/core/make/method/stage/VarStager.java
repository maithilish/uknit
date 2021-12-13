package org.codetab.uknit.core.make.method.stage;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.codetab.uknit.core.util.StringUtils.spaceit;

import java.util.Optional;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.exception.CodeException;
import org.codetab.uknit.core.exception.TypeException;
import org.codetab.uknit.core.make.Variables;
import org.codetab.uknit.core.make.model.ExpReturnType;
import org.codetab.uknit.core.make.model.ExpVar;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.InferVar;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Parameter;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.Mocks;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.TypeResolver;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclaration;

public class VarStager {

    private static final Logger LOG = LogManager.getLogger();

    @Inject
    private Mocks mocks;
    @Inject
    private Nodes nodes;
    @Inject
    private Methods methods;
    @Inject
    private Variables variables;
    @Inject
    private ModelFactory modelFactory;
    @Inject
    private TypeResolver typeResolver;

    public IVar stageParameter(final VariableDeclaration vd, final Type type,
            final boolean mock, final Heap heap) {
        String name = nodes.getVariableName(vd);
        Parameter parameter = modelFactory.createParameter(name, type, mock);
        heap.getVars().add(parameter);
        LOG.debug("stage parameter {}", parameter);
        return parameter;
    }

    public IVar stageLocalVar(final VariableDeclaration vd, final Type type,
            final boolean mock, final Heap heap) {

        LOG.debug("vdf node: {}", vd);

        /*
         * if fragment's initializer is new instance then it is real, other
         * fragments may still be mock
         */
        boolean fragmentIsMock = mock;
        boolean hidden = false;
        Type fragmentType = type;
        String name = nodes.getVariableName(vd);

        Expression initializer = vd.getInitializer();
        if (nonNull(initializer)) {
            if (nodes.isClassInstanceCreation(initializer)) {
                fragmentIsMock = false;
            }
            if (nodes.isAnonOrLambda(initializer)) {
                fragmentIsMock = false;
                hidden = true;
            }
            if (nodes.is(initializer, MethodInvocation.class)) {
                if (methods.isStaticCall(
                        nodes.as(initializer, MethodInvocation.class))) {
                    fragmentIsMock = false;
                }
            }
        }

        IVar localVar = null;
        if (nonNull(initializer)) {
            Optional<ExpVar> evo = heap.findByRightExp(initializer);
            if (evo.isPresent()) {
                Optional<IVar> lvo = evo.get().getLeftVar();
                if (lvo.isPresent()) {
                    IVar inferVar = lvo.get();
                    inferVar.setName(name);
                    inferVar.setType(fragmentType);
                    inferVar.setMock(fragmentIsMock);
                    localVar = inferVar;
                }
            }
        }

        if (isNull(localVar)) {
            localVar = modelFactory.createLocalVar(name, fragmentType,
                    fragmentIsMock);
            heap.getVars().add(localVar);
        }
        localVar.setHidden(hidden);

        LOG.debug("stage var {}", localVar);
        return localVar;
    }

    public Optional<IVar> stageInferVar(final Invoke invoke, final Heap heap) {
        LOG.debug("mi node: {}", invoke.getMi());
        Optional<ExpReturnType> expReturnType = invoke.getExpReturnType();
        InferVar inferVar = null;
        if (expReturnType.isPresent()) {
            Type type = expReturnType.get().getType();

            boolean isReturnMock = expReturnType.get().isMock();
            boolean isVarMock = false;
            if (nonNull(invoke.getCallVar())) {
                isVarMock = invoke.getCallVar().isMock();
            }

            boolean stageable = false;
            if (mocks.isInferVarStageable(isVarMock, isReturnMock)) {
                stageable = true;
            } else if (methods.isStaticCall(invoke.getMi())) {
                /*
                 * Byte.valueOf("100")
                 */
                stageable = true;
            }

            if (stageable) {
                String name = variables.getInferVarName();
                inferVar =
                        modelFactory.createInferVar(name, type, isReturnMock);
                heap.getVars().add(inferVar);
                LOG.debug("stage var {}", inferVar);
            } else {
                LOG.debug("inferVar is not stagable {}", invoke.getMi());
            }

            return Optional.ofNullable(inferVar);
        } else {
            throw new CodeException(
                    spaceit("unable to create inferred var for mi",
                            invoke.getMi().toString()));
        }
    }

    public InferVar stageInferVar(final Expression exp, final Heap heap) {
        Optional<Type> o = typeResolver.getVarClass(exp);
        if (o.isPresent()) {
            Type type = o.get();
            boolean mock = mocks.isMockable(type);
            String name = variables.getInferVarName();
            InferVar inferVar = modelFactory.createInferVar(name, type, mock);
            heap.getVars().add(inferVar);
            LOG.debug("stage var {}", inferVar);
            return inferVar;
        } else {
            String message = spaceit("unable to resolve type:", exp.toString());
            throw new TypeException(message);
        }
    }

    public InferVar stageInferVar(final Type type, final boolean mock,
            final Heap heap) {
        String name = variables.getInferVarName();
        InferVar inferVar = modelFactory.createInferVar(name, type, mock);
        heap.getVars().add(inferVar);
        LOG.debug("stage var {}", inferVar);
        return inferVar;
    }

}
