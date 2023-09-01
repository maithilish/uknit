package org.codetab.uknit.core.make.exp.srv;

import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.inject.Inject;

import org.codetab.uknit.core.node.NodeFactory;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;

public class SafeExpSetter {

    @Inject
    private NodeFactory factory;

    // REVIEW - example it
    public void setExp(final Expression copy,
            final Supplier<Expression> getOper,
            final Consumer<Expression> setOper, final Expression exp) {

        if (exp instanceof ConditionalExpression
                && getOper.get() instanceof ParenthesizedExpression) {
            /*
             * if exp of an exp is ConditionalExp then retain parenthesize. Ex:
             * 1 > (a < 1 ? 1 : 0), the right exp of infix exp is CE and
             * parenthesized, so set unparenthesized exp (the input parameter
             * exp) to existing ParenthesizedExpression.
             */
            ParenthesizedExpression pExp =
                    (ParenthesizedExpression) getOper.get();
            pExp.setExpression(factory.copyNode(exp));
        } else if (exp instanceof InfixExpression
                && copy instanceof InfixExpression
                && getOper.get() instanceof ParenthesizedExpression) {
            /*
             *
             * if exp of an exp is Infix and the copy is also Infix then retain
             * parenthesize. Ex: true == ((a < 1 ? 1 : 0) == (a < 1 ? 1 : 0)),
             * the the outer infix (copy) contains inner infix in parenthesize
             * (exp) then set the inner infix to then existing
             * ParenthesizedExpression. Same is true for ((a < 1 ? 1 : 0) == (a
             * < 1 ? 1 : 0)) == true.
             */
            ParenthesizedExpression pExp =
                    (ParenthesizedExpression) getOper.get();
            pExp.setExpression(factory.copyNode(exp));
        } else {
            // else replace existing exp with unparenthesized exp
            setOper.accept(factory.copyNode(exp));
        }
    }
}
