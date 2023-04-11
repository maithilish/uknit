package org.codetab.uknit.itest.internal;

import org.codetab.uknit.itest.internal.Model.Foo;

public class ArrayAccess {

    public void argParamSame(final Foo foo) {
        String[] array = {"foo", "bar"};
        imcSame(foo, array);
        String[] b = {"foox", new String("barx")};
        imcSame(foo, b);
        foo.append(array[0]);
    }

    private void imcSame(final Foo foo, final String[] array) {
        foo.append(array[0]);
        foo.append(array[1]);
    }

    public void argParamDiff(final Foo foo) {
        String[] array = {"foo", new String("bar")};
        imcDiff(foo, array);

        String[] b = {new String("foox"), "barx"};
        imcDiff(foo, b);
        foo.append(array[0]);
    }

    private void imcDiff(final Foo foo, final String[] names) {
        foo.append(names[0]);
        foo.append(names[1]);
    }

    private void imcArrayDefinedInIM(final Foo foo) {
        String[] array = {new String("foo"), "bar"};
        foo.append(array[0]);
        foo.append(array[1]);
    }

    public void arrayDefinedInIM(final Foo foo) {
        imcArrayDefinedInIM(foo);
        imcArrayDefinedInIM(foo);
    }
}
