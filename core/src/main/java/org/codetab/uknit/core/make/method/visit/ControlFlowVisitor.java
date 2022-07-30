package org.codetab.uknit.core.make.method.visit;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
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
    private TreeFactory treeFactory;
    @Inject
    private NodeFinder nodeFinder;

    private TreeNode<ASTNode> tree;
    private TreeNode<ASTNode> methodTBlock;
    /*
     * only first ctl node is top level ctl node
     */
    private boolean topLevelCtlNode;

    public void setup() {
        tree = null;
        topLevelCtlNode = true;
    }

    /**
     * For top level method create new tree from methodDecl. For inner methods
     * such as anonymous object's method add it to the existing tree.
     */
    @Override
    public boolean visit(final MethodDeclaration methodDecl) {
        TreeNode<ASTNode> parentTNode;
        if (isNull(tree)) {
            tree = treeFactory.createTreeNode(methodDecl, "");
            parentTNode = tree;
        } else {
            // for inner method such as anonymous object's method.
            parentTNode = nodeFinder.findAncestor(tree, methodDecl);
        }
        Block body = methodDecl.getBody();
        if (body == null) {
            // abstract method
            return true;
        }
        methodTBlock = treeFactory.createTreeNode(body, "md");
        parentTNode.add(methodTBlock);
        return true;
    }

    /**
     * Add if block or statement. In case else statement exists, then add it
     * else add empty block to test the second path.
     */
    @Override
    public boolean visit(final IfStatement ifStmt) {

        TreeNode<ASTNode> pTNode = nodeFinder.findAncestor(tree, ifStmt);
        List<TreeNode<ASTNode>> parentNodes =
                nodeFinder.findParentNodes(tree, pTNode);

        // for each branch add if branches for completeness
        for (int i = 0; i < parentNodes.size(); i++) {
            TreeNode<ASTNode> parentTNode = parentNodes.get(i);
            parentTNode = nodeFinder.findFinally(parentTNode);
            TreeNode<ASTNode> ifTNode = treeFactory.createTreeNode(ifStmt, "");
            Statement thenStmt = ifStmt.getThenStatement();
            TreeNode<ASTNode> thenTNode =
                    treeFactory.createTreeNode(thenStmt, "if");

            // add if (to parent as new branch) and then block
            parentTNode.add(ifTNode);
            ifTNode.add(thenTNode);

            // add else only to the first branch to avoid repeats of else test
            if (i == 0) {
                // if exists, add else block else empty block
                Statement elseStmt = ifStmt.getElseStatement();
                if (nonNull(elseStmt)) {
                    TreeNode<ASTNode> elseTNode =
                            treeFactory.createTreeNode(elseStmt, "else");
                    ifTNode.add(elseTNode);
                } else {
                    /*
                     * when this if is the top ctl flow node in the method block
                     * then add empty block for non existence else path.
                     * Otherwise some other top level ctl node path takes care
                     * of else path.
                     */
                    if (topLevelCtlNode) {
                        TreeNode<ASTNode> emptyBlockTNode = treeFactory
                                .createTreeNode(nodeFactory.createBlock(),
                                        "empty else");
                        ifTNode.add(emptyBlockTNode);
                    }
                }
            }
        }
        topLevelCtlNode = false;
        return true;
    }

    /**
     * For tryStmt, add try body. The finally block is not added here but
     * delegated to endVisit(Block).
     */
    @Override
    public boolean visit(final TryStatement tryStmt) {

        TreeNode<ASTNode> pTNode = nodeFinder.findAncestor(tree, tryStmt);
        List<TreeNode<ASTNode>> parentNodes =
                nodeFinder.findParentNodes(tree, pTNode);

        // for each branch add if branches for completeness
        for (int i = 0; i < parentNodes.size(); i++) {

            TreeNode<ASTNode> parentTNode = parentNodes.get(i);
            parentTNode = nodeFinder.findFinally(parentTNode);

            TreeNode<ASTNode> tryTNode =
                    treeFactory.createTreeNode(tryStmt, "");
            TreeNode<ASTNode> bodyTNode =
                    treeFactory.createTreeNode(tryStmt.getBody(), "try");

            // add try (to parent as new branch), body and finally
            parentTNode.add(tryTNode);
            tryTNode.add(bodyTNode);

            Block finallyBlock = tryStmt.getFinally();
            if (nonNull(finallyBlock)) {
                TreeNode<ASTNode> finallyTNode = treeFactory
                        .createTreeNode(tryStmt.getFinally(), "finally");
                bodyTNode.add(finallyTNode);
            }
        }
        topLevelCtlNode = false;
        return true;
    }

    /**
     * For catchClause add try body and catchClause.
     */
    @Override
    public boolean visit(final CatchClause catchClause) {
        TryStatement tryStmt =
                nodes.as(catchClause.getParent(), TryStatement.class);
        TreeNode<ASTNode> tryTNode = nodeFinder.findAncestor(tree, catchClause);
        /*
         * don't look for finally, we add try block to create new branch
         */

        TreeNode<ASTNode> tryBlockTNode =
                treeFactory.createTreeNode(tryStmt.getBody(), "try");
        TreeNode<ASTNode> catchTNode =
                treeFactory.createTreeNode(catchClause, "");
        TreeNode<ASTNode> catchBlockTNode =
                treeFactory.createTreeNode(catchClause.getBody(), "catch");

        // add tryBody (to try as new branch), catch, body and finally
        tryTNode.add(tryBlockTNode);
        tryBlockTNode.add(catchTNode);
        catchTNode.add(catchBlockTNode);

        Block finallyBlock = tryStmt.getFinally();
        if (nonNull(finallyBlock)) {
            TreeNode<ASTNode> finallyTNode =
                    treeFactory.createTreeNode(tryStmt.getFinally(), "finally");
            catchBlockTNode.add(finallyTNode);
        }

        return true;
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

        TreeNode<ASTNode> parentTNode = nodeFinder.findAncestor(tree, block);
        parentTNode = nodeFinder.findFinally(parentTNode);

        TreeNode<ASTNode> blockTNode =
                treeFactory.createTreeNode(block, "block");
        parentTNode.add(blockTNode);

        return true;
    }

    public TreeNode<ASTNode> getTree() {
        return tree;
    }
}

