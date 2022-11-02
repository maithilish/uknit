package org.codetab.uknit.itest.patch.invoke;

import java.util.Locale;

import org.codetab.uknit.itest.patch.invoke.Model.Bar;
import org.codetab.uknit.itest.patch.invoke.Model.Foo;

/**
 *
 * TODO N - If invoke on real obj then arg should be real, See
 * assginInvokeOnExpression()
 *
 * @author Maithilish
 *
 */
public class Invoke {

    public Locale assignInvoke(final Bar bar, final Foo foo) {
        Locale locale = bar.locale(foo.lang());
        return locale;
    }

    public Locale returnInvoke(final Bar bar, final Foo foo) {
        return bar.locale(foo.lang());
    }

    public Locale assignInvoke2(final Bar bar, final Foo foo) {
        Locale locale = bar.locale(foo.lang(), foo.cntry());
        return locale;
    }

    public Locale returnInvoke2(final Bar bar, final Foo foo) {
        return bar.locale(foo.lang(), foo.cntry());
    }

    public Locale assignInvokeNested(final Bar bar, final Foo foo) {
        Locale locale = bar.locale(foo.lang(foo.cntry()), foo.cntry());
        return locale;
    }

    public Locale returnInvokeNested(final Bar bar, final Foo foo) {
        return bar.locale(foo.lang(foo.cntry()), foo.cntry());
    }

    public String assginInvokeOnExpression(final Foo foo, final Bar bar) {
        String displayName =
                (new Locale(foo.lang())).getDisplayName(bar.locale(foo.lang()));
        return displayName;
    }

    public String returnInvokeOnExpression(final Foo foo, final Bar bar) {
        return (new Locale(foo.lang())).getDisplayName(bar.locale(foo.lang()));
    }
}
