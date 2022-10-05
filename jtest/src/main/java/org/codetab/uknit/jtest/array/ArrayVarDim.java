package org.codetab.uknit.jtest.array;

/**
 * Array var with dimension.
 *
 * TODO N - fix array var with dimension.
 *
 * @author m
 *
 */
public class ArrayVarDim {

    public int[] primitiveTypeVarDim() {
        int foo[] = new int[5];
        return foo;
    }

    public String[] typeNameVarDim() {
        String foo[] = new String[4];
        return foo;
    }

    public int[][] primitiveTypeVarTwoDim() {
        int foo[][] = new int[5][2];
        return foo;
    }

    public String[][][] typeNameVarThreeDim() {
        String foo[][][] = new String[4][2][1];
        return foo;
    }
}
