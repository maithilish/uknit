package org.codetab.uknit.core.node;

import static org.codetab.uknit.core.util.StringUtils.spaceit;

import java.util.List;

import org.codetab.uknit.core.exception.CodeException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;

public class Nodes {

    public boolean is(final ASTNode node, final Class<?> clz) {
        return node.getClass().isAssignableFrom(clz);
    }

    public boolean is(final ASTNode node, final Class<?>... clzs) {
        for (Class<?> clz : clzs) {
            if (node.getClass().isAssignableFrom(clz)) {
                return true;
            }
        }
        return false;
    }

    public boolean is(final ASTNode node, final List<Class<?>> clzs) {
        for (Class<?> clz : clzs) {
            if (node.getClass().isAssignableFrom(clz)) {
                return true;
            }
        }
        return false;
    }

    public <T> T as(final Object o, final Class<T> clz) {
        return clz.cast(o);
    }

    public String getName(final ASTNode node) {
        if (node.getNodeType() == ASTNode.SIMPLE_NAME) {
            return ((SimpleName) node).getFullyQualifiedName();
        }
        if (node.getNodeType() == ASTNode.QUALIFIED_NAME) {
            return ((QualifiedName) node).getName().getFullyQualifiedName();
        }
        throw new CodeException(
                spaceit("node should be Simple or QualifiedName, but was:",
                        node.getClass().getSimpleName()));
    }

    public boolean isName(final ASTNode node) {
        return node.getNodeType() == ASTNode.SIMPLE_NAME
                || node.getNodeType() == ASTNode.QUALIFIED_NAME;
    }

    public String label(final ASTNode node) {
        int type = node.getNodeType();
        String label = "undefined";
        switch (type) {
        case ASTNode.METHOD_INVOCATION:
            label = "mi";
            break;
        case ASTNode.VARIABLE_DECLARATION_STATEMENT:
            label = "vds";
            break;
        case ASTNode.VARIABLE_DECLARATION_FRAGMENT:
            label = "vdf";
            break;
        case ASTNode.ASSIGNMENT:
            label = "assignment";
            break;
        case ASTNode.EXPRESSION_STATEMENT:
            label = "expStmt";
            break;
        case ASTNode.RETURN_STATEMENT:
            label = "retStmt";
            break;
        case ASTNode.WHILE_STATEMENT:
            label = "whileStmt";
            break;
        case ASTNode.IF_STATEMENT:
            label = "ifStmt";
            break;
        case ASTNode.ENHANCED_FOR_STATEMENT:
            label = "ForEachStmt";
            break;
        case ASTNode.INFIX_EXPRESSION:
            label = "inFixExp";
            break;
        case ASTNode.PREFIX_EXPRESSION:
            label = "prefixExp";
            break;
        case ASTNode.CAST_EXPRESSION:
            label = "castExp";
            break;
        case ASTNode.CLASS_INSTANCE_CREATION:
            label = "instanceCreate";
            break;
        case ASTNode.INSTANCEOF_EXPRESSION:
            label = "instanceOf";
            break;
        case ASTNode.ARRAY_CREATION:
            label = "instanceOf";
            break;
        case ASTNode.NULL_LITERAL:
            label = "null literal";
            break;
        default:
            throw new CodeException(spaceit("label undefined for node:",
                    node.getClass().getSimpleName()));
        }
        return label;
    }

    public boolean isEmptyBlock(final ASTNode node) {
        if (node.getClass().isAssignableFrom(Block.class)) {
            Block block = Block.class.cast(node);
            return block.statements().isEmpty();
        } else {
            return false;
        }
    }

    /**
     * Formatted message for CodeException.
     * @param message
     * @param node
     * @return
     */
    public String exMessage(final String message, final ASTNode node) {
        return spaceit(message, node.getClass().getSimpleName(),
                node.toString());
    }

    /**
     * Formatted message for ASTNode that are yet to be implemented.
     * @param message
     * @param node
     * @return
     */
    public String noImplmentationMessage(final ASTNode node) {
        return spaceit("no implmentation for node type:",
                node.getClass().getSimpleName(), "-", node.toString());
    }
}
