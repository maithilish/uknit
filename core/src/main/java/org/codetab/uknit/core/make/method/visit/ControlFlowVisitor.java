package org.codetab.uknit.core.make.method.visit;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.List;
import java.util.NoSuchElementException;

import javax.inject.Inject;

import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.tree.TreeFactory;
import org.codetab.uknit.core.tree.TreeNode;
import org.codetab.uknit.core.tree.Trees;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TryStatement;

/**
 * Visit the method and create control flow path tree. The tree is partial
 * representation of the AST. Only the interesting statements and blocks are
 * added to the tree skipping statements such as ForStatement etc., At present
 * control paths for only If and Try constructs are implemented.
 * @author m
 *
 */
public class ControlFlowVisitor extends ASTVisitor {

    @Inject
    private Nodes nodes;
    @Inject
    private NodeFactory nodeFactory;
    @Inject
    private Trees trees;
    @Inject
    private TreeFactory treeFactory;
    @Inject
    private Ancestors ancestors;

    private TreeNode<ASTNode> tree;

    public void setup() {
        tree = null;
    }

    /**
     * For top level method create new tree from methodDecl. For inner methods
     * such as anonymous object's method add it to the existing tree.
     */
    @Override
    public boolean visit(final MethodDeclaration methodDecl) {
        TreeNode<ASTNode> parentTNode;
        if (isNull(tree)) {
            tree = treeFactory.createTreeNode(methodDecl);
            parentTNode = tree;
        } else {
            // for inner method such as anonymous object's method.
            parentTNode = ancestors.findNearest(tree, methodDecl);
        }
        Block body = methodDecl.getBody();
        if (body == null) {
            // abstract method
            return true;
        }
        TreeNode<ASTNode> blockTNode = treeFactory.createTreeNode(body);
        parentTNode.add(blockTNode);
        return true;
    }

    /**
     * Add if block or statement. In case else statement exists, then add it
     * else add empty block to test the second path.
     */
    @Override
    public boolean visit(final IfStatement ifStmt) {
        TreeNode<ASTNode> parentTNode = ancestors.findNearest(tree, ifStmt);
        TreeNode<ASTNode> ifTNode = treeFactory.createTreeNode(ifStmt);
        parentTNode.add(ifTNode);

        Statement thenStmt = ifStmt.getThenStatement();

        TreeNode<ASTNode> thenTNode = treeFactory.createTreeNode(thenStmt);
        ifTNode.add(thenTNode);

        Statement elseStmt = ifStmt.getElseStatement();
        if (nonNull(elseStmt)) {
            TreeNode<ASTNode> elseTNode = treeFactory.createTreeNode(elseStmt);
            ifTNode.add(elseTNode);
        } else {
            TreeNode<ASTNode> emptyBlockTNode =
                    treeFactory.createTreeNode(nodeFactory.createBlock());
            ifTNode.add(emptyBlockTNode);
        }
        return true;
    }

    /**
     * For tryStmt, add try body. The finally block is not added here but
     * delegated to endVisit(Block).
     */
    @Override
    public boolean visit(final TryStatement tryStmt) {
        TreeNode<ASTNode> parentTNode = ancestors.findNearest(tree, tryStmt);
        TreeNode<ASTNode> tryTNode = treeFactory.createTreeNode(tryStmt);
        parentTNode.add(tryTNode);

        // add try body block, finally block is added by block end visit
        Block tryBody = tryStmt.getBody();
        TreeNode<ASTNode> blockTNode = treeFactory.createTreeNode(tryBody);
        tryTNode.add(blockTNode);

        return true;
    }

    /**
     * For catchClause add try body and catchClause.
     */
    @Override
    public boolean visit(final CatchClause catchClause) {

        TryStatement tryStmt =
                nodes.as(catchClause.getParent(), TryStatement.class);
        TreeNode<ASTNode> tryTNode = ancestors.findNearest(tree, catchClause);

        TreeNode<ASTNode> tryBlockTNode =
                treeFactory.createTreeNode(tryStmt.getBody());
        TreeNode<ASTNode> catchTNode = treeFactory.createTreeNode(catchClause);
        TreeNode<ASTNode> catchBlockTNode =
                treeFactory.createTreeNode(catchClause.getBody());

        // add tryBody catch catchBody
        tryTNode.add(tryBlockTNode);
        tryBlockTNode.add(catchTNode);
        catchTNode.add(catchBlockTNode);

        return true;
    }

    /**
     * If TryStmt finally block, then add it to all leaves of try block.
     */
    @Override
    public void endVisit(final Block block) {
        ASTNode parent = block.getParent();
        if (nodes.is(parent, TryStatement.class)) {
            TryStatement tryStmt = nodes.as(parent, TryStatement.class);
            Block tryFinallyBlock = tryStmt.getFinally();
            // add try finally block to all leaves of try block
            if (block.equals(tryFinallyBlock)) {
                TreeNode<ASTNode> parentTNode = trees.findOne(tree, tryStmt);
                List<TreeNode<ASTNode>> leaves = trees.findLeaves(parentTNode);
                for (TreeNode<ASTNode> leaf : leaves) {
                    TreeNode<ASTNode> blockTNode =
                            treeFactory.createTreeNode(block);
                    leaf.add(blockTNode);
                }
            }
        }
    }

    /**
     * Blocks of MethodDeclaration, IfStatement, TryStatement and CatchClause
     * are added in above visits. Other statements such as ForStatement etc.,
     * may contain blocks they are added here. Such blocks may in turn contains
     * if or try and to handle them we need all blocks in tree. We ignore the
     * ForStatement but add its block to the tree.
     */
    @Override
    public boolean visit(final Block block) {
        ASTNode parent = block.getParent();
        if (nodes.is(parent, MethodDeclaration.class)
                || nodes.is(parent, IfStatement.class)
                || nodes.is(parent, TryStatement.class)
                || nodes.is(parent, CatchClause.class)) {
            return true;
        }

        TreeNode<ASTNode> parentTNode = ancestors.findNearest(tree, block);
        TreeNode<ASTNode> blockTNode = treeFactory.createTreeNode(block);
        parentTNode.add(blockTNode);

        return true;
    }

    public TreeNode<ASTNode> getTree() {
        return tree;
    }
}

class Ancestors {

    @Inject
    private Trees trees;

    /**
     * Scroll up through the node's parent nodes and return the first parent
     * that exists in the control path tree.
     * @param tree
     * @param node
     * @return
     */
    TreeNode<ASTNode> findNearest(final TreeNode<ASTNode> tree,
            final ASTNode node) {
        ASTNode parent = node;
        TreeNode<ASTNode> ancestor = null;
        while ((parent = parent.getParent()) != null) {
            try {
                ancestor = trees.findOne(tree, parent);
                break;
            } catch (NoSuchElementException e) {
                // look for next ancestor
            }
        }
        if (nonNull(ancestor)) {
            return ancestor;
        } else {
            throw new NoSuchElementException("unable to find nearest ancestor");
        }
    }

}
