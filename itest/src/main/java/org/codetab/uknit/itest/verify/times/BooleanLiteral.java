package org.codetab.uknit.itest.verify.times;

import org.codetab.uknit.itest.verify.times.Model.Foo;

class BooleanLiteral {

    // arg is boolean literal
    public void booleanLiteral(final Foo foo) {
        boolean a = true;
        boolean b = true;
        boolean c = false;

        foo.append(a);
        foo.append(b);
        foo.append(c);
    }
}
