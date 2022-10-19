package org.codetab.uknit.core.zap.make.method.visit;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.node.Expressions;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Types;
import org.codetab.uknit.core.tree.TreeFactory;
import org.codetab.uknit.core.tree.TreeNode;
import org.codetab.uknit.core.tree.Trees;
import org.codetab.uknit.core.util.StringUtils;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.Type;

/**
 * Creates Control Flow Path (CFP) Tree. Visits the method and creates control
 * flow path tree. The tree is partial representation of the AST. Only the
 * interesting statements and blocks are added to the tree skipping statements
 * such as ForStatement etc., At present control paths for only If and Try
 * constructs are implemented.
 * @author m
 *
 */
public class PathFinder extends ASTVisitor {

    @Inject
    private Nodes nodes;
    @Inject
    private Types types;
    @Inject
    private Configs configs;
    @Inject
    private Expressions expressions;
    @Inject
    private NodeFactory nodeFactory;
    @Inject
    private TreeFactory treeFactory;
    @Inject
    private NodeFinder nodeFinder;

    private TreeNode<ASTNode> tree;
    private TreeNode<ASTNode> methodTBlock;
    private AtomicInteger idCounter;
    private Properties properties;

    public void setup() {
        tree = null;
        idCounter = new AtomicInteger(1);
        properties = configs.getProperties("uknit.controlFlow.method.name");
    }

