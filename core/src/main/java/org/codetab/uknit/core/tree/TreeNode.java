
package org.codetab.uknit.core.tree;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.Stack;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;
import java.util.stream.StreamSupport;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

/**
 * Generic Tree.
 * @author m
 *
 * @param <T>
 */
public class TreeNode<T>
        implements Cloneable, Serializable, Iterable<TreeNode<T>> {

    private static final long serialVersionUID = 8655205359948583064L;

    // object held by this
    protected T object;
    protected TreeNode<T> parent;
    protected List<TreeNode<T>> children;

    @Inject
    public TreeNode(@Assisted final T object) {
        this.object = object;
    }

    public void add(final TreeNode<T> newChild) {
        if (nonNull(newChild) && nonNull(newChild.getParent())
                && newChild.getParent().equals(this)) {
            insert(newChild, getChildCount() - 1);
        } else {
            insert(newChild, getChildCount());
        }
    }

    public void insert(final TreeNode<T> child, final int childIndex) {
        if (isNull(child)) {
            throw new IllegalArgumentException("child is null");
        } else if (isAncestor(child)) {
            throw new IllegalArgumentException("child is an ancestor");
        }

        TreeNode<T> oldParent = child.getParent();

        if (nonNull(oldParent)) {
            oldParent.remove(child);
        }
        child.setParent(this);
        if (isNull(children)) {
            children = new ArrayList<>();
        }
        children.add(childIndex, child);
    }

    public void remove(final int index) {
        TreeNode<T> child = getChildAt(index);
        children.remove(index);
        child.setParent(null);
    }

    public void remove(final TreeNode<T> node) {
        if (isNull(node)) {
            throw new IllegalArgumentException("argument is null");
        }
        if (!isChild(node)) {
            throw new IllegalArgumentException("argument is not a child");
        }
        remove(getIndex(node));
    }

    public void setParent(final TreeNode<T> newParent) {
        parent = newParent;
    }

    public TreeNode<T> getParent() {
        return parent;
    }

    public TreeNode<T> getChildAt(final int index) {
        if (isNull(children)) {
            throw new ArrayIndexOutOfBoundsException("node has no children");
        }
        return children.get(index);
    }

    public int getChildCount() {
        if (isNull(children)) {
            return 0;
        } else {
            return children.size();
        }
    }

    public void removeAllChildren() {
        for (int i = getChildCount() - 1; i >= 0; i--) {
            remove(i);
        }
    }

    public boolean isChild(final TreeNode<T> node) {
        boolean result;

        if (isNull(node)) {
            result = false;
        } else {
            if (getChildCount() == 0) {
                result = false;
            } else {
                result = node.getParent().equals(this);
            }
        }
        return result;
    }

    public int getIndex(final TreeNode<T> child) {
        if (isNull(child)) {
            throw new IllegalArgumentException("argument is null");
        }
        if (!isChild(child)) {
            return -1;
        }
        return children.indexOf(child);
    }

    public boolean isLeaf() {
        return (getChildCount() == 0);
    }

    /**
     * Returns true if this node is the root of the tree.
     * @return
     */
    public boolean isRoot() {
        return isNull(getParent());
    }

    /**
     * Get object held by this node.
     * @return
     */
    public T getObject() {
        return object;
    }

    /**
     * Returns true if other node is ancestor of this node.
     * @param other
     * @return
     */
    public boolean isAncestor(final TreeNode<T> other) {
        if (isNull(other)) {
            return false;
        }
        TreeNode<T> ancestor = this;
        do {
            if (ancestor.equals(other)) {
                return true;
            }
        } while ((ancestor = ancestor.getParent()) != null);
        return false;
    }

    public boolean isDescendant(final TreeNode<T> other) {
        if (isNull(other)) {
            return false;
        }
        return other.isAncestor(this);
    }

    /**
     * Post order stream.
     * @return
     */
    public Stream<TreeNode<T>> postOrderStream() {
        Builder<TreeNode<T>> builder = Stream.builder();
        postorder(this, builder);
        return builder.build();
    }

    private void postorder(final TreeNode<T> node,
            final Stream.Builder<TreeNode<T>> builder) {
        if (nonNull(node.children)) {
            for (TreeNode<T> childTree : node.children) {
                postorder(childTree, builder);
            }
        }
        builder.add(node);
    }

    /**
     * Depth First iterator.
     */
    @Override
    public Iterator<TreeNode<T>> iterator() {
        return new DepthFirstIterator<T>(this);
    }

    /**
     * Depth First stream.
     * @return
     */
    public Stream<TreeNode<T>> stream() {
        Iterator<TreeNode<T>> it = new DepthFirstIterator<T>(this);
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(it, Spliterator.ORDERED),
                false);
    }

    @Override
    public String toString() {
        return object.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(children, object, parent);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        @SuppressWarnings("unchecked")
        TreeNode<T> other = (TreeNode<T>) obj;
        return Objects.equals(children, other.children)
                && Objects.equals(object, other.object)
                && Objects.equals(parent, other.parent);
    }

    private class DepthFirstIterator<U> implements Iterator<TreeNode<T>> {

        private Stack<TreeNode<T>> stack = new Stack<TreeNode<T>>();

        DepthFirstIterator(final TreeNode<T> root) {
            stack.push(root);
        }

        @Override
        public boolean hasNext() {
            return !stack.empty();
        }

        @Override
        public TreeNode<T> next() {
            if (!hasNext()) {
                throw new NoSuchElementException("tree ran out of elements");
            }
            TreeNode<T> node = stack.pop();
            if (nonNull(node.children)) {
                for (int i = node.children.size() - 1; i >= 0; i--) {
                    stack.push(node.children.get(i));
                }
            }
            return node;
        }
    }
}
