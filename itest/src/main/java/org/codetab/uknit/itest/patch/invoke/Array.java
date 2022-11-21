package org.codetab.uknit.itest.patch.invoke;

import java.util.Locale;

import org.codetab.uknit.itest.patch.invoke.Model.Bar;
import org.codetab.uknit.itest.patch.invoke.Model.Foo;

/**
 *
 * TODO N - Fix the Class name (Array) and var name (array) conflict. Add one
 * obj to array initializer in assignCastInArrayAccess and
 * returnCastInArrayAccess.
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

    public Locale assignCastInArrayAccess(final Foo foo,
            final Locale[] locales) {
        Locale locale = locales[(int) foo.obj()];
        return locale;
    }

    public Locale returnCastInArrayAccess(final Foo foo,
            final Locale[] locales) {
        return locales[(int) foo.obj()];
    }

    public String[] assignNewInitializer(final Foo foo, final Bar bar) {
        final String[] names = new String[] {foo.name(), bar.name()};
        return names;
    }

    public String[] returnNewInitializer(final Foo foo, final Bar bar) {
        return new String[] {foo.name(), bar.name()};
    }

    /*
     * ArrayInitializer can only be used in assign not in return;
     */
    public Locale[] assignArrayInitializer(final Foo foo) {
        Locale[] locales = {new Locale(foo.lang()), new Locale("foo", "bar")};
        return locales;
    }
}
