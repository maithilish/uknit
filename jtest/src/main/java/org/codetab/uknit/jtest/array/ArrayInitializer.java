package org.codetab.uknit.jtest.array;

/**
 * Array initializer test.
 *
 * <pre>
 * ArrayInitializer:
 *      <b>{</b> [ Expression { <b>,</b> Expression} [ <b>,</b> ]] <b>}</b>
 * </pre>
 *
 *
 * @author m
 *
 */
public class ArrayInitializer {

    public int[] primitiveTypeArrayInitilizer() {
        int[] foo = new int[] {1, 2, 3};
        return foo;
    }

    public String[] typeNameArrayInitilizer() {
        String[] foo = new String[] {"foo", "bar"};
        return foo;
    }

    public char[] primitiveTypeNoNew() {
        char[] ac = {'n', 'o'};
        return ac;
    }

    public String[] typeNameNoNew() {
        String[] foo = {"foo", "bar"};
        return foo;
    }
}
