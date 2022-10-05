package org.codetab.uknit.jtest.array;

/**
 * Type node for an array type.
 * <p>
 * In JLS8 and later, array types are represented by a base element type (which
 * cannot be an array type) and a list of dimensions, each of which may have a
 * list of annotations.
 * </p>
 *
 * <pre>
 * ArrayType:
 *    Type Dimension <b>{</b> Dimension <b>}</b>
 * </pre>
 *
 * Array types are used in declarations and in cast expressions.
 *
 * An array type is written as the name of an element type followed by some
 * number of empty pairs of square brackets []. The number of bracket pairs
 * indicates the depth of array nesting.
 *
 * TODO H - fix array type cast
 *
 * @author m
 *
 */
public class ArrayType {

    public String[] arrayTypeCast() {
        Object[] foo = new String[] {"foo", "bar"};
        return (String[]) foo;
    }

}