class NodeFinder {

    @Inject
    private Trees trees;
    @Inject
    private Nodes nodes;

    /**
     * Scroll up through the node's parent nodes and return the first parent
     * that exists in the control path tree.
     * @param tree
     * @param node
     * @return
     */
    TreeNode<ASTNode> findAncestor(final TreeNode<ASTNode> tree,
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

    /**
     * If node, such as finally, can occur in multiple branches then return list
     * of all its occurrences otherwise return list with the single node.
     * @param node
     * @return
     */
    public List<TreeNode<ASTNode>> findParentNodes(final TreeNode<ASTNode> tree,
            final TreeNode<ASTNode> node) {
        ASTNode parent = node.getObject().getParent();
        // same finally can occur in multiple branches
        if (nodes.is(parent, TryStatement.class) && node.getObject()
                .equals(nodes.as(parent, TryStatement.class).getFinally())) {
            return trees.findAll(tree, node.getObject());
        } else {
            ArrayList<TreeNode<ASTNode>> list = new ArrayList<>();
            list.add(node);
            // return list;
            return trees.findAll(tree, node.getObject());
        }
    }

    /**
     * If tree object parent is TryStatement or CatchClause then look for try's
     * finally block. If found return it else return the tree itself.
     * @param tree
     * @return
     */
    TreeNode<ASTNode> findFinally(final TreeNode<ASTNode> tree) {
        ASTNode parent = tree.getObject().getParent();
        Block finallyBlock = null;
        if (nodes.is(parent, TryStatement.class)) {
            finallyBlock = nodes.as(parent, TryStatement.class).getFinally();
        }
        if (nodes.is(parent, CatchClause.class)) {
            finallyBlock = nodes.as(parent.getParent(), TryStatement.class)
                    .getFinally();
        }
        try {
            TreeNode<ASTNode> child = tree.getChildAt(0);
            if (child.getObject().equals(finallyBlock)) {
                return child;
            }
        } catch (NoSuchElementException e) {
        }
        return tree;
    }
}
