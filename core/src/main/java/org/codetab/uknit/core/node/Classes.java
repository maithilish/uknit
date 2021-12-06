package org.codetab.uknit.core.node;

import static org.codetab.uknit.core.util.StringUtils.spaceit;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.NodeCastException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import com.google.common.base.CaseFormat;

public class Classes {

    @Inject
    private Nodes nodes;

    public TypeDeclaration asTypeDecl(final ASTNode node) {
        if (nodes.is(node, TypeDeclaration.class)) {
            return nodes.as(node, TypeDeclaration.class);
        } else {
            String type = node.getClass().getSimpleName();
            throw new NodeCastException(
                    spaceit(type, "is not TypeDeclaration"));
        }
    }

    public String getClzAsFieldName(final TypeDeclaration typeDecl) {
        String clzName = typeDecl.getName().getFullyQualifiedName();
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, clzName);
    }

    public String getClzName(final AbstractTypeDeclaration clzDecl) {
        return clzDecl.getName().getFullyQualifiedName();
    }

    /**
     * Get test class name for a TypeDeclaration.
     * @param clzDecl
     * @return
     */
    public String getTestClzName(final AbstractTypeDeclaration clzDecl) {
        return String.join("", clzDecl.getName().getFullyQualifiedName(),
                "Test");
    }

    // public Class<?> getClass(final ASTNode node) {
    // Class<?> argClz;
    // int type = node.getNodeType();
    // switch (type) {
    // case ASTNode.STRING_LITERAL:
    // argClz = String.class;
    // break;
    // case ASTNode.NUMBER_LITERAL:
    // argClz = int.class;
    // break;
    // case ASTNode.CHARACTER_LITERAL:
    // argClz = int.class;
    // break;
    // default:
    // throw new CodeException("unhandled node type");
    // }
    // return argClz;
    // }
    //
    // public Class<?> getPrimitiveClass(final String name) {
    //
    // checkNotNull(name);
    //
    // Class<?> clz = null;
    // switch (name) {
    // case "int":
    // clz = int.class;
    // break;
    // case "byte":
    // clz = byte.class;
    // break;
    // case "short":
    // clz = short.class;
    // break;
    // case "long":
    // clz = long.class;
    // break;
    // case "float":
    // clz = float.class;
    // break;
    // case "double":
    // clz = double.class;
    // break;
    // case "boolean":
    // clz = boolean.class;
    // break;
    // case "char":
    // clz = char.class;
    // break;
    // default:
    // break;
    // }
    // return clz;
    // }
}
