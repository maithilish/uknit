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

    /**
     * Safely replace exp. In infix exp 1 > ((a < 1 ? 1 : 0)) we need to retain
     * the outer parenthesize around the right side conditional exp discarding
     * the inner one. Here the method sets the striped exp (cleanExp) to the
     * outer parenthesize of the right exp.
     *
     * However there is no need to retain it in case of infix exp 1 + ((1)) and
     * the method sets the cleaned exp directly as the right exp.
     *
     * @param copy
     *            exp to which cleanExp is set
     * @param getOper
     *            the copy's method ref to get the exp
     * @param setOper
     *            the copy's method ref to set the exp
     * @param cleanExp
     *            striped exp
     */
    public void setExp(final Expression copy,
            final Supplier<Expression> getOper,
            final Consumer<Expression> setOper, final Expression cleanExp) {

        if (cleanExp instanceof ConditionalExpression
                && getOper.get() instanceof ParenthesizedExpression) {
            /*
             * if exp of an exp is ConditionalExp then retain parenthesize. Ex:
             * 1 > (a < 1 ? 1 : 0), the right exp of infix exp is CE and
             * parenthesized, so set unparenthesized exp (the input parameter
             * exp) to existing ParenthesizedExpression.
             */
            ParenthesizedExpression pExp =
                    (ParenthesizedExpression) getOper.get();
            pExp.setExpression(factory.copyNode(cleanExp));
        } else if (cleanExp instanceof InfixExpression
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
            pExp.setExpression(factory.copyNode(cleanExp));
        } else {
            // else replace existing exp with unparenthesized exp
            setOper.accept(factory.copyNode(cleanExp));
        }
    }
}
