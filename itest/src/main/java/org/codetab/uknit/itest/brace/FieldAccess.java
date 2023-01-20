package org.codetab.uknit.itest.brace;

import org.codetab.uknit.itest.brace.Model.Foo;
import org.codetab.uknit.itest.brace.Model.Person;

/**
 * In JDT AST, (person).id is QualifiedName and (person).id is FieldAccess.
 *
 * @author Maithilish
 *
 */
class FieldAccess {

    public int assignFieldAccess(final Person person) {
        int id = ((person).id);
        return id;
    }

    public int returnFieldAccess(final Person person) {
        return ((person).id);
    }

    public String assignFieldAccessInInvokeArg(final Foo foo,
            final Person person) {
        String name = foo.get(((person).id));
        return name;
    }

    public String returnFieldAccessInInvokeArg(final Foo foo,
            final Person person) {
        return foo.get(((person).id));
    }

    public String assignFieldAccessInInvokeExp(final Foo foo,
            final Person person) {
        String home = ((person.contacts).getHome());
        return home;
    }

    public String returnFieldAccessInInvokeExp(final Foo foo,
            final Person person) {
        return (((person.contacts).getHome()));
    }

    public int assignInfixFieldAccess(final Foo foo, final Person person1,
            final Person person2) {
        int id = ((person1).id) + ((person2).id);
        return id;
    }

    public int returnInfixFieldAccess(final Foo foo, final Person person1,
            final Person person2) {
        return (((person1).id)) + (((person2).id));
    }

    public int assignConditionalFieldAccess(final Foo foo,
            final Person person) {
        int id = ((person).id) > 1 ? 1 : 0;
        return id;
    }

    public int returnConditionalFieldAccess(final Foo foo,
            final Person person) {
        return (((person).id)) > 1 ? 1 : 0;
    }

    public int assignPostfixFieldAccess(final Foo foo, final Person person) {
        int id = ((person).id++);
        return id;
    }

    public int returnPostfixFieldAccess(final Foo foo, final Person person) {
        return (((person).id--));
    }

    public int assignPrefixFieldAccess(final Foo foo, final Person person) {
        int id = (++(person).id);
        return id;
    }

    public int returnPrefixFieldAccess(final Foo foo, final Person person) {
        return ((--(person).id));
    }

    public Person assignFieldAccessInCreation(final Person person) {
        Person clone = new Person(((person).id));
        return clone;
    }

    public Person returnFieldAccessInCreation(final Person person) {
        return new Person((((person).id)));
    }

    public String[] assignFieldAccessInArrayCreation(final Person person) {
        String[] names = new String[((person).id)];
        return names;
    }

    public String[] returnFieldAccessInArrayCreation(final Person person) {
        return new String[(((person).id))];
    }

    public String assignFieldAccessInArrayAccess(final String[] names,
            final Person person) {
        String name = names[((person).id)];
        return name;
    }

    public String returnFieldAccessInArrayAccess(final String[] names,
            final Person person) {
        return names[(((person).id))];
    }
}
