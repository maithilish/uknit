package org.codetab.uknit.itest.varargs;

import org.codetab.uknit.itest.varargs.Model.Foo;

public class Inline {

    private void imc(final Foo foo, final String... va) {
        foo.append(va[0]);
        foo.append(va[1]);
    }

    /*
     * not allowed, missing varargs arg, 1 args passed to varargs but code is
     * trying to access 2 items
     */
    // public void oneVaArg(final Foo foo) {
    // String a = "foo";
    // imc(foo, "foo");
    // foo.append(a);
    // imc(foo, "foo");
    // imc(foo, "barx");
    // foo.append(a);
    // }

    public void twoVaArg(final Foo foo) {
        String a = "foo";
        imc(foo, "foo", "bar");
        foo.append(a);
        imc(foo, "barx", "foox");
        imc(foo, "barx", "foo");
        foo.append(a);
    }

    public void threeVaArg(final Foo foo) {
        String a = "baz";
        imc(foo, "foo", "bar", "baz");
        foo.append(a);
        imc(foo, "barx", "foox", "bazx");
        imc(foo, "barx", "foo", "baz");
        foo.append(a);
    }

    public void reassignedTwoVaArg(final Foo foo) {
        String a = "foo";
        String b = "bar";
        imc(foo, a, b);

        foo.append(a);

        a = "foox";
        b = "barx";
        imc(foo, b, a);
    }

    public void nullVaArg(final Foo foo) {
        String a = "foo";
        imc(foo, "foo", null);
        foo.append(a);
        imc(foo, null, "foox");
        imc(foo, null, null);
        foo.append(a);
    }
}
