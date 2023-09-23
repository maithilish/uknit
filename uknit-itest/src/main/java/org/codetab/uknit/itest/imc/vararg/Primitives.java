package org.codetab.uknit.itest.imc.vararg;

import org.codetab.uknit.itest.imc.vararg.Model.Foo;

public class Primitives {

    public void booleans(final Foo foo) {
        boolean a = false;
        booleansIm(foo, a);
        booleansIm(foo, false);
        booleansIm(foo, true);
        booleansIm((foo), (true));
    }

    private void booleansIm(final Foo foo, final boolean... va) {
        foo.appendString(String.valueOf(va[0]));
        foo.appendBoolean(va[0]);
    }

    public void ints(final Foo foo) {
        intsIm(foo, 1);
        intsIm(foo, 1, 2, 3);
    }

    private void intsIm(final Foo foo, final int... va) {
        foo.appendString(String.valueOf(va[0]));
        foo.appendInt(va[0]);
    }
}
