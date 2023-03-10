package org.codetab.uknit.core.tree;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public interface TreeFactory {

    TreeNode<ASTNode> createTreeNode(int id, ASTNode node, String name);

    TreeNode<MethodDeclaration> createCallHierarchNode(int id,
            MethodDeclaration node, String name);
}
