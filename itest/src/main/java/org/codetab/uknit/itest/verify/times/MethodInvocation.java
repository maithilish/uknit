package org.codetab.uknit.itest.verify.times;

import org.codetab.uknit.itest.verify.times.Model.Foo;
import org.codetab.uknit.itest.verify.times.Model.Person;

/**
 * In all the tests we testing invoke arg of invoke. Ex: Testing the merge of
 * invoke foo.append(Foo.valueOf(city)), for various types of exp in arg of
 * invoke Foo.valueOf(...).
 *
 * @author Maithilish
 *
 */
class MethodInvocation {

    int id = 2;
    int lid = 3;

    // invoke arg is array access
    public void arrayAccess(final Foo foo) {
        String[] cities = {"foo", "foo", "baz"};
        foo.append(Foo.valueOf(cities[0]));
        foo.append(Foo.valueOf(cities[1]));
        foo.append(Foo.valueOf(cities[2]));
    }

    public void arrayAccessVar(final Foo foo) {
        String[] cities = {"foo", "foo", "baz"};
        String a = cities[0];
        String b = cities[1];
        String c = cities[2];
        foo.append(Foo.valueOf(a));
        foo.append(Foo.valueOf(b));
        foo.append(Foo.valueOf(c));
    }

    public void arrayAccessIndexIsExp(final Foo foo) {
        String[] cities = {"foo", "bar", "baz"};
        foo.append(cities[foo.index()]);
        foo.append(cities[foo.index()]);
        foo.append(cities[foo.index()]);
    }

    // invoke arg is array creation
    public void arrayCreation(final Foo foo) {
        foo.append(Foo.valueOf(new String[1]));
        foo.append(Foo.valueOf(new String[1]));
        foo.append(Foo.valueOf(new String[2]));
    }

    public void arrayCreationVar(final Foo foo) {
        int a = 1;
        int b = 1;
        int c = 2;
        foo.append(Foo.valueOf(new String[a]));
        foo.append(Foo.valueOf(new String[b]));
        foo.append(Foo.valueOf(new String[c]));
    }

    // invoke arg is array creation
    public void arrayCreationInitialized(final Foo foo) {
        foo.append(Foo.valueOf(new String[] {"foo", "bar"}));
        foo.append(Foo.valueOf(new String[] {"foo", "bar"}));
        foo.append(Foo.valueOf(new String[] {"foo", "bar", "baz"}));
    }

    public void arrayCreationInitializedVar(final Foo foo) {
        String a = "foo";
        String b = "bar";
        String c = "baz";
        foo.append(Foo.valueOf(new String[] {a, b}));
        foo.append(Foo.valueOf(new String[] {a, b}));
        foo.append(Foo.valueOf(new String[] {a, b, c}));
    }

    // invoke arg is initializer
    public void arrayInitializer(final Foo foo) {
        String[] cities = {"foo", "bar"};
        String[] states = {"foo", "bar"};
        String[] countries = {"bar", "baz"};

        foo.append(Foo.valueOf(cities));
        foo.append(Foo.valueOf(states));
        foo.append(Foo.valueOf(countries));
    }

    public void arrayInitializerVar(final Foo foo) {
        String a = "foo";
        String b = "bar";
        String c = "baz";
        String[] cities = {a, b};
        String[] states = {a, b};
        String[] countries = {b, c};

        foo.append(Foo.valueOf(cities));
        foo.append(Foo.valueOf(states));
        foo.append(Foo.valueOf(countries));
    }

    // invoke arg is boolean literal
    public void booleanLiteral(final Foo foo) {
        foo.append(Foo.valueOf(true));
        foo.append(Foo.valueOf(true));
        foo.append(Foo.valueOf(false));
    }

    public void booleanLiteralVar(final Foo foo) {
        boolean a = true;
        boolean b = true;
        boolean c = false;

        foo.append(Foo.valueOf(a));
        foo.append(Foo.valueOf(b));
        foo.append(Foo.valueOf(c));
    }

    // invoke arg is cast exp
    public void cast(final Foo foo) {
        foo.append(Foo.valueOf((Object) "foo"));
        foo.append(Foo.valueOf((Object) "foo"));
        foo.append(Foo.valueOf((Object) "bar"));
    }

    public void castVar(final Foo foo) {
        Object city = "foo";
        Object state = "foo";
        Object country = "bar";
        foo.append(Foo.valueOf((String) city));
        foo.append(Foo.valueOf((String) state));
        foo.append(Foo.valueOf((String) country));
    }

    // invoke arg is char literal
    public void charLiteral(final Foo foo) {
        foo.append(Foo.valueOf('f'));
        foo.append(Foo.valueOf('f'));
        foo.append(Foo.valueOf('b'));
    }

    public void charLiteralVar(final Foo foo) {
        char a = 'f';
        char b = 'f';
        char c = 'b';
        foo.append(Foo.valueOf(a));
        foo.append(Foo.valueOf(b));
        foo.append(Foo.valueOf(c));
    }

