package org.codetab.uknit.jtest.cast;

/**
 * Cast expression.
 *
 * <pre>
 * CastExpression:
 *    <b>(</b> Type <b>)</b> Expression
 * </pre>
 *
 * TODO H - fix the improper test
 *
 * @author m
 *
 */
public class CastInReturn {

    public String foo() {
        Object obj = new String("hello");
        return (String) obj;
    }

}
