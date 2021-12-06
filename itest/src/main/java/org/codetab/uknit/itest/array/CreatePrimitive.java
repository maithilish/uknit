package org.codetab.uknit.itest.array;

public class CreatePrimitive {

    public int[] createIntArray() {
        int[] anArray = new int[2];
        anArray[0] = 100;
        return anArray;
    }

    public int[][] createIntTwoDimArray() {
        int[][] anArray = new int[2][3];
        anArray[0][0] = 100;
        return anArray;
    }

    public int[] declareAndCreateIntArray() {
        int[] anArray;
        anArray = new int[2];
        anArray[0] = 100;
        return anArray;
    }

    public int[][] declareAndCreateIntTwoDimArray() {
        int[][] anArray;
        anArray = new int[2][4];
        anArray[0][1] = 100;
        return anArray;
    }

    @SuppressWarnings("deprecation")
    public int[] createAndAcessIntArray() {
        int[] anArray;
        anArray = new int[2];
        anArray[0] = 100;
        anArray[1] = new Integer(200);
        return anArray;
    }
}