    // invoke arg is instance creation
    public void classInstanceCreation(final Foo foo) {
        foo.append(Foo.valueOf(new String("foo")));
        foo.append(Foo.valueOf(new String("foo")));
        foo.append(Foo.valueOf(new String("bar")));
    }

    public void classInstanceCreationVar(final Foo foo) {
        String city = "foo";
        String state = "foo";
        String country = "bar";
        foo.append(Foo.valueOf(new String(city)));
        foo.append(Foo.valueOf(new String(state)));
        foo.append(Foo.valueOf(new String(country)));
    }

    /*
     * Invoke arg is conditional exp. The Foo.valueOf("foo") is called 4 times
     * and Foo.valueOf("bar") one time based on runtime value returned by
     * conditional exp. But, uKnit merges based on exp and not on runtime
     * resolved value.
     */
    public void conditional(final Foo foo) {
        boolean a = true;
        boolean b = false;
        foo.append(Foo.valueOf(a ? "foo" : "foo"));
        foo.append(Foo.valueOf(a ? "foo" : "foo"));
        foo.append(Foo.valueOf(a ? "foo" : "bar"));
        foo.append(Foo.valueOf(b ? "foo" : "foo"));
        foo.append(Foo.valueOf(b ? "foo" : "foo"));
        foo.append(Foo.valueOf(b ? "foo" : "bar"));
    }

    public void conditionalVar(final Foo foo) {
        boolean a = true;
        boolean b = false;
        String city = "foo";
        String state = "foo";
        String country = "bar";
        foo.append(Foo.valueOf(a ? city : state));
        foo.append(Foo.valueOf(a ? city : state));
        foo.append(Foo.valueOf(a ? state : country));
        foo.append(Foo.valueOf(b ? city : state));
        foo.append(Foo.valueOf(b ? city : state));
        foo.append(Foo.valueOf(b ? state : country));
    }

    // invoke arg is field access - (bar).id
    public void fieldaccess(final Foo foo, final Person person) {
        foo.append(Foo.valueOf((person).id));
        foo.append(Foo.valueOf((person).lid));
        foo.append(Foo.valueOf((person).id));
    }

    /**
     * TODO - if pid is changed to id then it hides field id of this class.
     * Initializer is set to id value 2 instead of person.id. Same happens to
     * plid. Rectify the error.
     *
     * @param foo
     * @param person
     */
    public void fieldaccessVar(final Foo foo, final Person person) {
        int pid = (person).id;
        long plid = (person).lid;
        foo.append(Foo.valueOf(pid));
        foo.append(Foo.valueOf(plid));
        foo.append(Foo.valueOf(pid));
    }

    // invoke arg is infix
    public void infix(final Foo foo, final Person person) {
        foo.append(Foo.valueOf(person.id == person.lid));
        foo.append(Foo.valueOf(person.id > person.lid));
        foo.append(Foo.valueOf(person.id == person.lid));
    }

    public void infixVar(final Foo foo, final Person person) {
        boolean a = person.id == person.lid;
        boolean b = person.id > person.lid;
        boolean c = person.id == person.lid;
        foo.append(Foo.valueOf(a));
        foo.append(Foo.valueOf(b));
        foo.append(Foo.valueOf(c));
    }

    /*
     * Invoke arg is instance of. Merges without resolving runtime value, but
     * all three instanceof resolves to false.
     */
    public void instanceOf(final Foo foo, final Object person) {
        foo.append(Foo.valueOf(person instanceof Person));
        foo.append(Foo.valueOf(person instanceof Person));
        foo.append(Foo.valueOf(person instanceof String));
    }

    /*
     * STEPIN - for person create mock of Person not Object.
     */
    public void instanceOfVar(final Foo foo, final Object person) {
        boolean a = person instanceof Person;
        boolean b = person instanceof Person;
        boolean c = person instanceof String;
        foo.append(Foo.valueOf(a));
        foo.append(Foo.valueOf(b));
        foo.append(Foo.valueOf(c));
    }

    // invoke arg is method invocation
    public void invoke(final Foo foo) {
        foo.append(Foo.valueOf(Foo.valueOf("foo")));
        foo.append(Foo.valueOf(Foo.valueOf("foo")));
        foo.append(Foo.valueOf(Foo.valueOf("baz")));
    }

    public void invokeVar(final Foo foo) {
        String city = "foo";
        String state = "foo";
        String country = "baz";
        foo.append(Foo.valueOf(Foo.valueOf(city)));
        foo.append(Foo.valueOf(Foo.valueOf(state)));
        foo.append(Foo.valueOf(Foo.valueOf(country)));
    }

    // invoke arg is null literal
    public void nullLiteral(final Foo foo) {
        foo.append(Foo.valueOf(null));
        foo.append(Foo.valueOf(null));
        foo.append(Foo.valueOf("foo"));
    }

