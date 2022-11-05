package org.codetab.uknit.itest.linked;

import java.util.Date;

import org.codetab.uknit.itest.linked.Model.Foo;

/**
 * Linked vars are vars defined in one stmt and used or casted in another. Tests
 * whether required vars are enabled.
 *
 * @author Maithilish
 *
 */
public class Cast {

    public Date castCreated() {
        Object date = new Date();
        return (Date) date;
    }

    public Date castTwiceCreated() {
        Object date = new Date();
        Date date2 = (Date) date;
        return date2;
    }

    public Date castThriceCreated() {
        Object date = new Date();
        Date date2 = (Date) date;
        Date date3 = date2;
        return date3;
    }

    public Date castInvoke(final Foo foo) {
        Object date = foo.date();
        Date date2 = (Date) date;
        Date date3 = date2;
        return date3;
    }

    public String castLiteral(final Foo foo) {
        Object name = "foo";
        String name2 = (String) name;
        String name3 = name2;
        return name3;
    }

    public String castArrayAccess(final Object[] names) {
        String name = (String) names[0];
        String name2 = name;
        String name3 = name2;
        return name3;
    }

    /*
     * String[] names2 = (String[]) names; throws cannot be cast runtime error
     * so test is not required.
     */
    // public String[] castArray() {
    // Object[] names = {"foo"};
    // String[] names2 = (String[]) names;
    // String[] names3 = names2;
    // return names3;
    // }
}
