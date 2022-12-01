package org.codetab.uknit.itest.brace;

import org.codetab.uknit.itest.brace.Model.Foo;
import org.codetab.uknit.itest.brace.Model.Person;

public class QName {

    public int assignQName(final Person person) {
        int id = ((person.id));
        return id;
    }

    public int returnQName(final Person person) {
        return ((person.id));
    }

    public String assignQNameInInvokeArg(final Foo foo, final Person person) {
        String name = foo.get(((person.id)));
        return name;
    }

    public String returnQNameInInvokeArg(final Foo foo, final Person person) {
        return foo.get(((person.id)));
    }

    public String assignQNameInInvokeExp(final Foo foo, final Person person) {
        String home = (person.contacts).getHome();
        return home;
    }

    public String returnQNameInInvokeExp(final Foo foo, final Person person) {
        return (person.contacts).getHome();
    }

    public int assignInfixQName(final Foo foo, final Person person1,
            final Person person2) {
        int id = (person1.id) + (person2.id);
        return id;
    }

    public int returnInfixQName(final Foo foo, final Person person1,
            final Person person2) {
        return (person1.id) + (person2.id);
    }

    public int assignConditionalQName(final Foo foo, final Person person) {
        int id = ((person.id)) > 1 ? 1 : 0;
        return id;
    }

    public int returnConditionalQName(final Foo foo, final Person person) {
        return ((person.id)) > 1 ? 1 : 0;
    }

    public int assignPostfixQName(final Foo foo, final Person person) {
        int id = ((person.id))++;
        return id;
    }

    public int returnPostfixQName(final Foo foo, final Person person) {
        return ((person.id))--;
    }

    public int assignPrefixQName(final Foo foo, final Person person) {
        int id = ++((person.id));
        return id;
    }

    public int returnPrefixQName(final Foo foo, final Person person) {
        return --((person.id));
    }

    public Person assignQNameInCreation(final Person person) {
        Person clone = new Person(((person.id)));
        return clone;
    }

    public Person returnQNameInCreation(final Person person) {
        return new Person(((person.id)));
    }

    public String[] assignQNameInArrayCreation(final Person person) {
        String[] names = new String[((person.id))];
        return names;
    }

    public String[] returnQNameInArrayCreation(final Person person) {
        return new String[((person.id))];
    }

    public String assignQNameInArrayAccess(final String[] names,
            final Person person) {
        String name = names[((person.id))];
        return name;
    }

    public String returnQNameInArrayAccess(final String[] names,
            final Person person) {
        return names[((person.id))];
    }
}
