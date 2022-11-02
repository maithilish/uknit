package org.codetab.uknit.itest.patch.invoke;

import java.util.Locale;

import org.codetab.uknit.itest.patch.invoke.Model.Foo;

/**
 *
 * TODO Normal - fix the Class name (Array) and var name (array) conflict.
 *
 * @author Maithilish
 *
 */
public class Array {

    public Locale[] assignArrayCreation(final Foo foo) {
        Locale[] locales = new Locale[foo.size()];
        return locales;
    }

    public Locale[] returnArrayCreation(final Foo foo) {
        return new Locale[foo.size()];
    }

    public Locale assignArrayAccess(final Foo foo, final Locale[] locales) {
        Locale locale = locales[foo.index()];
        return locale;
    }

    public Locale returnArrayAccess(final Foo foo, final Locale[] locales) {
        return locales[foo.index()];
    }

    /*
     * ArrayInitializer can only be used in assign not in return;
     */
    public Locale[] assignArrayInitializer(final Foo foo) {
        Locale[] locales = {new Locale(foo.lang()), new Locale("foo", "bar")};
        return locales;
    }
}
