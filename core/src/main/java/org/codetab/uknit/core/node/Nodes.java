package org.codetab.uknit.core.node;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.nonNull;
import static org.codetab.uknit.core.util.StringUtils.spaceit;

import org.codetab.uknit.core.exception.CodeException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclaration;

public class Nodes {

    public boolean is(final ASTNode node, final Class<?> clz) {
        return node.getClass().isAssignableFrom(clz);
    }

    public <T> T as(final Object o, final Class<T> clz) {
        return clz.cast(o);
    }

    public String getVariableName(final VariableDeclaration vd) {
        return vd.getName().getFullyQualifiedName();
    }

    public boolean isPrimitiveType(final FieldDeclaration fieldDecl) {
        return fieldDecl.getType().isPrimitiveType();
    }

    public String getName(final ASTNode node) {
        checkArgument(node instanceof SimpleName,
                spaceit("expected SimpleName, actual",
                        node.getClass().getSimpleName()));
        return ((SimpleName) node).getFullyQualifiedName();
    }

    // public Optional<String> getQualifiedImportName(
    // final List<ImportDeclaration> imports, final String clsName) {
    // for (ImportDeclaration importDecl : imports) {
    // String importName = importDecl.getName().getFullyQualifiedName();
    // if (importName.endsWith(clsName)) {
    // return Optional.of(importName);
    // }
    // }
    // return Optional.empty();
    // }
    //
    // public boolean isMethodReturnInferred(final MethodInvocation mi) {
    // int type = mi.getParent().getNodeType();
    // boolean inferredType = false;
    // switch (type) {
    // case ASTNode.IF_STATEMENT:
    // inferredType = true;
    // break;
    // case ASTNode.WHILE_STATEMENT:
    // inferredType = true;
    // break;
    // case ASTNode.INFIX_EXPRESSION:
    // inferredType = true;
    // break;
    // case ASTNode.PREFIX_EXPRESSION:
    // inferredType = true;
    // break;
    // case ASTNode.ENHANCED_FOR_STATEMENT:
    // inferredType = true;
    // break;
    // case ASTNode.CLASS_INSTANCE_CREATION:
    // inferredType = true;
    // break;
    // case ASTNode.ARRAY_CREATION:
    // inferredType = true;
    // break;
    // default:
    // break;
    // }
    // return inferredType;
    // }
    //
    // public String label(final ASTNode node) {
    // int type = node.getNodeType();
    // String label = "undefined";
    // switch (type) {
    // case ASTNode.METHOD_INVOCATION:
    // label = "mi";
    // break;
    // case ASTNode.VARIABLE_DECLARATION_STATEMENT:
    // label = "vds";
    // break;
    // case ASTNode.VARIABLE_DECLARATION_FRAGMENT:
    // label = "vdf";
    // break;
    // case ASTNode.ASSIGNMENT:
    // label = "assignment";
    // break;
    // case ASTNode.EXPRESSION_STATEMENT:
    // label = "expStmt";
    // break;
    // case ASTNode.RETURN_STATEMENT:
    // label = "retStmt";
    // break;
    // case ASTNode.WHILE_STATEMENT:
    // label = "whileStmt";
    // break;
    // case ASTNode.IF_STATEMENT:
    // label = "ifStmt";
    // break;
    // case ASTNode.ENHANCED_FOR_STATEMENT:
    // label = "ForEachStmt";
    // break;
    // case ASTNode.INFIX_EXPRESSION:
    // label = "inFixExp";
    // break;
    // case ASTNode.PREFIX_EXPRESSION:
    // label = "prefixExp";
    // break;
    // case ASTNode.CAST_EXPRESSION:
    // label = "castExp";
    // break;
    // case ASTNode.CLASS_INSTANCE_CREATION:
    // label = "instanceCreate";
    // break;
    // case ASTNode.INSTANCEOF_EXPRESSION:
    // label = "instanceOf";
    // break;
    // case ASTNode.ARRAY_CREATION:
    // label = "instanceOf";
    // break;
    // default:
    // throw new CodeException(spaceit("label undefined for node:",
    // node.getClass().getSimpleName()));
    // }
    // return label;
    // }
    //
    // public String getPackageName(final PackageDeclaration packageDecl) {
    // return packageDecl.getName().getFullyQualifiedName();
    // }
    //

    // public boolean isArrayDimensionsNumeral(final ArrayCreation node) {
    // @SuppressWarnings("unchecked")
    // List<ASTNode> dims = node.dimensions();
    // for (ASTNode dim : dims) {
    // if (dim.getNodeType() != ASTNode.NUMBER_LITERAL) {
    // return false;
    // }
    // }
    // return true;
    // }
    //

    public CodeException getCodeException(final String message,
            final ASTNode node) {
        return new CodeException(spaceit(message, node.toString()));
    }

    public CodeException unexpectedException(final ASTNode node) {
        return new CodeException(spaceit("unexpected node type:",
                node.getClass().getSimpleName()));
    }

    // public List<Expression> getDimensions(final ArrayCreation node) {
    // @SuppressWarnings("unchecked")
    // List<Expression> dims = node.dimensions();
    // return dims;
    // }
    //
    // public List<Expression> getInitializers(final ForStatement node) {
    // @SuppressWarnings("unchecked")
    // List<Expression> initializers = node.initializers();
    // return initializers;
    // }
    //
    // public boolean isLiteral(final ASTNode node) {
    // boolean literal = true;
    // int type = node.getNodeType();
    // switch (type) {
    // case ASTNode.BOOLEAN_LITERAL:
    // break;
    // case ASTNode.CHARACTER_LITERAL:
    // break;
    // case ASTNode.NULL_LITERAL:
    // break;
    // case ASTNode.TYPE_LITERAL:
    // break;
    // case ASTNode.NUMBER_LITERAL:
    // break;
    // case ASTNode.STRING_LITERAL:
    // break;
    // default:
    // literal = false;
    // break;
    // }
    // return literal;
    // }
    //
    public boolean isClassInstanceCreation(final Expression exp) {
        return is(exp, ClassInstanceCreation.class);
    }

    public boolean isAnonOrLambda(final Expression exp) {
        if (is(exp, ClassInstanceCreation.class)) {
            ClassInstanceCreation cic = as(exp, ClassInstanceCreation.class);
            return nonNull(cic.getAnonymousClassDeclaration());
        } else if (is(exp, LambdaExpression.class)) {
            return true;
        } else {
            return false;
        }
    }
}
