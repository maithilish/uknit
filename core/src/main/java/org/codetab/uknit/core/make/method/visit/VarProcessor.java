package org.codetab.uknit.core.make.method.visit;

import static java.util.Objects.nonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.stage.VarExpStager;
import org.codetab.uknit.core.make.method.stage.VarStager;
import org.codetab.uknit.core.make.model.Call;
import org.codetab.uknit.core.make.model.ExpReturnType;
import org.codetab.uknit.core.make.model.ExpVar;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.InferVar;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.node.Mocks;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class VarProcessor {

    @Inject
    private Mocks mocks;
    @Inject
    private Nodes nodes;
    @Inject
    private VarStager varStager;
    @Inject
    private VarExpStager varExpStager;

    public Map<IVar, VariableDeclaration> stageParameters(final Type type,
            final List<VariableDeclaration> vdList, final Heap heap) {
        Map<IVar, VariableDeclaration> varMap = new HashMap<>();
        boolean mock = mocks.isMockable(type);

        for (VariableDeclaration vd : vdList) {
            IVar parameter = varStager.stageParameter(vd, type, mock, heap);
            varMap.put(parameter, vd);
        }
        return varMap;
    }

    public Map<IVar, VariableDeclaration> stageLocalVars(final Type type,
            final List<VariableDeclaration> vdList,
            final boolean internalMethod, final Heap heap) {
        Map<IVar, VariableDeclaration> varMap = new HashMap<>();
        boolean mock = mocks.isMockable(type);

        for (VariableDeclaration vd : vdList) {
            /*
             * IMC parameters are local vars but not staged as explained in
             * InternalCallProcessor.process().
             */
            String name = nodes.getVariableName(vd);
            if (heap.useArgVar(name)) {
                continue;
            }

            IVar localVar = varStager.stageLocalVar(vd, type, mock,
                    internalMethod, heap);
            varMap.put(localVar, vd);

            Expression initializerExp = vd.getInitializer();
            if (nonNull(initializerExp)) {
                // TODO - remove this
                boolean stageVarExp = true;
                Optional<Invoke> o = heap.findInvoke(initializerExp);
                // TODO - improve the below
                if (o.isPresent()) {
                    Invoke invoke = o.get();
                    invoke.setReturnVar(Optional.of(localVar));
                    if (nonNull(invoke.getCallVar())
                            && invoke.getCallVar().isMock()) {
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
                    if (!expVar.isPresent()) {
                        expVar = Optional.of(varExpStager.stage(vd.getName(),
                                initializerExp, heap));
                    }
                    expVar.get().setLeftVar(localVar);
                }
            }
        }
        return varMap;
    }

    public void stageInferVar(final Expression node, final Heap heap) {
        InferVar inferVar = varStager.stageInferVar(node, heap);
        Optional<ExpVar> expVar = heap.findByRightExp(node);
        if (!expVar.isPresent()) {
            expVar = Optional.of(varExpStager.stage(null, node, heap));
        }
        expVar.get().setLeftVar(inferVar);
    }

    /**
     * Stage infer var for literals. Normally, parent is not considered for
     * staging infer, however this is special case where parent is checked.
     * @param node
     * @param heap
     */
    public void stageInferVarForLiteralReturn(final Expression node,
            final Heap heap) {
        if (nodes.is(node.getParent(), ReturnStatement.class)) {
            Call call = heap.getCall();
            Type type = call.getReturnType();
            boolean mock = mocks.isMockable(type);
            InferVar inferVar = varStager.stageInferVar(type, mock, heap);
            Optional<ExpVar> expVar = heap.findByRightExp(node);
            if (!expVar.isPresent()) {
                expVar = Optional.of(varExpStager.stage(null, node, heap));
            }
            expVar.get().setLeftVar(inferVar);
        }
    }

    public void cast(final CastExpression node, final Heap heap) {
        Expression exp = node.getExpression();
        Type type = node.getType();
        boolean mock = mocks.isMockable(type);

        if (nodes.is(exp, SimpleName.class)) {
            String name = nodes.getName(exp);
            IVar var = heap.findVar(name);
            var.setType(type);
            var.setMock(mock);
        }

        Optional<ExpVar> expVar = heap.findByRightExp(exp);
        expVar.ifPresent(ve -> {
            ve.getLeftVar().ifPresent(lv -> {
                lv.setType(type);
                lv.setMock(mock);
                ve.setRightExp(node);
            });
        });
    }

    public List<VariableDeclaration> getVariableDeclarations(
            final VariableDeclarationStatement vds) {
        @SuppressWarnings("unchecked")
        List<VariableDeclaration> list = vds.fragments();
        return list;
    }

    public List<VariableDeclaration> getVariableDeclarations(
            final VariableDeclarationExpression vde) {
        @SuppressWarnings("unchecked")
        List<VariableDeclaration> list = vde.fragments();
        return list;
    }
}
