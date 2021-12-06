package org.codetab.uknit.core.make.method.stage;

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
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.InferVar;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.LocalVar;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.Mocks;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.TypeResolver;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

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

    public LocalVar stageLocalVar(final VariableDeclarationFragment vdf,
            final Type type, final boolean mock, final Heap heap) {

        LOG.debug("vdf node: {}", vdf);

        /*
         * if fragment's initializer is new instance then it is real, other
         * fragments may still be mock
         */
        boolean fragmentIsMock = mock;
        boolean exposed = true;
        Type fragmentType = type;
        String name = nodes.getVariableName(vdf);

        Expression initializer = vdf.getInitializer();
        if (nonNull(initializer)) {
            if (nodes.isClassInstanceCreation(initializer)) {
                fragmentIsMock = false;
            }
            if (nodes.isAnonOrLambda(initializer)) {
                fragmentIsMock = false;
                exposed = false;
            }
            if (nodes.is(initializer, MethodInvocation.class)) {
                if (methods.isStaticCall(
                        nodes.as(initializer, MethodInvocation.class))) {
                    fragmentIsMock = false;
                }
            }
        }

        LocalVar localVar =
                modelFactory.createLocalVar(name, fragmentType, fragmentIsMock);
        localVar.setExposed(exposed);
        heap.getVars().add(localVar);

        if (nonNull(initializer)) {
            heap.findByRightExp(initializer)
                    .ifPresent(i -> i.setLeftVar(localVar));
        }

        LOG.debug("stage var {}", localVar);
        return localVar;
    }

    public Optional<InferVar> stageInferVar(final Invoke invoke,
            final Heap heap) {
        LOG.debug("mi node: {}", invoke.getMi());
        Optional<ExpReturnType> expReturnType = invoke.getExpReturnType();
        InferVar inferVar = null;
        if (expReturnType.isPresent()) {
            Type type = expReturnType.get().getType();
            boolean isReturnMock = mocks.isMockable(type);
            boolean isVarMock = false;
            IVar var = invoke.getVar();
            if (nonNull(var)) {
                isVarMock = invoke.getVar().isMock();
            }
            if (mocks.isInferVarStageable(isVarMock, isReturnMock)) {
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
