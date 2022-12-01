package org.codetab.uknit.core.node;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;

public class Braces {

    @Inject
    private Nodes nodes;

    /**
     * Recursively strip cast and parenthesise from an expression.
     *
     * @param exp
     * @return
     */
    public Expression stripWraper(final Expression exp) {
        Expression eExp = exp;
        if (nodes.is(exp, CastExpression.class)) {
            eExp = nodes.as(exp, CastExpression.class).getExpression();
        } else if (nodes.is(exp, ParenthesizedExpression.class)) {
            eExp = nodes.as(exp, ParenthesizedExpression.class).getExpression();
        }
        if (nodes.is(eExp, CastExpression.class,
                ParenthesizedExpression.class)) {
            eExp = stripWraper(eExp);
        }
        return eExp;
    }

    // REVIEW
    public Expression strip(final Expression exp) {
        Expression eExp = exp;
        if (nodes.is(exp, ParenthesizedExpression.class)) {
            eExp = nodes.as(exp, ParenthesizedExpression.class).getExpression();
        }
        if (nodes.is(eExp, ParenthesizedExpression.class)) {
            eExp = strip(eExp);
        }
        return eExp;
    }

    // REVIEW
    public List<Expression> strip(final List<Expression> exps) {
        boolean anyBraces = exps.stream()
                .anyMatch(e -> nodes.is(e, ParenthesizedExpression.class));
        if (anyBraces) {
            List<Expression> cleanExps = new ArrayList<>();
            exps.forEach(e -> cleanExps.add(strip(e)));
            return cleanExps;
        } else {
            return exps;
        }
    }

    // REVIEW
    public ASTNode stripAndGetParent(final Expression exp) {
        ASTNode parent = exp.getParent();
        while (true) {
            if (nodes.is(parent, ParenthesizedExpression.class)) {
                parent = parent.getParent();
            } else {
                break;
            }
        }
        return parent;
    }

}
