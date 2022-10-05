package org.codetab.uknit.jtest.array;

/**
 * Array access.
 *
 * <pre>
 * ArrayAccess:
 *    Expression <b>[</b> Expression <b>]</b>
 * </pre>
 *
 * An array access expression contains two subexpressions, the array reference
 * expression (before the left bracket) and the index expression (within the
 * brackets). Note that the array reference expression may be a name or any
 * primary expression that is not an array creation expression. Arrays must be
 * indexed by int values; short, byte, or char values may also be used as index
 * values because they are subjected to unary numeric promotion and become int
 * values.
 *
 * Primary expressions include most of the simplest kinds of expressions, from
 * which all others are constructed: literals, class literals, field accesses,
 * method invocations, and array accesses.
 *
 * @author m
 *
 */
public class ArrayAccess {

    public int primitiveType() {
        int[] foo = new int[] {1, 5};
        return foo[1];
    }

    public String typeName() {
        String[] foo = new String[] {"foo", "bar"};
        return foo[1];
    }
}
