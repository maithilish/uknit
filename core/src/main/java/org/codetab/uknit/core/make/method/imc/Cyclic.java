package org.codetab.uknit.core.make.method.imc;

import static org.codetab.uknit.core.util.StringUtils.spaceit;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.tree.TreeFactory;
import org.codetab.uknit.core.tree.TreeNode;
import org.codetab.uknit.core.tree.Trees;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class Cyclic {

    private static final Logger LOG = LogManager.getLogger();

    @Inject
    private Cyclics cyclics;
    @Inject
    private Trees trees;
    @Inject
    private TreeFactory treeFactory;

    /**
     * If one or more leaf with invoker method exists then cyclic is derived
     * from the last leaf, else get list of non-leaf nodes with invoker method
     * and cyclic is derived from the last one in the list.
     *
     * @param invoker
     * @param invoked
     * @param callHierarchy
     * @return
     */
    public boolean isCyclic(final MethodDeclaration invoker,
            final MethodDeclaration invoked,
            final TreeNode<MethodDeclaration> callHierarchy) {

        List<TreeNode<MethodDeclaration>> invokerLeaves =
                trees.findLeaves(callHierarchy).stream()
                        .filter(n -> n.getObject().equals(invoker))
                        .collect(Collectors.toList());

        if (invokerLeaves.isEmpty()) {
            // no leaf, find other nodes
            List<TreeNode<MethodDeclaration>> invokerNodes =
                    trees.findAll(callHierarchy, invoker);
            if (invokerNodes.isEmpty()) {
                String error = "no node found";
                debugCallHierarchy(callHierarchy);
                throw new IllegalStateException(
                        spaceit(error, "for", invoker.getName().toString()));
            } else {
                return cyclics.isCyclic(invoked, invokerNodes);
            }
        } else {
            return cyclics.isCyclic(invoked, invokerLeaves);
        }
    }

    /**
     * If one or more leaf with invoker method exists then invoked node is added
     * to the last leaf, else get list of non-leaf nodes with invoker method and
     * add invoked node to the last one in the list.
     *
     * @param invoker
     * @param invoked
     * @param callHierarchy
     * @return
     */
    public void addCallHierarchyNode(final MethodDeclaration invoked,
            final MethodDeclaration invoker,
            final TreeNode<MethodDeclaration> callHierarchy) {

        List<TreeNode<MethodDeclaration>> invokerLeaves =
                trees.findLeaves(callHierarchy).stream()
                        .filter(n -> n.getObject().equals(invoker))
                        .collect(Collectors.toList());

        if (invokerLeaves.isEmpty()) {
            // no leaf, find other nodes
            List<TreeNode<MethodDeclaration>> invokerNodes =
                    trees.findAll(callHierarchy, invoker);
            if (invokerNodes.isEmpty()) {
                String error = "no node found";
                debugCallHierarchy(callHierarchy);
                throw new IllegalStateException(
                        spaceit(error, "for", invoker.getName().toString()));
            } else {
                cyclics.addNode(invoked, invokerNodes);
            }
        } else {
            cyclics.addNode(invoked, invokerLeaves);
        }
    }

    /**
     * Create the root node of call tree.
     *
     * @param method
     * @return
     */
    public TreeNode<MethodDeclaration> createCallHierarchy(
            final MethodDeclaration method) {
        return treeFactory.createCallHierarchNode(0, method,
                method.getName().toString());
    }

    /**
     * Log call tree and depth first call order.
     *
     * @param callHierarchy
     */
    public void debugCallHierarchy(
            final TreeNode<MethodDeclaration> callHierarchy) {
        LOG.debug("call hierarchy {}",
                trees.prettyPrintBasicTree(callHierarchy, "", "", null));
        LOG.debug("call order (depth first) {}",
                trees.depthFirstCallOrder(callHierarchy));
    }
}

class Cyclics {

    @Inject
    private Trees trees;
    @Inject
    private TreeFactory treeFactory;

    /**
     * If invokedNode exists in path to root of last invoker node then invoked
     * method is cyclic.
     *
     * Ex: Consider tree 1 3 4 5 3, for invoked node 5 the invoker node is 4 and
     * root to path for 4 is 4,3,1 where 5 doesn't exists, so 5 is acyclic. For
     * invoked node 3 (last one) the invoker node is 5 and root to path for 5 is
     * 5,4,3,1 where 3 exists, so 3 is cyclic.
     *
     * If there are more than one invoker node (may be from single or multiple
     * paths) then last one is chosen as it is the latest and relevant one.
     *
     * @param invoked
     * @param invokerNodes
     * @return
     */
    public boolean isCyclic(final MethodDeclaration invoked,
            final List<TreeNode<MethodDeclaration>> invokerNodes) {
        // one or more nodes - choose the last one
        TreeNode<MethodDeclaration> invokerNode =
                invokerNodes.get(invokerNodes.size() - 1);
        List<TreeNode<MethodDeclaration>> ancestors =
                trees.getPathToRoot(invokerNode);
        return ancestors.stream().anyMatch(n -> n.getObject().equals(invoked));
    }

    /**
     * The invoked node is created and added to the invoker node. If invoker
     * node already has a child whose object is invoked method then node is
     * added to avoid duplication. For example, if a method calls same IM twice
     * then there is no difference between the two calls and whether IM is
     * cyclic may be derived from one path.
     *
     * If there are more than one invoker node (may be from single or multiple
     * paths) then last one is chosen as it is the latest and relevant one.
     *
     * @param invoked
     * @param invokerNodes
     */
    public void addNode(final MethodDeclaration invoked,
            final List<TreeNode<MethodDeclaration>> invokerNodes) {
        // one or more nodes - choose the last one
        TreeNode<MethodDeclaration> invokerNode =
                invokerNodes.get(invokerNodes.size() - 1);
        if (!invokerNode.getChildren().stream()
                .anyMatch(n -> n.getObject().equals(invoked))) {
            TreeNode<MethodDeclaration> child =
                    treeFactory.createCallHierarchNode(0, invoked,
                            invoked.getName().toString());
            invokerNode.add(child);
        }
    }
}
