package org.codetab.uknit.core.tree;

import org.eclipse.jdt.core.dom.ASTNode;

public interface TreeFactory {

    TreeNode<ASTNode> createTreeNode(ASTNode node, String name);
}
