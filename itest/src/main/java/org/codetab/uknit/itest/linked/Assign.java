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
public class Assign {

    public Locale assignCreated() {
        Locale Locale = new Locale("en");
        return Locale;
    }

    public Locale assignTwiceCreated() {
        Locale Locale = new Locale("en");
        Locale Locale2 = Locale;
        return Locale2;
    }

    public Locale assignThriceCreated() {
        Locale Locale = new Locale("en");
        Locale Locale2 = Locale;
        Locale Locale3 = Locale2;
        return Locale3;
    }

    public Locale assignInvoke(final Foo foo) {
        Locale Locale = foo.locale();
        Locale Locale2 = Locale;
        Locale Locale3 = Locale2;
        return Locale3;
    }

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
