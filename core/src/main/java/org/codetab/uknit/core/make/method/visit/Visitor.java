package org.codetab.uknit.core.make.method.visit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class Visitor extends ASTVisitor {

    @Inject
    private InvokeProcessor invokeProcessor;
    @Inject
    private AssignProcessor assignProcessor;
    @Inject
    private ReturnProcessor returnProcessor;
    @Inject
    private CreateProcessor createProcessor;
    @Inject
    private VarProcessor varProcessor;
    @Inject
    private LiteralProcessor literalProcessor;
    @Inject
    private Nodes nodes;

    @Inject
    private UseMarker useMarker;

    private Heap heap;

    @Override
    public void endVisit(final VariableDeclarationStatement node) {
        Type type = node.getType();
        List<VariableDeclaration> vdList =
                varProcessor.getVariableDeclarations(node);
        Map<IVar, VariableDeclaration> varMap =
                varProcessor.stageLocalVars(type, vdList, heap);
        invokeProcessor.stageLocalVarWhen(varMap, heap);
    }

    @Override
    public void endVisit(final Assignment node) {
        assignProcessor.process(node, heap);
    }

    /**
     * Collect invocation details in Invoke and if required, stage InferVar and
     * When.
     */
    @Override
    public void endVisit(final MethodInvocation node) {
        invokeProcessor.replaceExpressions(node, heap);

        Invoke invoke = invokeProcessor.process(node, heap);
        heap.getInvokes().add(invoke);

        if (invoke.isInfer()) {
            invokeProcessor.stageInferVar(invoke, heap);
            if (invoke.isWhen()) {
                invokeProcessor.stageInferVarWhen(invoke, heap);
            }
        }
    }

    @Override
    public void endVisit(final ReturnStatement node) {
        returnProcessor.replaceExpression(node, heap);
        Optional<IVar> expectedVar = returnProcessor.getExpectedVar(node, heap);
        if (expectedVar.isPresent() && !expectedVar.get().isHidden()) {
            heap.setExpectedVar(expectedVar);
        }
    }

    @Override
    public void endVisit(final InfixExpression node) {
        varProcessor.stageInferVar(node, heap);
    }

    @Override
    public void endVisit(final PrefixExpression node) {
        varProcessor.stageInferVar(node, heap);
    }

    @Override
    public void endVisit(final PostfixExpression node) {
        varProcessor.stageInferVar(node, heap);
    }

    @Override
    public void endVisit(final ClassInstanceCreation node) {
        createProcessor.replaceExpressions(node, heap);
        createProcessor.process(node, heap);
    }

    @Override
    public void endVisit(final ArrayCreation node) {
        createProcessor.replaceExpressions(node, heap);
        createProcessor.process(node, heap);
    }

    @Override
    public void endVisit(final ArrayAccess node) {
        varProcessor.stageInferVar(node, heap);
    }

    @Override
    public void endVisit(final InstanceofExpression node) {
        Expression exp = node.getLeftOperand();
        if (nodes.is(exp, MethodInvocation.class)) {
            MethodInvocation mi = nodes.as(exp, MethodInvocation.class);
            invokeProcessor.changeToInstanceofType(node, mi, heap);
        }
    }

    @Override
    public void endVisit(final ExpressionStatement node) {
        Expression exp = node.getExpression();
        invokeProcessor.stageVerify(exp, heap);
    }

    @Override
    public void endVisit(final CastExpression node) {
        varProcessor.cast(node, heap);
    }

    @Override
    public void endVisit(final VariableDeclarationExpression node) {
        Type type = node.getType();
        List<VariableDeclaration> vdList =
                varProcessor.getVariableDeclarations(node);
        Map<IVar, VariableDeclaration> varMap =
                varProcessor.stageLocalVars(type, vdList, heap);
        invokeProcessor.stageLocalVarWhen(varMap, heap);
    }

    @Override
    public void endVisit(final ForStatement node) {
        // do nothing, initializer var is staged by
        // VariableDeclarationExpression and Assignment visit
    }

    @Override
    public void endVisit(final EnhancedForStatement node) {
        /*
         * For each local var is staged by SingleVariableDeclaration visit. Even
         * if is not used by other, here we just mark it as used for tester
         * convenience.
         */
        SingleVariableDeclaration svd = node.getParameter();
        String name = nodes.getVariableName(svd);
        useMarker.mark(name, heap);
    }

    @Override
    public void endVisit(final SingleVariableDeclaration node) {
        List<VariableDeclaration> vdList = new ArrayList<>();
        vdList.add(node);
        Type type = node.getType();

        // svd may be parameter or local var
        ASTNode parent = node.getParent();
        if (nodes.is(parent, MethodDeclaration.class)) {
            varProcessor.stageParameters(type, vdList, heap);
        } else {
            Map<IVar, VariableDeclaration> fragments =
                    varProcessor.stageLocalVars(type, vdList, heap);
            invokeProcessor.stageLocalVarWhen(fragments, heap);
        }
    }

    @Override
    public void endVisit(final StringLiteral node) {
        literalProcessor.process(node, heap);
        varProcessor.stageInferVarForLiteralReturn(node, heap);
    }

    @Override
    public void endVisit(final NumberLiteral node) {
        varProcessor.stageInferVarForLiteralReturn(node, heap);
    }

    @Override
    public void endVisit(final CharacterLiteral node) {
        varProcessor.stageInferVarForLiteralReturn(node, heap);
    }

    @Override
    public void endVisit(final NullLiteral node) {
        varProcessor.stageInferVarForLiteralReturn(node, heap);
    }

    @Override
    public void endVisit(final TypeLiteral node) {
        varProcessor.stageInferVarForLiteralReturn(node, heap);
    }

    @Override
    public void endVisit(final QualifiedName node) {
        varProcessor.stageInferVarForLiteralReturn(node, heap);
    }

    @Override
    public boolean visit(final AnonymousClassDeclaration node) {
        return false; // don't visit child nodes
    }

    public void setHeap(final Heap heap) {
        this.heap = heap;
    }
}
