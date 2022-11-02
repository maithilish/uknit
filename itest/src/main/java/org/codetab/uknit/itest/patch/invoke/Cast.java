package org.codetab.uknit.itest.patch.invoke;

import java.util.Locale;

import org.codetab.uknit.itest.patch.invoke.Model.Foo;

public class Cast {

    public Locale cast(final Foo foo) {
        return (Locale) foo.obj();
    }

}
