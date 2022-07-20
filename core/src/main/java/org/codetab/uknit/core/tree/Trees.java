package org.codetab.uknit.core.tree;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Utility methods to access tree nodes.
 * @author m
 *
 */
public class Trees {

    /**
     * Find node in the tree. Throws exception if node not found or multiple
     * found.
     * @param <T>
     * @param tree
     * @param node
     * @return
     */
    public <T> TreeNode<T> findOne(final TreeNode<T> tree, final T node) {
        List<TreeNode<T>> list =
                tree.stream().filter(t -> t.getObject().equals(node))
                        .collect(Collectors.toList());
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() == 0) {
            throw new NoSuchElementException("node not found in tree");
        }
        throw new IllegalStateException("found multiple nodes");
    }

    public <T> List<TreeNode<T>> findLeaves(final TreeNode<T> tree) {
        return tree.stream().filter(t -> t.isLeaf())
                .collect(Collectors.toList());
    }

    /**
     * Returns the root of the tree of this node.
     * @return
     */
    public <T> TreeNode<T> getRoot(final TreeNode<T> node) {
        TreeNode<T> ancestor = node;
        TreeNode<T> previous;

        do {
            previous = ancestor;
            ancestor = ancestor.getParent();
        } while (nonNull(ancestor));

        return previous;
    }

    /**
     * Path from a node to root.
     * @return
     */
    public <T> List<TreeNode<T>> getPathToRoot(final TreeNode<T> node) {
        List<TreeNode<T>> list = new ArrayList<>();

        TreeNode<T> ancestor = node;
        do {
            list.add(ancestor);
            ancestor = ancestor.getParent();
        } while (nonNull(ancestor));

        return list;
    }

    /**
     * Path from root to a node.
     * @return
     */
    public <T> List<TreeNode<T>> getPathFromRoot(final TreeNode<T> node) {
        List<TreeNode<T>> list = new ArrayList<>();

        TreeNode<T> ancestor = node;
        do {
            list.add(ancestor);
            ancestor = ancestor.getParent();
        } while (nonNull(ancestor));

        Collections.reverse(list);
        return list;
    }

    public <T> int positionInTree(final TreeNode<T> tree,
            final TreeNode<T> node, final Class<?> clz) {
        int pos = tree.stream()
                .filter(t -> t.getObject().getClass().isAssignableFrom(clz))
                .collect(Collectors.toList()).indexOf(node);
        return pos;
    }
}
