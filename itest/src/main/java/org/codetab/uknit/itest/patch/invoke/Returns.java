package org.codetab.uknit.itest.patch.invoke;

import java.util.Locale;

public class Returns {

    public Locale cast(final Foo foo) {
        return (Locale) foo.obj();
    }

    public Locale conditional(final Foo foo, final Bar bar) {
        return foo.size() > 1 ? bar.locale(foo.lang()) : bar.locale(foo.lang());
    }

    /*
     * Invoke not allowed with post and prefix operators. For example
     * foo.index()++ is not allowed.
     *
     */
    public String infix(final Foo foo, final Bar bar) {
        return foo.lang() + " " + bar.locale(foo.lang());
    }

    static interface Foo {
        String lang();

        int size();

        int index();

        Object obj();
    }

    static interface Bar {
        Locale locale(String lang);

        int size();
    }
}
