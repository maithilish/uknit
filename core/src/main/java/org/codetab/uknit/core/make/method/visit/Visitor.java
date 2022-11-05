package org.codetab.uknit.core.make.method.visit;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.process.Assignor;
import org.codetab.uknit.core.make.method.process.ReturnCreator;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar.Kind;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Types;
import org.codetab.uknit.core.node.Variables;
import org.codetab.uknit.core.tree.TreeNode;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.InfixExpression;
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
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class Visitor extends ASTVisitor {

    @Inject
    private Types types;
    @Inject
    private Nodes nodes;
    @Inject
    private Packer packer;
    @Inject
    private ReturnCreator returnCreator;
    @Inject
    private Assignor assignor;
    @Inject
    private Variables variables;

    private Heap heap;

    /*
     * whether internal method call
     */
    private boolean imc;

    /*
     * if true then separate test method is created for each control flow path
     * else creates single combined method.
     */
    private boolean splitOnControlFlow;

    /*
     * Control flow path.
     */
    private List<TreeNode<ASTNode>> ctlPath;
    private Deque<Boolean> inCtlFlowPathState = new ArrayDeque<>();
    private boolean inCtlPath;

    /*
     * return type of method under test
     */
    private Type methodReturnType;

    /**
     * Used for formal Parameter. SingleVariableDeclaration (svd) nodes are used
     * in formal parameter lists and catch clauses. They are not used for field
     * (FieldDeclaration is used for fields) and regular variable declaration
     * statements. The VariableDeclaration (super of svd) defines name and
     * initializer.
     */
    @Override
    public void endVisit(final SingleVariableDeclaration node) {
        List<VariableDeclaration> vdList = List.of(node);
        Type type = types.getType(node);

        // svd may be parameter or local var (catch clause)
        if (nodes.is(node.getParent(), MethodDeclaration.class)) {
            packer.packVars(Kind.PARAMETER, type, vdList, inCtlPath, heap);
        } else {
            packer.packVars(Kind.LOCAL, type, vdList, inCtlPath, heap);
        }
    }

    /**
     * Used for local vars. VariableDeclarationStatement collects several
     * VariableDeclarationFragment into a Statement. It is used for Local
     * variable declaration. It holds type and fragments (vdf). The
     * VariableDeclaration (super of fragment) defines name and initializer.
     */
    @Override
    public void endVisit(final VariableDeclarationStatement node) {
        Type type = node.getType();
        List<VariableDeclaration> vdList = variables.getFragments(node);
        packer.packVars(Kind.LOCAL, type, vdList, inCtlPath, heap);
    }

    /**
     * Used for For stmt. VariableDeclarationExpression (vde) collects together
     * several VariableDeclarationFragment (vdf) into a single Expression. It
     * can be used as the initializer of a ForStatement or with
     * ExpressionStatement. The VariableDeclaration (super of fragment) defines
     * name and initializer.
     */
    @Override
    public void endVisit(final VariableDeclarationExpression node) {
        Type type = node.getType();
        List<VariableDeclaration> vdList = variables.getFragments(node);
        packer.packVars(Kind.LOCAL, type, vdList, inCtlPath, heap);
    }

    @Override
    public void endVisit(final MethodInvocation node) {
        Invoke invoke = packer.createInvoke(node, inCtlPath, heap);
        packer.setupInvokes(invoke, heap);
        heap.addPack(invoke);
    }

    @Override
    public void endVisit(final SuperMethodInvocation node) {
        Invoke invoke = packer.createInvoke(node, inCtlPath, heap);
        packer.setupInvokes(invoke, heap);
        heap.addPack(invoke);
    }

    @Override
    public void endVisit(final ReturnStatement node) {
        returnCreator.createReturnVar(node, methodReturnType, heap);
    }

    /**
     * LHS of assignment, apart from var, can be array access (array[0]), field
     * access ((point).x) or qualified name (point.x).
     */
    @Override
    public void endVisit(final Assignment node) {
        assignor.process(node, heap);
    }

    @Override
    public void endVisit(final ClassInstanceCreation node) {
        packer.packExp(node, inCtlPath, heap);
    }

    @Override
    public void endVisit(final ArrayCreation node) {
        packer.packExp(node, inCtlPath, heap);
    }

    @Override
    public void endVisit(final InfixExpression node) {
        packer.packExp(node, inCtlPath, heap);
    }

    @Override
    public void endVisit(final PrefixExpression node) {
        packer.packExp(node, inCtlPath, heap);
    }

    @Override
    public void endVisit(final PostfixExpression node) {
        packer.packExp(node, inCtlPath, heap);
    }

    @Override
    public void endVisit(final ConditionalExpression node) {
        packer.packExp(node, inCtlPath, heap);
    }

    @Override
    public void endVisit(final ArrayAccess node) {
        packer.packExp(node, inCtlPath, heap);
    }

    @Override
    public void endVisit(final StringLiteral node) {
        packer.packLiteralExp(node, inCtlPath, heap);
    }

    @Override
    public void endVisit(final NumberLiteral node) {
        packer.packLiteralExp(node, inCtlPath, heap);
    }

    @Override
    public void endVisit(final CharacterLiteral node) {
        packer.packLiteralExp(node, inCtlPath, heap);
    }

    @Override
    public void endVisit(final NullLiteral node) {
        packer.packLiteralExp(node, inCtlPath, heap);
    }

    @Override
    public void endVisit(final TypeLiteral node) {
        packer.packLiteralExp(node, inCtlPath, heap);
    }

    @Override
    public void endVisit(final BooleanLiteral node) {
        packer.packLiteralExp(node, inCtlPath, heap);
    }

    @Override
    public void endVisit(final QualifiedName node) {
        packer.packLiteralExp(node, inCtlPath, heap);
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
        if (imc) {
            inCtlPath = true;
            return true;
        }
        if (splitOnControlFlow) {
            // save the enclosing block state and set new state
            inCtlFlowPathState.push(inCtlPath);
            inCtlPath = ctlPath.stream()
                    .anyMatch(treeNode -> treeNode.getObject().equals(node)
                            && treeNode.isEnable());
            return true;
        } else {
            // for combined test method process all blocks.
            inCtlPath = true;
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
        inCtlPath = state;
    }

    /*
     * setters
     */
    public void setHeap(final Heap heap) {
        this.heap = heap;
    }

    public void setCtlPath(final List<TreeNode<ASTNode>> ctlPath) {
        this.ctlPath = ctlPath;
    }

    public void setSplitOnControlFlow(final boolean splitOnControlFlow) {
        this.splitOnControlFlow = splitOnControlFlow;
    }

    public void setImc(final boolean imc) {
        this.imc = imc;
    }

    public void setMethodReturnType(final Type returnType) {
        this.methodReturnType = returnType;
    }
}
