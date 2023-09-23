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

    public void sameInitializerAssignedToDifferentArray(final Foo foo) {
        String[] array = {"foo", "bar"};
        foo.append(array[0]);
        String[] b = array;
        foo.append(b[0]);
        foo.append(b[1]);
        String[] c = b;
        foo.append(c[0]);
        foo.append(c[1]);
    }

    // STEPIN - the new String() is not interpreted and two verify generated
    // instead of one, user has to update the one verify with times()
    public void reassignArrayItem(final Foo foo) {
        String[] array = {"foo", new String("bar")};
        foo.append(array[1]);

        array[1] = "bar";
        foo.append(array[1]);
    }

    /**
     * TODO N - Returns value if array initializer item is literal and simple
     * name. Handle other exps such as MI in nodes.Arrays.getValue().
     *
     * @param foo
     */
    public void initializerItemIsVar(final Foo foo) {
        String a = "foo";
        String array[] = new String[] {foo.format(a), a};

        a = "bar";
        String array2[] = new String[] {foo.format(a), a};

        foo.append(array[1]);
        foo.append(array2[1]);
    }
}
