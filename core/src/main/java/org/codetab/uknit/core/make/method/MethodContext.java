package org.codetab.uknit.core.make.method;

import javax.inject.Singleton;

import org.codetab.uknit.core.tree.TreeNode;
import org.eclipse.jdt.core.dom.MethodDeclaration;

/**
 * Holds objects used in the context of a method under test.
 *
 * The controller holds object used in the context of class under test.
 *
 * @author Maithilish
 *
 */
@Singleton
public class MethodContext {

    /*
     * IM call hierarchy graph used by Cyclic to know whether a method or IM
     * call is cyclic.
     */
    private TreeNode<MethodDeclaration> callHierarchy;

    public void init() {
        callHierarchy = null;
    }

    public TreeNode<MethodDeclaration> getCallHierarchy() {
        return callHierarchy;
    }

    public void setCallHierarchy(
            final TreeNode<MethodDeclaration> callHierarchy) {
        this.callHierarchy = callHierarchy;
    }
}
