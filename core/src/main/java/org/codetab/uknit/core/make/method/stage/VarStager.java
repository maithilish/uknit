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
import org.codetab.uknit.core.make.method.VarNames;
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
import org.codetab.uknit.core.node.Resolver;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
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
    private VarNames varNames;
    @Inject
    private ModelFactory modelFactory;
    @Inject
    private Resolver resolver;

    public IVar stageParameter(final VariableDeclaration vd, final Type type,
            final boolean mock, final Heap heap) {
        String name = nodes.getVariableName(vd);
        Parameter parameter = modelFactory.createParameter(name, type, mock);
        heap.getVars().add(parameter);
        LOG.debug("stage parameter {}", parameter);
        return parameter;
    }

    public IVar stageLocalVar(final VariableDeclaration vd, final Type type,
            final boolean mock, final boolean internalMethod, final Heap heap) {

        LOG.debug("vdf node: {}", vd);

        /*
         * if fragment's initializer is new instance then it is real, other
         * fragments may still be mock
         */
        boolean fragmentIsMock = mock;
        boolean hidden = false;
        boolean created = false;
        Type fragmentType = type;
        String name = nodes.getVariableName(vd);

        Expression initializer = vd.getInitializer();
        if (nonNull(initializer)) {
            if (nodes.isCreation(initializer)) {
                fragmentIsMock = false;
                created = true;
            }
            if (nodes.isAnonOrLambda(initializer)) {
                fragmentIsMock = false;
                hidden = true;
                created = true;
            }
            if (nodes.is(initializer, MethodInvocation.class)) {
                MethodInvocation mi =
                        nodes.as(initializer, MethodInvocation.class);
                if (methods.isStaticCall(mi)) {
                    fragmentIsMock = false;
                    created = true;

                    // type is mock but static call - stage var but hide it
                    if (mock) {
                        hidden = true;
                    }
                } else {
                    /*
                     * foo.stream().get(); if obj returned by get() is real then
                     * fragment is real
                     */
                    Optional<ExpReturnType> expReturnType =
                            heap.findExpReturnType(mi);
                    if (expReturnType.isPresent()
                            && !expReturnType.get().isMock()) {
                        fragmentIsMock = false;
                    }
                    /*
                     * Track track = tracks.stream().min(...).get(); tracks may
                     * parameter or local var initialized with creation. If
                     * former, then track is mock else it is real.
                     */
                    Optional<String> oTopName = methods.getVarName(mi);
                    if (oTopName.isPresent()) {
                        String topName = oTopName.get();
                        // if IMC, use arg var in place of IMC parameter
                        if (heap.useArgVar(topName)) {
                            topName = heap.getArgName(topName);
                        }
                        IVar methodVar = heap.findVar(topName);
                        if (methodVar.isCreated()) {
                            fragmentIsMock = false;
                            created = true;
                        }
                    }
                }
            }
        }

        /*
         * ex: Date date = createDate(); For mi initializer the infer var is
         * already created, find the infer var use that as local var by updating
         * name etc.
         */
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
                    // retain created and hidden value
                    localVar = inferVar;
                }
            }
        }

        if (isNull(localVar)) {
            /*
             * multiple calls to same internal method (IM) results in duplicate
             * vars, rename such vars. Parameters of IM are staged as local var
             * and when variableDecl's parent is methodDecl then it is
             * parameter. Don't rename parameters.
             */
            if (internalMethod && heap.findLocalVar(name).isPresent()) {
                if (!nodes.is(vd.getParent(), MethodDeclaration.class)) {
                    name = varNames.renameVar(name);
                }
            }

            localVar = modelFactory.createLocalVar(name, fragmentType,
                    fragmentIsMock);
            heap.getVars().add(localVar);
        }

        // if not infer var
        if (localVar.isLocalVar()) {
            localVar.setHidden(hidden);
            localVar.setCreated(created);
        }

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

            /*
             * Stage all infer vars. If not stageable then mark them as real,
             * created and hidden.
             */
            String name = varNames.getInferVarName();
            inferVar = modelFactory.createInferVar(name, type, isReturnMock);
            heap.getVars().add(inferVar);

            if (!stageable) {
                inferVar.setMock(false);
                inferVar.setCreated(true);
                inferVar.setHidden(true);
            }

            return Optional.ofNullable(inferVar);
        } else {
            throw new CodeException(
                    spaceit("unable to create inferred var for mi",
                            invoke.getMi().toString()));
        }
    }

    public InferVar stageInferVar(final Expression exp, final Heap heap) {
        Optional<Type> o = resolver.getVarClass(exp);
        if (o.isPresent()) {
            Type type = o.get();
            boolean mock = mocks.isMockable(type);
            String name = varNames.getInferVarName();
            InferVar inferVar = modelFactory.createInferVar(name, type, mock);
            if (nodes.isCreation(exp)) {
                inferVar.setCreated(true);
            }
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
        String name = varNames.getInferVarName();
        InferVar inferVar = modelFactory.createInferVar(name, type, mock);
        heap.getVars().add(inferVar);
        LOG.debug("stage var {}", inferVar);
        return inferVar;
    }

}
