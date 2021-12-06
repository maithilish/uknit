package org.codetab.uknit.itest.array;

public class AccessPrimitive {

    public int access() {
        int[] array = new int[1];
        array[0] = 10;

        int foo = array[0];
        return foo;
    }

    public int accessAndReturn() {
        int[] array = new int[1];
        array[0] = 10;

        return array[0];
    }
}
