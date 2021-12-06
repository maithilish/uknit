package org.codetab.uknit.core.make.method.visit;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ReturnStatement;

public class Replacers {

    @Inject
    private ArgReplacer argReplacer;
    @Inject
    private Nodes nodes;

    /**
     * Replace expression with var - arg expressions in MethodInvocation,
     * ClassInstanceCreation and ArrayCreation and expression in
     * MethodInvocation and ReturnStatement.
     * <p>
     * foo.x().bar(baz.x()) - apple.bar(orange)
     * <p>
     * return foo.bar() - return apple
     * <p>
     * new String[group.size()] - new String[apple]
     * <p>
     * new Date(person.getDob()) - new Date(apple)
     * @param node
     * @param exp
     * @param name
     * @return
     */
    public boolean replaceExpWithVar(final ASTNode node, final Expression exp,
            final String name) {
        checkNotNull(node);
        checkNotNull(exp);
        checkNotNull(name);

        if (nodes.is(node, ReturnStatement.class)) {
            ReturnStatement rs = nodes.as(node, ReturnStatement.class);
            rs.setExpression(rs.getAST().newSimpleName(name));
            return true;
        }
        if (nodes.is(node, MethodInvocation.class)) {
            MethodInvocation mi = nodes.as(node, MethodInvocation.class);
            @SuppressWarnings("unchecked")
            List<Expression> args = mi.arguments();
            if (args.contains(exp)) {
                argReplacer.replace(args, exp, name);
            } else {
                mi.setExpression(mi.getAST().newSimpleName(name));
            }
            return true;
        }
        if (nodes.is(node, ClassInstanceCreation.class)) {
            @SuppressWarnings("unchecked")
            List<Expression> args =
                    nodes.as(node, ClassInstanceCreation.class).arguments();
            argReplacer.replace(args, exp, name);
            return true;
        }
        if (nodes.is(node, ArrayCreation.class)) {
            @SuppressWarnings("unchecked")
            List<Expression> args =
                    nodes.as(node, ArrayCreation.class).dimensions();
            argReplacer.replace(args, exp, name);
            return true;
        }
        throw nodes.unexpectedException(node);
    }
}
