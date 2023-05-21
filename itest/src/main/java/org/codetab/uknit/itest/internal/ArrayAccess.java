package org.codetab.uknit.itest.internal;

import org.codetab.uknit.itest.internal.Model.Foo;

public class ArrayAccess {

    public void argParamSame(final Foo foo) {
        String[] array = {"foo", "bar"};
        imSame(foo, array);
        String[] b = {"foox", new String("barx")};
        imSame(foo, b);
        foo.append(array[0]);
    }

    public void argParamSame2(final Foo foo) {
        String[] array = {"foo", "bar"};
        imSame(foo, array);
        String[] b = {"foox", new String("barx")};
        String[] c = b;
        String[] d = c;
        imSame(foo, d);
        foo.append(array[0]);
    }

    private void imSame(final Foo foo, final String[] array) {
        foo.append(array[0]);
        foo.append(array[1]);
    }

    private void im(final Foo foo, final String[] names) {
        foo.append(names[0]);
        foo.append(names[1]);
    }

    public void argParamDiff(final Foo foo) {
        String[] array = {"foo", new String("bar")};
        im(foo, array);

        String[] b = {new String("foox"), "barx"};
        im(foo, b);
        foo.append(array[0]);
    }

    private void imArrayDefinedInIM(final Foo foo) {
        String[] array = {new String("foo"), "bar"};
        foo.append(array[0]);
        foo.append(array[1]);

        array = new String[] {new String("foox"), "barx"};
        foo.append(array[1]);
    }

    public void arrayDefinedInIM(final Foo foo) {
        imArrayDefinedInIM(foo);
        imArrayDefinedInIM(foo);
    }

    public void sameInitializerAssignedToDifferentArraySame(final Foo foo) {
        String[] array = {"foo", "bar"};
        imSame(foo, array);
        String[] b = array;
        imSame(foo, b);
        String[] c = b;
        imSame(foo, c);
    }

    public void sameInitializerAssignedToDifferentArrayDiff(final Foo foo) {
        String[] a = {"foo", "bar"};
        im(foo, a);
        String[] b = a;
        im(foo, b);
        String[] c = b;
        im(foo, c);
    }

    public void reassignArrayItemSame(final Foo foo) {
        String[] array = {"foo", new String("bar")};
        imSame(foo, array);

        array[1] = "bar";
        imSame(foo, array);

        array[1] = "barx";
        imSame(foo, array);
    }

    public void reassignArrayItemDiff(final Foo foo) {
        String[] array = {"foo", new String("bar")};
        im(foo, array);

        array[1] = "bar";
        im(foo, array);

        array[1] = "barx";
        im(foo, array);
    }

    public void initializerItemIsVarSame(final Foo foo) {
        String a = "foo";
        String b = "bar";
        String array[] = new String[] {a, b};

        a = "foox";
        b = "barx";
        String array2[] = new String[] {a, b};

        imSame(foo, array);
        imSame(foo, array2);

        array = new String[] {b, a};

        imSame(foo, array);
    }

    public void initializerItemIsVarDiff(final Foo foo) {
        String a = "foo";
        String b = "bar";
        String array[] = new String[] {a, b};

        a = "foox";
        b = "barx";
        String array2[] = new String[] {a, b};

        im(foo, array);
        im(foo, array2);

        array = new String[] {b, a};

        im(foo, array);
    }
}
