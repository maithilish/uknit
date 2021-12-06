package org.codetab.uknit.core.make.method.visit;

import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.LocalVar;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
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

    private Heap heap;

    @Override
    public void endVisit(final VariableDeclarationStatement node) {
        Map<LocalVar, VariableDeclarationFragment> fragments =
                varProcessor.stageLocalVar(node, heap);
        invokeProcessor.stageLocalVarWhen(fragments, heap);
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
        Optional<IVar> returnVar = returnProcessor.getExpectedVar(node, heap);
        heap.setExpectedVar(returnVar);
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
    public void endVisit(final Assignment node) {
        assignProcessor.process(node, heap);
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
    public void endVisit(final StringLiteral node) {
        literalProcessor.process(node, heap);
        varProcessor.stageInferVarForReturn(node, heap);
    }

    @Override
    public void endVisit(final NumberLiteral node) {
        varProcessor.stageInferVarForReturn(node, heap);
    }

    @Override
    public void endVisit(final CharacterLiteral node) {
        varProcessor.stageInferVarForReturn(node, heap);
    }

    @Override
    public void endVisit(final NullLiteral node) {
        varProcessor.stageInferVarForReturn(node, heap);
    }

    @Override
    public void endVisit(final TypeLiteral node) {
        varProcessor.stageInferVarForReturn(node, heap);
    }

    @Override
    public boolean visit(final AnonymousClassDeclaration node) {
        return false; // don't visit child nodes
    }

    public void setHeap(final Heap heap) {
        this.heap = heap;
    }
}
