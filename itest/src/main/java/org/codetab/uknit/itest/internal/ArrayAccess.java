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
    }

    public void arrayDefinedInIM(final Foo foo) {
        imArrayDefinedInIM(foo);
        imArrayDefinedInIM(foo);
    }

    public void sameInitializerAssignedToDifferentArray(final Foo foo) {
        String[] a = {"foo", "bar"};
        im(foo, a);
        String[] b = a;
        im(foo, b);
        String[] c = b;
        im(foo, c);
    }

    public void reassignArrayItem(final Foo foo) {
        String[] array = {"foo", new String("bar")};
        im(foo, array);

        array[1] = "bar";
        im(foo, array);
    }

    public void initializerItemIsVar(final Foo foo) {
        String a = "foo";
        String b = "bar";
        String array[] = new String[] {a, b};

        a = "foox";
        b = "barx";
        String array2[] = new String[] {a, b};

        im(foo, array);
        im(foo, array2);
    }
}
