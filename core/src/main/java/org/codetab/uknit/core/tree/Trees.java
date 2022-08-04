package org.codetab.uknit.core.tree;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
     * Find first matching node in the tree. Throws exception if node not found.
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
        return list.get(0);
    }

    /**
     * Find all matching nodes.
     * @param <T>
     * @param tree
     * @param node
     * @return
     */
    public <T> List<TreeNode<T>> findAll(final TreeNode<T> tree, final T node) {
        List<TreeNode<T>> list =
                tree.stream().filter(t -> t.getObject().equals(node))
                        .collect(Collectors.toList());
        return list;
    }

    /**
     * Find all leaves of the tree.
     * @param <T>
     * @param tree
     * @return
     */
    public <T> List<TreeNode<T>> findLeaves(final TreeNode<T> tree) {
        return tree.stream().filter(t -> t.isLeaf())
                .collect(Collectors.toList());
    }

    /**
     * Find all leaves of the tree that are enabled.
     * @param <T>
     * @param tree
     * @return
     */
    public <T> List<TreeNode<T>> findEnabledLeaves(final TreeNode<T> tree) {
        return tree.stream().filter(t -> t.isLeaf() && t.isEnable())
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

    /**
     * Find position of the node in the tree.
     * @param <T>
     * @param tree
     * @param node
     * @param clz
     * @return
     */
    public <T> int positionInTree(final TreeNode<T> tree,
            final TreeNode<T> node, final Class<?> clz) {
        int pos = tree.stream()
                .filter(t -> t.getObject().getClass().isAssignableFrom(clz))
                .collect(Collectors.toList()).indexOf(node);
        return pos;
    }

    /**
     * Returns tree as string similar to Linux tree cmd output.
     * @param <T>
     * @param tree
     * @param prefix
     * @param childrenPrefix
     * @param sb
     * @return
     */
    public <T> String prettyPrintTree(final TreeNode<T> tree,
            final String prefix, final String childrenPrefix,
            final StringBuilder sb) {

        T obj = tree.getObject();
        List<TreeNode<T>> children = tree.getChildren();

        StringBuilder buffer = sb;
        if (isNull(buffer)) {
            buffer = new StringBuilder();
            buffer.append(System.lineSeparator());
        }
        buffer.append(prefix);
        buffer.append(obj.getClass().getSimpleName());
        buffer.append(" ");
        if (!tree.getName().isBlank()) {
            buffer.append(tree.getName());
            buffer.append(" ");
        }
        buffer.append(tree.objectId());
        if (tree.isEnable()) {
            buffer.append("+");
        }
        buffer.append(System.lineSeparator());

        if (nonNull(children)) {
            Iterator<TreeNode<T>> it = children.iterator();
            while (it.hasNext()) {
                TreeNode<T> next = it.next();
                if (it.hasNext()) {
                    prettyPrintTree(next, childrenPrefix + "├── ",
                            childrenPrefix + "│   ", buffer);
                } else {
                    prettyPrintTree(next, childrenPrefix + "└── ",
                            childrenPrefix + "    ", buffer);
                }
            }
        }

        return buffer.toString();
    }

    /**
     * Returns a path of a tree as string similar to Linux tree cmd output.
     * @param <T>
     * @param tree
     * @param prefix
     * @param childrenPrefix
     * @param sb
     * @return
     */
    public <T> String prettyPrintPath(final TreeNode<T> tree,
            final List<TreeNode<T>> path, final String prefix,
            final String childrenPrefix, final StringBuilder sb) {

        T obj = tree.getObject();
        List<TreeNode<T>> children = tree.getChildren();
        StringBuilder buffer = sb;

        if (isNull(buffer)) {
            buffer = new StringBuilder();
            buffer.append(System.lineSeparator());
        }
        if (path.contains(tree)) {
            buffer.append(prefix);
            buffer.append(obj.getClass().getSimpleName());
            buffer.append(" ");
            if (!tree.getName().isBlank()) {
                buffer.append(tree.getName());
                buffer.append(" ");
            }
            buffer.append(tree.objectId());
            if (tree.isEnable()) {
                buffer.append("+");
            }
            buffer.append(System.lineSeparator());
        }

        if (nonNull(children)) {
            Iterator<TreeNode<T>> it = children.iterator();
            while (it.hasNext()) {
                TreeNode<T> next = it.next();
                if (it.hasNext()) {
                    prettyPrintPath(next, path, childrenPrefix + "└── ",
                            childrenPrefix + "    ", buffer);
                } else {
                    prettyPrintPath(next, path, childrenPrefix + "└── ",
                            childrenPrefix + "    ", buffer);
                }
            }
        }
        return buffer.toString();
    }
}
