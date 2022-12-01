package org.codetab.uknit.itest.brace.patch;

import java.util.Locale;

import org.codetab.uknit.itest.brace.patch.Model.Foo;

public class Cast {

    public Locale createAssignCast() {
        Object obj = new Locale("en");
        Locale locale = (Locale) obj;
        return locale;
    }

    public Locale createReturnCast() {
        Object obj = new Locale("en");
        return (Locale) obj;
    }

    public Locale assignCast(final Foo foo) {
        Locale locale = (Locale) (foo.obj());
        return locale;
    }

    public Locale returnCast(final Foo foo) {
        return (Locale) (foo.obj());
    }

    public Locale invokeAssignCast(final Foo foo) {
        Object obj = (foo.obj());
        Locale locale = (Locale) obj;
        return locale;
    }

    public Locale invokeReturnCast(final Foo foo) {
        Object obj = (foo.obj());
        return (Locale) obj;
    }

}
