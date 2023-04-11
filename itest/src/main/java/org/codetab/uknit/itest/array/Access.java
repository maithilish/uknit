package org.codetab.uknit.itest.array;

import org.codetab.uknit.itest.array.Model.Foo;

class Access {

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

    /**
     * STEPIN - two similar verifies are merged and this makes var of one verify
     * unused. Remove the unused one manually.
     *
     * The var is enabled/disabled before output generation and verify merge
     * happens in output phase and this results in dangling var.
     *
     * @param foo
     */
    public void declareTwoArray(final Foo foo) {
        String[] array = {"foo", "bar"};
        foo.append(array[0]);
        foo.append(array[1]);
        String[] b = {"foox", new String("barx")};
        foo.append(b[0]);
        foo.append(b[1]);
        foo.append(array[0]);
    }

    // reassign array with initializer is not allowed

    public void arrayParameter(final Foo foo, final String[] array) {
        foo.append(array[0]);
        foo.append(array[1]);
        String[] b = {"foox", new String("barx")};
        foo.append(b[0]);
        foo.append(b[1]);
        foo.append(array[0]);
    }

    public int accessInfix(final boolean flag) {
        int[] array = {10, 20};
        int foo = flag ? array[1] : array[0];
        return foo;
    }

    public int returnAccessInfix(final boolean flag) {
        int[] array = {10, 20};
        return flag ? array[1] : array[0];
    }

    public int accessPrefix() {
        int[] array = {10, 20};
        int foo = array[0]++;
        return foo;
    }

    public int returnAccessPrefix() {
        int[] array = {10, 20};
        return array[0]++;
    }

    public int returnInitializedAccess() {
        int[] array = {10, 20};
        return array[0];
    }
}
