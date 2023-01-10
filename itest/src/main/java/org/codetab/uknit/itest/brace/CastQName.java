package org.codetab.uknit.itest.brace;

import org.codetab.uknit.itest.brace.Model.Foo;
import org.codetab.uknit.itest.brace.Model.Person;

public class CastQName {

    public int assignQName(final Person person) {
        int lid = (int) ((person.lid));
        return lid;
    }

    // TODO N - cast is not set in initializer
    public int returnQName(final Person person) {
        return (int) ((person.lid));
    }

    public String assignQNameInInvokeArg(final Foo foo, final Person person) {
        String name = foo.get((int) ((person.lid)));
        return name;
    }

    public String returnQNameInInvokeArg(final Foo foo, final Person person) {
        return foo.get((int) ((person.lid)));
    }

    public int assignInfixQName(final Foo foo, final Person person1,
            final Person person2) {
        int lid = (int) ((person1.lid) + (person2.lid));
        return lid;
    }

    public int returnInfixQName(final Foo foo, final Person person1,
            final Person person2) {
        return (int) ((person1.lid) + (person2.lid));
    }

    public int assignConditionalQName(final Foo foo, final Person person) {
        int lid = (int) ((person.lid)) > 1 ? 1 : 0;
        return lid;
    }

    public int returnConditionalQName(final Foo foo, final Person person) {
        return (int) ((person.lid)) > 1 ? 1 : 0;
    }

    public int assignPostfixQName(final Foo foo, final Person person) {
        int lid = (int) ((person.lid))++;
        return lid;
    }

    public int returnPostfixQName(final Foo foo, final Person person) {
        return (int) ((person.lid))--;
    }

    public int assignPrefixQName(final Foo foo, final Person person) {
        int lid = (int) ++((person.lid));
        return lid;
    }

    public int returnPrefixQName(final Foo foo, final Person person) {
        return (int) --((person.lid));
    }

    public Person assignQNameInCreation(final Person person) {
        Person clone = new Person((int) ((person.lid)));
        return clone;
    }

    public Person returnQNameInCreation(final Person person) {
        return new Person((int) ((person.lid)));
    }

    public String[] assignQNameInArrayCreation(final Person person) {
        String[] names = new String[(int) ((person.lid))];
        return names;
    }

    public String[] returnQNameInArrayCreation(final Person person) {
        return new String[(int) ((person.lid))];
    }

    public String assignQNameInArrayAccess(final String[] names,
            final Person person) {
        String name = names[(int) ((person.lid))];
        return name;
    }

    public String returnQNameInArrayAccess(final String[] names,
            final Person person) {
        return names[(int) ((person.lid))];
    }
}
