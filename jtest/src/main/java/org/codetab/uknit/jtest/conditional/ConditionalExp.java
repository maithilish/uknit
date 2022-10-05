package org.codetab.uknit.jtest.conditional;

import java.util.Date;

/**
 * Conditional expression AST node type.
 *
 * <pre>
 * ConditionalExpression:
 *    Expression <b>?</b> Expression <b>:</b> Expression
 * </pre>
 *
 * TODO N - enable ctl flow logic for missing test branch.
 *
 * @author m
 *
 */
public class ConditionalExp {

    public boolean conditionalPrimitive(final int a, final int b) {
        return a > b ? true : false;
    }

    public boolean conditionalReal(final String str1, final String str2) {
        return str1.compareTo(str2) > 1 ? true : false;
    }

    public boolean conditionalMock(final Date date1, final Date date2) {
        return date1.compareTo(date2) > 0 ? true : false;
    }

}