    public void nullLiteralVar(final Foo foo) {
        String a = null;
        String b = null;
        String c = "foo";
        foo.append(Foo.valueOf(a));
        foo.append(Foo.valueOf(b));
        foo.append(Foo.valueOf(c));
    }

    // invoke arg is number literal
    public void numberLiteral(final Foo foo) {
        foo.append(Foo.valueOf(1));
        foo.append(Foo.valueOf(1));
        foo.append(Foo.valueOf(2));
    }

    public void numberLiteralVar(final Foo foo) {
        int a = 1;
        int b = 1;
        int c = 2;
        foo.append(Foo.valueOf(a));
        foo.append(Foo.valueOf(b));
        foo.append(Foo.valueOf(c));
    }

    // Invoke arg is parenthesized exp.
    public void parenthesized(final Foo foo) {
        foo.append(Foo.valueOf("foo"));
        foo.append(Foo.valueOf(("foo")));
        foo.append(Foo.valueOf((("foo"))));
    }

    public void parenthesizedVar(final Foo foo) {
        String a = "foo";
        String b = ("foo");
        String c = (("foo"));
        foo.append(Foo.valueOf(a));
        foo.append(Foo.valueOf((b)));
        foo.append(Foo.valueOf(((c))));
    }

    /*
     * Invoke arg is postfix. Merges A and C, but verify fails as mockito
     * internally applies -- before second verify. User has unmerge the verify.
     * Mockito treats C as called twice because A has incremented the
     * indexes[0].
     */
    public void postfix(final Foo foo) {
        int[] indexes = {1, 2};
        foo.append(Foo.valueOf(indexes[0]++)); // A
        foo.append(Foo.valueOf(indexes[1]++)); // B
        foo.append(Foo.valueOf(indexes[0]++)); // C
    }

    public void postfixVar(final Foo foo, final Person person) {
        int[] indexes = {1, 2};
        int a = indexes[0];
        int b = indexes[1];
        int c = indexes[0];
        foo.append(Foo.valueOf(a++));
        foo.append(Foo.valueOf(b++));
        foo.append(Foo.valueOf(c++));
    }

    /*
     * Invoke arg is prefix. Merges A and B, but verify fails as mockito
     * internally applies -- before second verify. User has unmerge the verify.
     */
    public void prefix(final Foo foo) {
        int[] indexes = {1, 2};
        foo.append(Foo.valueOf(--indexes[0])); // A
        foo.append(Foo.valueOf(--indexes[1]));
        foo.append(Foo.valueOf(--indexes[0])); // B
    }

    public void prefixVar(final Foo foo, final Person person) {
        int[] indexes = {1, 2};
        int a = indexes[0];
        int b = indexes[1];
        int c = indexes[0];
        foo.append(Foo.valueOf(--a));
        foo.append(Foo.valueOf(++b));
        foo.append(Foo.valueOf(--c));
    }

    /*
     * Invoke arg is simple name i.e. var. The runtime value returned by
     * Foo.valueOf(city)) is "foo" and stmt A and B are same. As uKnit can't
     * resolve runtime values, A, B and C verifies are not merged.
     */
    public void var(final Foo foo) {
        String city = "foo";
        String state = "foo";
        String country = "bar";
        foo.append(city); // A
        foo.append(Foo.valueOf(city)); // B
        foo.append(Foo.valueOf(country));
        foo.append(Foo.valueOf(state)); // C
    }

    /*
     * Invoke arg is String literal. The runtime value returned by
     * Foo.valueOf(city)) is "foo" and stmt A and B are same. As uKnit can't
     * resolve runtime values, A, B and C verifies are not merged.
     */
    public void literal(final Foo foo) {
        foo.append("foo"); // A
        foo.append(Foo.valueOf("foo")); // B
        foo.append(Foo.valueOf("bar"));
        foo.append(Foo.valueOf("foo")); // C
    }

    // invoke arg is this exp
    public void thisExp(final Foo foo, final Person person) {
        foo.append(Foo.valueOf(this.id));
        foo.append(Foo.valueOf(this.lid));
        foo.append(Foo.valueOf(this.id));
    }

    public void thisExpVar(final Foo foo, final Person person) {
        int a = this.id;
        int b = this.lid;
        foo.append(Foo.valueOf(a));
        foo.append(Foo.valueOf(b));
        foo.append(Foo.valueOf(a));
    }

    // invoke arg is type literal
    public void typeLiteral(final Foo foo, final Person person) {
        foo.append(Foo.valueOf((Person.class)));
        foo.append(Foo.valueOf(String.class));
        foo.append(Foo.valueOf(Person.class));
    }

    public void typeLiteralVar(final Foo foo, final Person person) {
        Class<?> a = (Person.class);
        Class<?> b = String.class;
        Class<?> c = Person.class;
        foo.append(Foo.valueOf(a));
        foo.append(Foo.valueOf(b));
        foo.append(Foo.valueOf(c));
    }

}
