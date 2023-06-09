package org.codetab.uknit.itest.exp.value;

import org.codetab.uknit.itest.exp.value.Model.Foo;

class Conditional {

    public void expsAreVar(final Foo foo) {
        boolean flg = false;
        String a = "foo";
        String b = "bar";

        foo.appendString(flg ? a : b);
    }

}
