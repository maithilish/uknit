package org.codetab.uknit.itest.cast;

import org.codetab.uknit.itest.cast.Model.Foo;

public class Param {

    public void castParam(final Foo foo, final Object fooObj,
            final Object count) {
        foo.append(((Foo) fooObj).index());

        foo.append((((Foo) fooObj)).index());
    }
}
