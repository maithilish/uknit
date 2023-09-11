package org.codetab.uknit.itest.exp.value;

import org.codetab.uknit.itest.exp.value.Model.Foo;

public class InternalCall {

    // use of value in IM
    public void invokeInIM(final Foo foo) {
        String city = "foo";
        String state = "bar";
        im(foo, city);
        im(foo, "baz");
        im(foo, state);
        im(foo, "zoo");
    }

    private void im(final Foo foo, final String str) {
        foo.appendString(str);
        foo.appendString(String.valueOf(str));
    }

    /**
     * STEPIN - array access value is not proper.
     *
     * TODO N - See ArgParams.init() to do.
     *
     * @param foo
     */
    public void arrayAccessByVar(final Foo foo) {
        String[] a = {"x", "y"};
        arrayAccessIm(foo, a, 0);
        arrayAccessIm(foo, a, 1);
    }

    private void arrayAccessIm(final Foo foo, final String[] str,
            final int index) {
        foo.appendString(str[index]);
        foo.appendString(String.valueOf(str[index]));
    }
}
