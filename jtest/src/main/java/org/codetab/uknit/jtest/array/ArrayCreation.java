package org.codetab.uknit.jtest.array;

/**
 * Array creation and initializer test.
 *
 * <pre>
 * ArrayCreation:
 *    <b>new</b> PrimitiveType <b>[</b> Expression <b>]</b> { <b>[</b> Expression <b>]</b> } { <b>[</b> <b>]</b> }
 *    <b>new</b> TypeName [ <b>&lt;</b> Type { <b>,</b> Type } <b>&gt;</b> ]
 *        <b>[</b> Expression <b>]</b> { <b>[</b> Expression <b>]</b> } { <b>[</b> <b>]</b> }
 *    <b>new</b> PrimitiveType <b>[</b> <b>]</b> { <b>[</b> <b>]</b> } ArrayInitializer
 *    <b>new</b> TypeName [ <b>&lt;</b> Type { <b>,</b> Type } <b>&gt;</b> ]
 *        <b>[</b> <b>]</b> { <b>[</b> <b>]</b> } ArrayInitializer
 * </pre>
 *
 * The type of each dimension expression within a DimExpr must be a type that is
 * convertible to an integral type, or a compile-time error occurs. For example,
 * new char[] x = new char[10L]; throws error as 10L is not int. Also,
 * Assignment is mandatory.
 *
 * @author m
 *
 */
public class ArrayCreation {

    public int[] primitiveType() {
        int[] foo = new int[5];
        return foo;
    }

    public String[] typeName() {
        String[] foo = new String[4];
        return foo;
    }

    public int[] primitiveTypeDeclareThenCreate() {
        int[] foo;
        foo = new int[5];
        return foo;
    }

    public String[] typeNameDeclareThenCreate() {
        String[] foo;
        foo = new String[4];
        return foo;
    }

    public int[] primitiveTypeArrayInitilizer() {
        int[] foo = new int[] {1, 2, 3};
        return foo;
    }

    public String[] typeNameArrayInitilizer() {
        String[] foo = new String[] {"foo", "bar"};
        return foo;
    }
}
