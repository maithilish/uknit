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
    private List<Class<?>> nodesWithInvoke = List.of(MethodInvocation.class,
            SuperMethodInvocation.class, ClassInstanceCreation.class,
            ArrayCreation.class, ArrayAccess.class, ArrayInitializer.class,
            InfixExpression.class, PostfixExpression.class,
            PrefixExpression.class, ConditionalExpression.class,
            CastExpression.class);

    /*
     * Literal Nodes.
     */
    private List<Class<?>> literalNodes = List.of(BooleanLiteral.class,
            CharacterLiteral.class, NullLiteral.class, NumberLiteral.class,
            StringLiteral.class, TypeLiteral.class);

    /**
     * Expressions those can be used as initializer. The MI and SMI can't be
     * directly used as initialiser but they are patched to var name which is
     * used as initializer.
     */
    private List<Class<?>> allowedAsInitializer = List.of(
            ClassInstanceCreation.class, ArrayCreation.class, ArrayAccess.class,
            ArrayInitializer.class, InfixExpression.class,
            PostfixExpression.class, PrefixExpression.class,
            ConditionalExpression.class, CastExpression.class,
            BooleanLiteral.class, CharacterLiteral.class, NullLiteral.class,
            NumberLiteral.class, StringLiteral.class, TypeLiteral.class);

    /**
     * Nodes for which infer vars can be created. SMI is not included as as it
     * is replaced by IMC packs and infer is not created.
     */
    private List<Class<?>> inferableNodes = List.of(ClassInstanceCreation.class,
            MethodInvocation.class, ArrayCreation.class, ArrayAccess.class,
            ArrayInitializer.class, InfixExpression.class,
            PostfixExpression.class, PrefixExpression.class,
            ConditionalExpression.class, QualifiedName.class,
            BooleanLiteral.class, CharacterLiteral.class, NullLiteral.class,
            NumberLiteral.class, StringLiteral.class, TypeLiteral.class);

    /**
     * Instance creation new Foo(), Array creation, Literals "foo", 5 etc.,
     */
    private List<Class<?>> creationNodes = List.of(ClassInstanceCreation.class,
            ArrayCreation.class, ArrayInitializer.class, InfixExpression.class,
            PostfixExpression.class, PrefixExpression.class,
            ConditionalExpression.class, QualifiedName.class,
            BooleanLiteral.class, CharacterLiteral.class, NullLiteral.class,
            NumberLiteral.class, StringLiteral.class, TypeLiteral.class);

    /**
     * Initializer expression that needs evaluation. Nothing at present.
     */
    private List<Class<?>> evalNodes = List.of();

    public List<Class<?>> nodesWithInvoke() {
        return nodesWithInvoke;
    }

    public List<Class<?>> literalNodes() {
        return literalNodes;
    }

    public List<Class<?>> allowedAsInitializer() {
        return allowedAsInitializer;
    }

    public List<Class<?>> inferableNodes() {
        return inferableNodes;
    }

    public List<Class<?>> creationNodes() {
        return creationNodes;
    }

    public List<Class<?>> evalNodes() {
        return evalNodes;
    }
}
