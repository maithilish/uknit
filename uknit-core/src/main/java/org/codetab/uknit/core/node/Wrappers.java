package org.codetab.uknit.core.node;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

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
        if (isNull(exp)) {
            return exp;
        }
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
     * Get exp that is wrapped in parenthesises. Ex: For ParenthesizedExpression
     * (((foo))) returns foo.
     *
     * @param exp
     * @return
     */
    public Expression strip(final Expression exp) {
        Expression eExp = exp;
        if (nodes.is(exp, ParenthesizedExpression.class)) {
            ParenthesizedExpression pExp =
                    nodes.as(exp, ParenthesizedExpression.class);
            /**
             * If pExp has cast and pExp parent is any type of exp other than
             * vdf then don't strip. If pExp has no cast or has cast with pExp
             * parent is vdf then strip. <code>
             * int index = ((Foo) foo).index(); // parent of pExp is mi, don't strip
             * Locale locale2 = ((Locale) (locale)); // parent is vdf, strip
             * </code>
             */
            boolean doStrip = true;
            if (nodes.is(pExp.getExpression(), CastExpression.class) && !nodes
                    .is(pExp.getParent(), VariableDeclarationFragment.class)) {
                doStrip = false;
            }
            if (doStrip) {
                // get pExp's exp to strip
                eExp = pExp.getExpression();
            } else {
                // don't strip return pExp
                return pExp;
            }
        }
        // recursively strip inner parenthesises
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
     * Use this method with caution. Don't use it in any patch related class.
     * Any modification to the new list will not reflect in the copy!
     *
     * @param exps
     * @return
     */
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
     * @param node
     * @return
     */
    public ASTNode stripAndGetParent(final ASTNode node) {
        ASTNode parent = node.getParent();
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
