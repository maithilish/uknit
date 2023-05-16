package org.codetab.uknit.itest.verify.times;

import org.codetab.uknit.itest.verify.times.Model.Foo;

class ArrayCreation {

    public void dim(final Foo foo) {
        foo.append(new String[1]);
        foo.append(new String[1]);
    }

    public void literal(final Foo foo) {
        foo.append(new String[] {"foo", "bar"});
        foo.append(new String[] {"foo", "bar"});
        foo.append(new String[] {"foo", "bar", "baz"});
    }
}
