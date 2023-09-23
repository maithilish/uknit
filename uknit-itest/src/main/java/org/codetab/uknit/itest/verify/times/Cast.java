package org.codetab.uknit.itest.verify.times;

import org.codetab.uknit.itest.verify.times.Model.Foo;

class Cast {

    // arg is cast exp
    public void cast(final Foo foo) {
        Object city = "foo";
        Object state = "foo";
        Object country = "bar";
        foo.append((String) city);
        foo.append((String) state);
        foo.append((String) country);
    }
}
