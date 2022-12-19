package org.codetab.uknit.itest.linked;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Locale;

import org.codetab.uknit.itest.linked.Model.Foo;

/**
 * Linked vars are vars defined in one stmt and used or casted in another. Tests
 * whether required vars are enabled.
 *
 * @author Maithilish
 *
 */
public class Cast {

    public Locale castCreated() {
        Object locale = new Locale("en");
        return (Locale) locale;
    }

    public Locale castTwiceCreated() {
        Object locale = new Locale("en");
        Locale locale2 = (Locale) locale;
        return locale2;
    }

    public Locale castThriceCreated() {
        Object locale = new Locale("en");
        Locale locale2 = (Locale) locale;
        Locale locale3 = locale2;
        return locale3;
    }

    public Locale castInvoke(final Foo foo) {
        Object locale = foo.locale();
        Locale locale2 = (Locale) locale;
        Locale locale3 = locale2;
        return locale3;
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

    public Locale createAssignCast(final Foo foo) {
        Object obj = new Locale("");
        Locale locale = (Locale) obj;
        return locale;
    }

    public Locale createReturnCast(final Foo foo) {
        Object obj = new Locale("");
        return (Locale) obj;
    }

    public Locale invokeAssignCast(final Foo foo) {
        Object obj = foo.obj();
        Locale locale = (Locale) obj;
        return locale;
    }

    public Locale invokeReturnCast(final Foo foo) {
        Object obj = foo.obj();
        return (Locale) obj;
    }

    public FileInputStream assginMultiCast(final Foo foo) {
        FileInputStream bar = ((FileInputStream) ((InputStream) (foo.obj())));
        return bar;
    }

    public FileInputStream returnMultiCast(final Foo foo) {
        return ((FileInputStream) ((InputStream) (foo.obj())));
    }
}
