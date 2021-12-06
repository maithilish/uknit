package org.codetab.uknit.core.make.method.visit;

import static java.util.Objects.nonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.make.method.stage.VarExpStager;
import org.codetab.uknit.core.make.method.stage.VarStager;
import org.codetab.uknit.core.make.model.Call;
import org.codetab.uknit.core.make.model.ExpReturnType;
import org.codetab.uknit.core.make.model.ExpVar;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.InferVar;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.LocalVar;
import org.codetab.uknit.core.node.Mocks;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class VarProcessor {

    private static final Logger LOG = LogManager.getLogger();

    @Inject
    private Mocks mocks;
    @Inject
    private Nodes nodes;
    @Inject
    private VarStager varStager;
    @Inject
    private VarExpStager varExpStager;

    public Map<LocalVar, VariableDeclarationFragment> stageLocalVar(
            final VariableDeclarationStatement vds, final Heap heap) {
        Map<LocalVar, VariableDeclarationFragment> fragments = new HashMap<>();
        Type type = vds.getType();
        boolean mock = mocks.isMockable(type);

        LOG.debug("vds node: {}", vds);

        for (Object fragment : vds.fragments()) {
            VariableDeclarationFragment vdf =
                    nodes.as(fragment, VariableDeclarationFragment.class);
            LocalVar localVar = varStager.stageLocalVar(vdf, type, mock, heap);
            fragments.put(localVar, vdf);

            Expression initializerExp = vdf.getInitializer();
            if (nonNull(initializerExp)) {
                // TODO - remove this
                boolean stageVarExp = true;
                Optional<Invoke> o = heap.getInvoke(initializerExp);
                // TODO - improve the below
                if (o.isPresent()) {
                    Invoke invoke = o.get();
                    if (nonNull(invoke.getVar()) && invoke.getVar().isMock()) {
                        stageVarExp = false;
                    }
                    Optional<ExpReturnType> e = invoke.getExpReturnType();
                    if (e.isPresent() && !e.get().isMock()) {
                        stageVarExp = false;
                    }
                }
                stageVarExp = true;
                if (stageVarExp) {
                    Optional<ExpVar> expVar =
                            heap.findByRightExp(initializerExp);
                    if (expVar.isEmpty()) {
                        expVar = Optional.of(varExpStager.stage(vdf.getName(),
                                initializerExp, heap));
                    }
                    expVar.get().setLeftVar(localVar);
                }
            }
        }
        return fragments;
    }

    public void stageInferVar(final Expression node, final Heap heap) {
        InferVar inferVar = varStager.stageInferVar(node, heap);
        Optional<ExpVar> expVar = heap.findByRightExp(node);
        if (expVar.isEmpty()) {
            expVar = Optional.of(varExpStager.stage(null, node, heap));
        }
        expVar.get().setLeftVar(inferVar);
    }

    /**
     * Stage infer var for NullLiteral. Normally, parent is not considered for
     * staging infer, however this is special case where parent is checked.
     * @param node
     * @param heap
     */
    public void stageInferVarForReturn(final Expression node, final Heap heap) {
        if (nodes.is(node.getParent(), ReturnStatement.class)) {
            Call call = heap.getCall();
            Type type = call.getReturnType();
            boolean mock = mocks.isMockable(type);
            InferVar inferVar = varStager.stageInferVar(type, mock, heap);
            Optional<ExpVar> expVar = heap.findByRightExp(node);
            if (expVar.isEmpty()) {
                expVar = Optional.of(varExpStager.stage(null, node, heap));
            }
            expVar.get().setLeftVar(inferVar);
        }
    }
}
