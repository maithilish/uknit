package org.codetab.uknit.core.node;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;

public class Wrappers {

    @Inject
    private Nodes nodes;

    /**
     * Recursively strip cast and parenthesise from an expression. Ex: for
     * CastExpression '(Foo) ((obj))' returns 'obj'.
     *
     * @param exp
     * @return
     */
    public Expression unpack(final Expression exp) {
        Expression eExp = exp;
        if (nodes.is(exp, CastExpression.class)) {
            eExp = nodes.as(exp, CastExpression.class).getExpression();
        } else if (nodes.is(exp, ParenthesizedExpression.class)) {
            eExp = nodes.as(exp, ParenthesizedExpression.class).getExpression();
        }
        if (nodes.is(eExp, CastExpression.class,
                ParenthesizedExpression.class)) {
            eExp = unpack(eExp);
        }
        return eExp;
    }

    /**
     * If exps list contains any Cast or Parenthesized exp then return new list
     * of striped exps else return the original list. Ex: exps [((foo)),
     * (index)] then return new list [foo, index].
     *
     * @param exps
     * @return
     */
    // REVIEW if possible remove this to avoid side effects - any patch to new
    // list will not reflect in copy
    public List<Expression> unpack(final List<Expression> exps) {
        boolean anyWrapers = exps.stream().anyMatch(e -> nodes.is(e,
                ParenthesizedExpression.class, CastExpression.class));
        if (anyWrapers) {
            List<Expression> cleanExps = new ArrayList<>();
            exps.forEach(e -> cleanExps.add(unpack(e)));
            return cleanExps;
        } else {
            return exps;
        }
    }

    /**
     * Get exp that is wrapped in parenthesises. Ex: For ParenthesizedExpression
     * (((foo))) returns foo.
     *
     * @param exp
     * @return
     */
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

    /**
     * If exps list contains any ParenthesizedExpression then return new list of
     * striped exps else return the original list. Ex: exps [((foo)), (index)]
     * then return new list [foo, index].
     *
     * @param exps
     * @return
     */
    // REVIEW if possible remove this to avoid side effects - any patch to new
    // list will not reflect in copy
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

    /**
     *
     * Strip wrapper parenthesises and get parent. Ex: return (((foo))); For
     * SimpleName exp 'foo' returns the ReturnStatement 'return (((foo)))'.
     *
     * @param exp
     * @return
     */
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
