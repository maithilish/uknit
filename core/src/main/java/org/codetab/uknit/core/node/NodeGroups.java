package org.codetab.uknit.core.node;

import java.util.List;

import javax.inject.Singleton;

import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.TypeLiteral;

/**
 * The groups of participant ASTNode classes used in situations.
 *
 * @author Maithilish
 *
 */
@Singleton
public class NodeGroups {

    /*
     * List of Expression classes which can have MI or SMI.
     */
    private List<Class<? extends Expression>> nodesWithInvoke = List.of(
            MethodInvocation.class, SuperMethodInvocation.class,
            ClassInstanceCreation.class, ArrayCreation.class, ArrayAccess.class,
            ArrayInitializer.class, InfixExpression.class,
            PostfixExpression.class, PrefixExpression.class,
            ConditionalExpression.class, CastExpression.class);

    /*
     * Literal Nodes.
     */
    private List<Class<? extends Expression>> literalNodes = List.of(
            BooleanLiteral.class, CharacterLiteral.class, NullLiteral.class,
            NumberLiteral.class, StringLiteral.class, TypeLiteral.class);

    /**
     * Expressions those can be used as initializer. The MI and SMI can't be
     * directly used as initialiser but they are patched to var name which is
     * used as initializer.
     *
     * QualifiedName as initializer. Ex: int id = foo.id; Actually this is
     * FieldAccess but AST treats this as QName. Don't know the full impact by
     * allowing it as initializer.
     */
    private List<Class<? extends Expression>> allowedAsInitializer = List.of(
            ClassInstanceCreation.class, ArrayCreation.class,
            ArrayInitializer.class, InfixExpression.class,
            PostfixExpression.class, PrefixExpression.class,
            ConditionalExpression.class, CastExpression.class,
            FieldAccess.class, QualifiedName.class, BooleanLiteral.class,
            CharacterLiteral.class, NullLiteral.class, NumberLiteral.class,
            StringLiteral.class, TypeLiteral.class);

    /**
     * Nodes for which infer vars can be created. SMI is not included as as it
     * is replaced by IMC packs and infer is not created.
     */
    private List<Class<? extends Expression>> inferableNodes = List.of(
            ClassInstanceCreation.class, MethodInvocation.class,
            ArrayCreation.class, ArrayAccess.class, ArrayInitializer.class,
            InfixExpression.class, PostfixExpression.class,
            PrefixExpression.class, ConditionalExpression.class,
            QualifiedName.class, FieldAccess.class, BooleanLiteral.class,
            CharacterLiteral.class, NullLiteral.class, NumberLiteral.class,
            StringLiteral.class, TypeLiteral.class);

    /**
     * Instance creation new Foo(), Array creation, Literals "foo", 5 etc.,
     */
    private List<Class<? extends Expression>> creationNodes = List.of(
            ClassInstanceCreation.class, ArrayCreation.class,
            ArrayInitializer.class, InfixExpression.class,
            PostfixExpression.class, PrefixExpression.class,
            ConditionalExpression.class, QualifiedName.class,
            BooleanLiteral.class, CharacterLiteral.class, NullLiteral.class,
            NumberLiteral.class, StringLiteral.class, TypeLiteral.class);

    /**
     * Initializer expression that needs evaluation. Nothing at present.
     */
    private List<Class<?>> evalNodes = List.of();

    public List<Class<? extends Expression>> nodesWithInvoke() {
        return nodesWithInvoke;
    }

    public List<Class<? extends Expression>> literalNodes() {
        return literalNodes;
    }

    public List<Class<? extends Expression>> allowedAsInitializer() {
        return allowedAsInitializer;
    }

    public List<Class<? extends Expression>> inferableNodes() {
        return inferableNodes;
    }

    public List<Class<? extends Expression>> creationNodes() {
        return creationNodes;
    }

    public List<Class<?>> evalNodes() {
        return evalNodes;
    }
}
