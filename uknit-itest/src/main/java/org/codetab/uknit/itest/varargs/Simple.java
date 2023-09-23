package org.codetab.uknit.itest.varargs;

import org.codetab.uknit.itest.varargs.Model.Foo;

public class Simple {

    public void varargs(final Foo foo, final String... va) {
        foo.append(va[0]);
        foo.append(va[1]);
    }
}
