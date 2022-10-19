package org.codetab.uknit.core.make.method.visit;

import static java.util.Objects.nonNull;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.detect.insert.Inserter;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Types;
import org.codetab.uknit.core.node.Variables;
import org.codetab.uknit.core.tree.TreeNode;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.LambdaExpression;
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
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

/**
 * Visitor to create test methods.
 * @author m
 *
 */
public class Visitor extends ASTVisitor {

    @Inject
    private PatchProcessor patchProcessor;
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
    private Inserter inserter;
    @Inject
    private Variables variables;
    @Inject
    private Nodes nodes;
    @Inject
    private Types types;

    @Inject
    private VarEnabler varEnabler;

    private Heap heap;

    private Deque<Boolean> inCtlFlowPathState = new ArrayDeque<>();

    /*
     * whether internal method call visitor
     */
    private boolean internalMethod = false;

    /*
     * Control flow path.
     */
    private List<TreeNode<ASTNode>> ctlPath;

    /*
     * if true then separate test method is created for each control flow path
     * else creates single combined method.
     */
    private boolean splitOnControlFlow;

    @Override
    public void endVisit(final VariableDeclarationStatement node) {
        Type type = node.getType();
        List<VariableDeclaration> vdList =
                varProcessor.getVariableDeclarations(node);
        Map<IVar, VariableDeclaration> varMap =
                varProcessor.stageLocalVars(type, vdList, internalMethod, heap);
        invokeProcessor.stageLocalVarWhen(varMap, heap);
    }

    @Override
    public void endVisit(final Assignment node) {
        patchProcessor.stageAssignmentPatches(node, heap);
        assignProcessor.process(node, heap);
    }

    /**
     * Collect invocation details in Invoke and if required, stage InferVar and
     * When.
     */
    @Override
    public void endVisit(final MethodInvocation node) {

        patchProcessor.stageMiPatches(node, heap);

        Invoke invoke = invokeProcessor.process(node, heap);
        heap.getInvokes().add(invoke);

        IVar var = invoke.getCallVar();
        if (nonNull(var) && variables.isStatic(var)) {
            return;
        }

        if (invokeProcessor.nonStubable(invoke)) {
            return;
        }

        if (invoke.isInfer()) {
            if (invoke.getReturnVar().isPresent()) {
                /*
                 * for internal call arg in internal call (see internal itest),
                 * stage expVar
                 */
                invokeProcessor.stageExpVar(invoke, heap);
            } else {
                invokeProcessor.stageInferVar(invoke, heap);
            }
            if (invoke.isWhen()) {
                invokeProcessor.stageInferVarWhen(invoke, heap);
            }
        }
    }

    @Override
    public void endVisit(final SuperMethodInvocation node) {
        Invoke invoke = invokeProcessor.process(node, heap);
        heap.getInvokes().add(invoke);
        Optional<IVar> retVar = invoke.getReturnVar();
        if (invoke.isInfer()) {
            if (invoke.getReturnVar().isPresent()) {
                /*
                 * for internal call arg in internal call (see internal itest),
                 * stage expVar
                 */
                invokeProcessor.stageExpVar(invoke, heap);
            } else {
                invokeProcessor.stageInferVar(invoke, heap);
            }
        }
        patchProcessor.stageSuperMiPatch(node, retVar, heap);
    }

    @Override
    public void endVisit(final ReturnStatement node) {
        patchProcessor.stageReturnStmtPatches(node, heap);

        Optional<IVar> returnVar = returnProcessor.process(node, heap);
        if (returnProcessor.isReturnable(returnVar, heap)) {
            heap.setExpectedVar(returnVar);
        }
    }

    @Override
    public void endVisit(final InfixExpression node) {
        patchProcessor.stageInfixPatches(node, heap);
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
        patchProcessor.stageInstanceCreationPatches(node, heap);
        createProcessor.process(node, heap);
    }

    @Override
    public void endVisit(final ArrayCreation node) {
        patchProcessor.stageArrayCreationPatches(node, heap);
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
                varProcessor.stageLocalVars(type, vdList, internalMethod, heap);
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
         * Ex: for(String key: list) - Even though key is not used by when etc.,
         * we force enable it so the tester can add an item to list.
         */
        SingleVariableDeclaration svd = node.getParameter();
        String name = nodes.getVariableName(svd);
        varEnabler.enforce(name, Optional.of(true), heap);
        inserter.processForEach(node, internalMethod, heap);
    }

    @Override
    public void endVisit(final SingleVariableDeclaration node) {
        List<VariableDeclaration> vdList = List.of(node);
        // Type type = node.getType();
        Type type = types.getType(node);

        // svd may be parameter or local var
        ASTNode parent = node.getParent();
        if (nodes.is(parent, MethodDeclaration.class)) {
            if (internalMethod) {
                // stage internal method parameters as local vars
                varProcessor.stageLocalVars(type, vdList, internalMethod, heap);
            } else {
                varProcessor.stageParameters(type, vdList, heap);
            }
        } else {
            Map<IVar, VariableDeclaration> fragments = varProcessor
                    .stageLocalVars(type, vdList, internalMethod, heap);
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
    public boolean visit(final LambdaExpression node) {
        return false; // don't visit child nodes
    }

    @Override
    public boolean visit(final AnonymousClassDeclaration node) {
        return false; // don't visit child nodes
    }

    @Override
    public boolean visit(final TypeDeclaration node) {
        return false; // for now don't process local class inside a method
    }

    /**
     * Decides how blocks are processed based on Control Flow Path.
     * <p>
     * Returns true for blocks that are in ctlPath which ensures that child
     * nodes of blocks are processed and added to test method. Returns false for
     * the blocks that are not in ctlPath and the block's statements are
     * ignored.
     */
    @Override
    public boolean visit(final Block node) {
        // ctl flow for internal method call is not yet implemented
        if (internalMethod) {
            heap.setInCtlFlowPath(true);
            return true;
        }
        if (splitOnControlFlow) {
            // save the enclosing block state and set new state
            inCtlFlowPathState.push(heap.isInCtlFlowPath());
            heap.setInCtlFlowPath(ctlPath.stream()
                    .anyMatch(treeNode -> treeNode.getObject().equals(node)
                            && treeNode.isEnable()));
            return true;
        } else {
            // for combined test method process all blocks.
            heap.setInCtlFlowPath(true);
            return true;
        }
    }

    @Override
    public void endVisit(final Block node) {
        boolean state;
        try {
            // restore previous state - enclosing block state
            state = inCtlFlowPathState.pop();
        } catch (NoSuchElementException e) {
            state = true;
        }
        heap.setInCtlFlowPath(state);
    }

    /*
     * setters
     */
    public void setHeap(final Heap heap) {
        this.heap = heap;
    }

    public void setInternalMethod(final boolean internalMethod) {
        this.internalMethod = internalMethod;
    }

    public void setCtlPath(final List<TreeNode<ASTNode>> ctlPath) {
        this.ctlPath = ctlPath;
    }

    public void setSplitOnControlFlow(final boolean splitOnControlFlow) {
        this.splitOnControlFlow = splitOnControlFlow;
    }
}
