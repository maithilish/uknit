package org.codetab.uknit.itest.brace.patch;

import java.util.Locale;

import org.codetab.uknit.itest.brace.patch.Model.Bar;
import org.codetab.uknit.itest.brace.patch.Model.Foo;

class Conditional {

    public Locale assignConditional(final Foo foo, final Bar bar) {
        Locale locale = (foo.size()) > 1 ? bar.locale((foo.lang()))
                : (bar.locale("en"));
        return locale;
    }

    public Locale returnConditional(final Foo foo, final Bar bar) {
        return (foo.size()) > 1 ? bar.locale((foo.lang())) : (bar.locale("en"));
    }
}