    /**
     * For top level method create new tree from methodDecl. For inner methods
     * such as anonymous object's method add it to the existing tree.
     */
    @Override
    public boolean visit(final MethodDeclaration methodDecl) {
        TreeNode<ASTNode> parentTNode;
        if (isNull(tree)) {
            tree = treeFactory.createTreeNode(idCounter.getAndIncrement(),
                    methodDecl, "");
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
        methodTBlock = treeFactory.createTreeNode(idCounter.getAndIncrement(),
                body, "md");
        parentTNode.add(methodTBlock);
        return true;
    }

    /**
     * Add if block or statement. In case else statement exists, then add it
     * else add empty block to test the second path.
     */
    @Override
    public boolean visit(final IfStatement ifStmt) {

        TreeNode<ASTNode> parentTNode = null;

        List<TreeNode<ASTNode>> leafTNodes;
        parentTNode = nodeFinder.findElseIf(tree, ifStmt);
        boolean isElseIf;

        // ifName for if(canSwim) is CanSwim, for if(foo.swim(...)) is fooSwim
        List<String> names = new ArrayList<>();
        expressions.collectNames(ifStmt.getExpression(), names, properties);
        String ifName = StringUtils.concatCapitalized(names);

        /*
         * if if-else find leaves of all its occurrences. if if-else-if treat
         * ifStmt of else as the leaf.
         *
         */
        if (isNull(parentTNode)) {
            isElseIf = false;
            parentTNode = nodeFinder.findAncestor(tree, ifStmt);
            parentTNode = nodeFinder.findBranchingParent(tree, parentTNode);
            leafTNodes = nodeFinder.findAllLeaves(tree, parentTNode);
        } else {
            isElseIf = true;
            leafTNodes = new ArrayList<>();
            leafTNodes.add(parentTNode);
        }

        // optional else stmt
        Statement elseStmt = ifStmt.getElseStatement();
        String elseName = properties.getProperty("suffix.else");

        // if else doesn't exists, create empty else block
        if (isNull(elseStmt)) {
            elseStmt = nodeFactory.createBlock();
            elseName = "empty else";
        }

        // add if branches to each branch to complete the path
        for (int i = 0; i < leafTNodes.size(); i++) {

            TreeNode<ASTNode> leafTNode = leafTNodes.get(i);
            leafTNode = nodeFinder.findTerminalNode(leafTNode);

            /*
             * Add if (to parent as new branch) and then block. If earlier if is
             * else-if then it has already added the ifStmt so don't add again.
             */
            TreeNode<ASTNode> ifTNode;
            if (isElseIf) {
                ifTNode = parentTNode;
            } else {
                ifTNode = treeFactory.createTreeNode(
                        idCounter.getAndIncrement(), ifStmt, ifName);
                leafTNode.add(ifTNode);
            }

            String thenName = properties.getProperty("suffix.if");
            Statement thenStmt = ifStmt.getThenStatement();
            TreeNode<ASTNode> thenTNode = treeFactory.createTreeNode(
                    idCounter.getAndIncrement(), thenStmt, thenName);
            ifTNode.add(thenTNode);

            // add optional else or empty else
            TreeNode<ASTNode> elseTNode = treeFactory.createTreeNode(
                    idCounter.getAndIncrement(), elseStmt, elseName);
            elseTNode.setEnable(false);
            ifTNode.add(elseTNode);
        }
        return true;
    }

    /**
     * For tryStmt, add try body and finally block if exists.
     */
    @Override
    public boolean visit(final TryStatement tryStmt) {

        TreeNode<ASTNode> parentTNode = nodeFinder.findAncestor(tree, tryStmt);
        parentTNode = nodeFinder.findBranchingParent(tree, parentTNode);

        List<TreeNode<ASTNode>> leafTNodes =
                nodeFinder.findAllLeaves(tree, parentTNode);

        // for each branch add try branches to complete the path
        for (int i = 0; i < leafTNodes.size(); i++) {

            TreeNode<ASTNode> leafTNode = leafTNodes.get(i);
            leafTNode = nodeFinder.findTerminalNode(leafTNode);

            // add try (to parent as new branch) and body
            String name = properties.getProperty("suffix.try");
            TreeNode<ASTNode> tryTNode = treeFactory
                    .createTreeNode(idCounter.getAndIncrement(), tryStmt, name);
            TreeNode<ASTNode> bodyTNode = treeFactory.createTreeNode(
                    idCounter.getAndIncrement(), tryStmt.getBody(), name);
            leafTNode.add(tryTNode);
            tryTNode.add(bodyTNode);

            // if exists add finally block
            Block finallyBlock = tryStmt.getFinally();
            if (nonNull(finallyBlock)) {
                name = properties.getProperty("suffix.finally");
                TreeNode<ASTNode> finallyTNode =
                        treeFactory.createTreeNode(idCounter.getAndIncrement(),
                                tryStmt.getFinally(), name);
                bodyTNode.add(finallyTNode);
            }
        }
        return true;
    }

    /**
     * For catchClause add try body, catchClause and finally block if exists.
     */
    @Override
    public boolean visit(final CatchClause catchClause) {
        TryStatement tryStmt =
                nodes.as(catchClause.getParent(), TryStatement.class);
        TreeNode<ASTNode> tryTNode = nodeFinder.findAncestor(tree, catchClause);

        /*
         * Don't look for finally, we add try block to create new branch. Catch
         * clause may contain multiple exceptions, add a branch for each. Try
         * that comes after another if or try is added to earlier branches to
         * complete the path. There is no need to add catch to each branch.
         */
        List<Type> exceptions =
                types.getExceptionTypes(catchClause.getException().getType());

        for (int i = 0; i < exceptions.size(); i++) {
            String exceptionName = types.getTypeName(exceptions.get(i));
            String name = properties.getProperty("suffix.catch");
            TreeNode<ASTNode> tryBlockTNode = treeFactory.createTreeNode(
                    idCounter.getAndIncrement(), tryStmt.getBody(), "try");
            TreeNode<ASTNode> catchTNode = treeFactory.createTreeNode(
                    idCounter.getAndIncrement(), catchClause,
                    String.join("", "catch", exceptionName));
            TreeNode<ASTNode> catchBlockTNode = treeFactory.createTreeNode(
                    idCounter.getAndIncrement(), catchClause.getBody(), name);

            // add tryBody (to try as new branch), catch, body and finally
            tryTNode.add(tryBlockTNode);
            tryBlockTNode.add(catchTNode);
            catchTNode.add(catchBlockTNode);

            Block finallyBlock = tryStmt.getFinally();
            if (nonNull(finallyBlock)) {
                name = properties.getProperty("suffix.finally");
                TreeNode<ASTNode> finallyTNode =
                        treeFactory.createTreeNode(idCounter.getAndIncrement(),
                                tryStmt.getFinally(), name);
                catchBlockTNode.add(finallyTNode);
            }
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
        parentTNode = nodeFinder.findTerminalNode(parentTNode);

        List<TreeNode<ASTNode>> leafTNodes =
                nodeFinder.findAllLeaves(tree, parentTNode);

        // add to each branch to complete the path
        for (int i = 0; i < leafTNodes.size(); i++) {

            TreeNode<ASTNode> leafTNode = leafTNodes.get(i);
            leafTNode = nodeFinder.findTerminalNode(leafTNode);

            TreeNode<ASTNode> blockTNode = treeFactory.createTreeNode(
                    idCounter.getAndIncrement(), block, "block");
            leafTNode.add(blockTNode);
        }

        return true;
    }

    /**
     * Enable disabled nodes. When multiple tree nodes contains same object then
     * only one is enabled. Disable entire subtree if node is disabled.
     * <p>
     * By default mandatory constructs such as try and if are enabled. When such
     * tree nodes with same object are found in multiple branches then are
     * enabled.
     * <p>
     * On the other hand optional constructs such as else and catch are disabled
     * by default. When such tree nodes with same object are found in multiple
     * branches then only one is enabled. The entire subtree of others are
     * disabled.
     * @param treeNode
     */
    public void enableUncoveredNodes(final TreeNode<ASTNode> treeNode) {
        List<ASTNode> coveredNodes =
                treeNode.stream().filter(TreeNode::isEnable)
                        .map(n -> n.getObject()).collect(Collectors.toList());

        List<TreeNode<ASTNode>> unCoveredNodes = treeNode.stream()
                .filter(n -> !n.isEnable()).collect(Collectors.toList());

        for (TreeNode<ASTNode> node : unCoveredNodes) {
            if (coveredNodes.contains(node.getObject())) {
                if (node.isEnable()) {
                    throw new IllegalStateException("expects disabled node");
                } else {
                    /*
                     * as similar node is already enabled don't enabled this
                     * node and disable the entire subtree
                     */
                    node.stream().forEach(n -> n.setEnable(false));
                }
            } else {
                node.setEnable(true);
                coveredNodes.add(node.getObject());
            }
        }
    }

    /**
     * Get tree of all ctl flow paths in a method.
     * @return
     */
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
     * Return true if ifStmt is else-if.
     * @param tree
     * @param ifStmt
     * @return
     */
    public TreeNode<ASTNode> findElseIf(final TreeNode<ASTNode> tree,
            final IfStatement ifStmt) {
        TreeNode<ASTNode> elseIfTNode = null;
        ASTNode parentNode = ifStmt.getParent();
        if (nodes.is(parentNode, IfStatement.class)) {
            IfStatement parentIf = nodes.as(parentNode, IfStatement.class);
            if (ifStmt.equals(parentIf.getElseStatement())) {
                elseIfTNode = trees.findOne(tree, parentIf.getElseStatement());
            }
        }
        return elseIfTNode;
    }

    /**
     * Get branching parent.
     * <p>
     * For finally block tryStmt is branching parent. For others input
     * parentTNode itself is the branching parent.
     * @param tree
     * @param parentTNode
     * @return
     */
    public TreeNode<ASTNode> findBranchingParent(final TreeNode<ASTNode> tree,
            final TreeNode<ASTNode> parentTNode) {
        TreeNode<ASTNode> branchingParent = parentTNode;
        ASTNode parentNode = parentTNode.getObject();
        // if finally block then branching parent is tryStmt
        if (nodes.is(parentNode, Block.class)
                && nodes.is(parentNode.getParent(), TryStatement.class)) {
            branchingParent = trees.findOne(tree, parentNode.getParent());
        }
        return branchingParent;
    }

    /**
     * Find the first tree node that contains the AST node.
     * @param tree
     * @param node
     * @return
     */
    TreeNode<ASTNode> findNode(final TreeNode<ASTNode> tree,
            final ASTNode node) {
        return trees.findOne(tree, node);
    }

    /**
     * Look for terminal treeNode for a treeNode.
     * <p>
     * If node's parent is TryStatement or CatchClause then look for try's
     * finally block. If found returns the finally else the input treeNode.
     * @param treeNode
     * @return
     */
    TreeNode<ASTNode> findTerminalNode(final TreeNode<ASTNode> treeNode) {
        TreeNode<ASTNode> terminalNode = treeNode;
        ASTNode parent = treeNode.getObject().getParent();
        if (nonNull(parent)) {
            Block finallyBlock = null;
            if (nodes.is(parent, TryStatement.class)) {
                finallyBlock =
                        nodes.as(parent, TryStatement.class).getFinally();
            }
            if (nodes.is(parent, CatchClause.class)) {
                finallyBlock = nodes.as(parent.getParent(), TryStatement.class)
                        .getFinally();
            }
            try {
                TreeNode<ASTNode> child = treeNode.getChildAt(0);
                if (child.getObject().equals(finallyBlock)) {
                    terminalNode = child;
                }
            } catch (NoSuchElementException e) {
            }
        }
        return terminalNode;
    }

    /**
     * Find matching parent node in the tree and for each occurrence find
     * leaves. Filter any empty else with same empty block.
     * @param tree
     * @param parentTNode
     * @return
     */
    public List<TreeNode<ASTNode>> findAllLeaves(final TreeNode<ASTNode> tree,
            final TreeNode<ASTNode> parentTNode) {

        List<TreeNode<ASTNode>> allLeaves = new ArrayList<>();

        List<ASTNode> emptyBlockDupes = new ArrayList<>();

        List<TreeNode<ASTNode>> allNodes =
                trees.findAll(tree, parentTNode.getObject());

        for (TreeNode<ASTNode> node : allNodes) {
            List<TreeNode<ASTNode>> leaves = trees.findLeaves(node);
            for (TreeNode<ASTNode> leaf : leaves) {
                boolean isEmptyBlock = nodes.isEmptyBlock(leaf.getObject());
                if (isEmptyBlock) {
                    /*
                     * Same empty else is added to all branches of a ctl node.
                     * This results in redundant tests and to avoid this add
                     * only one empty else among multiple empty else tree node
                     * same empty block. Example itest: operator.RelationalIT
                     */
                    if (!emptyBlockDupes.contains(leaf.getObject())) {
                        emptyBlockDupes.add(leaf.getObject());
                        allLeaves.add(leaf);
                    }
                } else {
                    allLeaves.add(leaf);
                }
            }
        }
        return allLeaves;
    }
}
