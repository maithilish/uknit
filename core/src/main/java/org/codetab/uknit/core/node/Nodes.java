package org.codetab.uknit.core.node;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.nonNull;
import static org.codetab.uknit.core.util.StringUtils.spaceit;

import org.codetab.uknit.core.exception.CodeException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.VariableDeclaration;

public class Nodes {

    private Class<?>[] creationNodes = {NumberLiteral.class,
            StringLiteral.class, TypeLiteral.class, CharacterLiteral.class,
            NullLiteral.class, ClassInstanceCreation.class, ArrayCreation.class,
            ArrayInitializer.class, PrefixExpression.class,
            PostfixExpression.class, InfixExpression.class,
            QualifiedName.class};

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

    public CodeException getCodeException(final String message,
            final ASTNode node) {
        return new CodeException(spaceit(message, node.toString()));
    }

    public CodeException unexpectedException(final ASTNode node) {
        return new CodeException(spaceit("unexpected node type:",
                node.getClass().getSimpleName()));
    }

    public boolean isClassInstanceCreation(final Expression exp) {
        return is(exp, ClassInstanceCreation.class);
    }

    public boolean isCreation(final Expression exp) {
        for (Class<?> clz : creationNodes) {
            if (exp.getClass().isAssignableFrom(clz)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAnonOrLambda(final Expression exp) {
        if (is(exp, ClassInstanceCreation.class)) {
            ClassInstanceCreation cic = as(exp, ClassInstanceCreation.class);
            return nonNull(cic.getAnonymousClassDeclaration());
        } else {
            return is(exp, LambdaExpression.class);
        }
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
        default:
            throw new CodeException(spaceit("label undefined for node:",
                    node.getClass().getSimpleName()));
        }
        return label;
    }
}
