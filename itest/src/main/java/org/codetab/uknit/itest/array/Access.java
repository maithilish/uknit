package org.codetab.uknit.itest.array;

public class Access {

    public int assignAccessPrimitive() {
        int[] array = new int[1];
        array[0] = 10;

        int foo = array[0];
        return foo;
    }

    public int returnAccess() {
        int[] array = new int[1];
        array[0] = 10;

        return array[0];
    }
}
