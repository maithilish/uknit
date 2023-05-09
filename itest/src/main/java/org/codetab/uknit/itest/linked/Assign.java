package org.codetab.uknit.itest.linked;

import java.util.Locale;

import org.codetab.uknit.itest.linked.Model.Foo;

/**
 * Linked vars are vars defined in one stmt and used or assigned in another.
 * Tests whether required vars are enabled.
 *
 * @author Maithilish
 *
 */
class Assign {

    public Locale assignCreated() {
        Locale locale = new Locale("en");
        return locale;
    }

    public Locale assignTwiceCreated() {
        Locale locale = new Locale("en");
        Locale locale2 = locale;
        return locale2;
    }

    public Locale assignThriceCreated() {
        Locale locale = new Locale("en");
        Locale locale2 = locale;
        Locale locale3 = locale2;
        return locale3;
    }

    /**
     * Capitalised var names are not converted to lower case in generated test.
     *
     * @param foo
     * @return
     */
    // CHECKSTYLE:OFF
    public Locale assignInvoke(final Foo foo) {
        Locale Locale = foo.locale();
        Locale Locale2 = Locale;
        Locale Locale3 = Locale2;
        return Locale3;
    }
    // CHECKSTYLE:ON

    public String assignLiteral(final Foo foo) {
        String name = "foo";
        String name2 = name;
        String name3 = name2;
        return name3;
    }

    public String[] assignArray() {
        String[] names = {"foo"};
        String[] names2 = names;
        String[] names3 = names2;
        return names3;
    }

    public String assignArrayAccess(final String[] names) {
        String name = names[0];
        String name2 = name;
        String name3 = name2;
        return name3;
    }
}
