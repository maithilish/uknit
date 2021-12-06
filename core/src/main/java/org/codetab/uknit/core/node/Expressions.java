package org.codetab.uknit.core.node;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.SimpleName;

public class Expressions {

    @Inject
    private Nodes nodes;

    /**
     * Get name from various types of expression.
     * @param exp
     * @return
     */
    public List<String> getNames(final Expression exp) {
        List<String> names = new ArrayList<>();
        if (nonNull(exp)) {
            if (nodes.is(exp, PrefixExpression.class)) {
                Expression nameExp =
                        nodes.as(exp, PrefixExpression.class).getOperand();
                if (nonNull(nameExp) && nodes.is(nameExp, SimpleName.class)) {
                    names.add(nodes.getName(nameExp));
                }
            } else if (nodes.is(exp, PostfixExpression.class)) {
                Expression nameExp =
                        nodes.as(exp, PostfixExpression.class).getOperand();
                if (nonNull(nameExp) && nodes.is(nameExp, SimpleName.class)) {
                    names.add(nodes.getName(nameExp));
                }
            } else if (nodes.is(exp, InfixExpression.class)) {
                Expression nameExp =
                        nodes.as(exp, InfixExpression.class).getLeftOperand();
                if (nonNull(nameExp) && nodes.is(nameExp, SimpleName.class)) {
                    names.add(nodes.getName(nameExp));
                }
                nameExp =
                        nodes.as(exp, InfixExpression.class).getRightOperand();
                if (nonNull(nameExp) && nodes.is(nameExp, SimpleName.class)) {
                    names.add(nodes.getName(nameExp));
                }
            }
        }
        return names;
    }

}
