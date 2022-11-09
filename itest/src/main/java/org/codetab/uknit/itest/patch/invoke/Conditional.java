package org.codetab.uknit.itest.patch.invoke;

import java.util.Locale;

import org.codetab.uknit.itest.patch.invoke.Model.Bar;
import org.codetab.uknit.itest.patch.invoke.Model.Foo;

public class Conditional {

    public Locale assignConditional(final Foo foo, final Bar bar) {
        Locale locale =
                foo.size() > 1 ? bar.locale(foo.lang()) : bar.locale("en");
        return locale;
    }

    public Locale returnConditional(final Foo foo, final Bar bar) {
        return foo.size() > 1 ? bar.locale(foo.lang()) : bar.locale("en");
    }
}
