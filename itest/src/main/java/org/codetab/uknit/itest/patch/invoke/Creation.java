package org.codetab.uknit.itest.patch.invoke;

import java.util.Locale;

import org.codetab.uknit.itest.patch.invoke.Model.Foo;

public class Creation {

    public Locale assginCreate(final Foo foo) {
        Locale locale = new Locale(foo.lang());
        return locale;
    }

    public Locale returnCreate(final Foo foo) {
        return new Locale(foo.lang());
    }

    public Locale assginCreate2(final Foo foo) {
        Locale locale = new Locale(foo.lang(), foo.cntry());
        return locale;
    }

    public Locale returnCreate2(final Foo foo) {
        return new Locale(foo.lang(), foo.cntry());
    }

    public Locale assginCreateNestedInvoke(final Foo foo) {
        Locale locale = new Locale(foo.lang(foo.cntry()));
        return locale;
    }

    public Locale returnCreateNestedInvoke(final Foo foo) {
        return new Locale(foo.lang(foo.cntry()));
    }
}
