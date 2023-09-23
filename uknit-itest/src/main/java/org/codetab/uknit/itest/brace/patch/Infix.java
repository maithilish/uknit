package org.codetab.uknit.itest.brace.patch;

import org.codetab.uknit.itest.brace.patch.Model.Bar;
import org.codetab.uknit.itest.brace.patch.Model.Foo;

class Infix {

    public String assignInfix(final Foo foo, final Bar bar) {
        String str = (foo.lang()) + " " + (bar.locale((foo.lang())));
        return str;
    }

    public String returnInfix(final Foo foo, final Bar bar) {
        return (foo.lang()) + " " + (bar.locale((foo.lang())));
    }

    public String assignCreationInfix() {
        String foo = new String(("foo") + ("bar"));
        return foo;
    }

    public String returnCreationInfix() {
        return new String(("foo") + ("bar"));
    }

    public String assignConditionalInfix(final Foo foo) {
        String str = (foo.size()) > 1 ? ("foo") + (foo.cntry())
                : (foo.lang()) + ("foo");
        return str;
    }

    public String returnConditionalInfix(final Foo foo) {
        return (foo.size()) > 1 ? ("foo") + (foo.cntry())
                : (foo.lang()) + ("foo");
    }

    public String assignCastInfix(final Foo foo) {
        Object obj = (Object) ("foo") + ("bar");
        return (String) obj;
    }

    public Object returnCastInfix(final Foo foo) {
        return (Object) ("foo") + ("bar");
    }

    public String[] assignArrayCreationInfix(final Foo foo) {
        String[] names = new String[(foo.size()) + 1];
        return names;
    }

    public String[] returnArrayCreationInfix(final Foo foo) {
        return new String[(foo.size()) + 1];
    }

    public String assignArrayAccessInfix(final Foo foo, final String[] names) {
        String name = names[(foo.size()) + 1];
        return name;
    }

    public String returnArrayAccessInfix(final Foo foo, final String[] names) {
        return names[(foo.size()) + 1];
    }

    public String assignInvokeInfix(final Foo foo) {
        String lang = foo.lang(("en_") + (foo.cntry()));
        return lang;
    }

    public String returnInvokeInfix(final Foo foo) {
        return foo.lang(("en_") + (foo.cntry()));
    }

    /*
     * No test for post and prefix. Invoke not allowed with post and prefix
     * operators. For example foo.index()++ is not allowed.
     *
     */
}
