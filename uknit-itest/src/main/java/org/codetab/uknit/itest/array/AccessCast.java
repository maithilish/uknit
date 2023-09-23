package org.codetab.uknit.itest.array;

import org.codetab.uknit.itest.array.Model.Foo;

class AccessCast {

    public String assignAccessPrimitive() {
        Object[] array = new Object[1];
        array[0] = "foo";

        String foo = (String) array[0];
        return foo;
    }

    public int returnAccess() {
        Object[] array = new Object[1];
        array[0] = 10;

        return (int) array[0];
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
        Object[] array = {"foo", "bar"};
        foo.append((String) array[0]);
        foo.append((String) array[1]);
        Object[] b = {"foox", new String("barx")};
        foo.append((String) b[0]);
        foo.append((String) b[1]);
        foo.append((String) array[0]);
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

    public Object accessInfix(final boolean flag) {
        Object[] array = {10, 20};
        Object foo = flag ? array[1] : array[0];
        return foo;
    }

    public Object returnAccessInfix(final boolean flag) {
        Object[] array = {10, 20};
        return flag ? array[1] : array[0];
    }

    public Object returnInitializedAccess() {
        Object[] array = {10, 20};
        return array[0];
    }

    /**
     * TODO N - the var type of array[0] (apple) is changed from Object to
     * String, but b[0] (grape) is not changed as String.
     *
     * @param foo
     */
    public void sameInitializerAssignedToDifferentArray(final Foo foo) {
        Object[] array = {"foo", "bar"};
        foo.append((String) array[0]);
        Object[] b = array;
        foo.append((String) b[0]);
        foo.append((String) b[1]);
        Object[] c = b;
        foo.append((String) c[0]);
        foo.append((String) c[1]);
    }

    // STEPIN - the new String() is not interpreted and two verify generated
    // instead of one, user has to update the one verify with times()
    public void reassignArrayItem(final Foo foo) {
        Object[] array = {"foo", new String("bar")};
        foo.append((String) array[1]);

        array[1] = "bar";
        foo.append((String) array[1]);
    }

    public void initializerItemIsVar(final Foo foo) {
        Object a = "foo";
        String array[] = new String[] {(String) a};

        a = "bar";
        String array2[] = new String[] {(String) a};

        foo.append(array[0]);
        foo.append(array2[0]);
    }
}
