package org.codetab.uknit.itest.verify.times;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.verify.times.Model.Foo;
import org.codetab.uknit.itest.verify.times.Model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class MethodInvocationTest {
    @InjectMocks
    private MethodInvocation methodInvocation;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testArrayAccess() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "foo";
        // String orange = "foo";
        String mango = "baz";
        methodInvocation.arrayAccess(foo);

        verify(foo, times(2)).append(Foo.valueOf(apple));
        verify(foo).append(Foo.valueOf(mango));
    }

    @Test
    public void testArrayAccessVar() {
        Foo foo = Mockito.mock(Foo.class);
        String a = "foo";
        // String b = "foo";
        String c = "baz";
        methodInvocation.arrayAccessVar(foo);

        verify(foo, times(2)).append(Foo.valueOf(a));
        verify(foo).append(Foo.valueOf(c));
    }

    @Test
    public void testArrayAccessIndexIsExp() {
        Foo foo = Mockito.mock(Foo.class);
        int apple = 1;
        String grape = "bar";
        int orange = 1;
        // String kiwi = "bar";
        int mango = 1;
        // String banana = "bar";

        when(foo.index()).thenReturn(apple).thenReturn(orange)
                .thenReturn(mango);
        methodInvocation.arrayAccessIndexIsExp(foo);

        verify(foo, times(3)).append(grape);
    }

    @Test
    public void testArrayCreation() {
        Foo foo = Mockito.mock(Foo.class);
        methodInvocation.arrayCreation(foo);

        verify(foo, times(2)).append(Foo.valueOf(new String[1]));
        verify(foo).append(Foo.valueOf(new String[2]));
    }

    @Test
    public void testArrayCreationVar() {
        Foo foo = Mockito.mock(Foo.class);
        int a = 1;
        // int b = 1;
        int c = 2;
        methodInvocation.arrayCreationVar(foo);

        verify(foo, times(2)).append(Foo.valueOf(new String[a]));
        verify(foo).append(Foo.valueOf(new String[c]));
    }

    @Test
    public void testArrayCreationInitialized() {
        Foo foo = Mockito.mock(Foo.class);
        methodInvocation.arrayCreationInitialized(foo);

        verify(foo, times(2)).append(Foo.valueOf(new String[] {"foo", "bar"}));
        verify(foo).append(Foo.valueOf(new String[] {"foo", "bar", "baz"}));
    }

    @Test
    public void testArrayCreationInitializedVar() {
        Foo foo = Mockito.mock(Foo.class);
        String a = "foo";
        String b = "bar";
        String c = "baz";
        methodInvocation.arrayCreationInitializedVar(foo);

        verify(foo, times(2)).append(Foo.valueOf(new String[] {a, b}));
        verify(foo).append(Foo.valueOf(new String[] {a, b, c}));
    }

    @Test
    public void testArrayInitializer() {
        Foo foo = Mockito.mock(Foo.class);
        String[] cities = {"foo", "bar"};
        // String[] states = {"foo", "bar"};
        String[] countries = {"bar", "baz"};
        methodInvocation.arrayInitializer(foo);

        verify(foo, times(2)).append(Foo.valueOf(cities));
        verify(foo).append(Foo.valueOf(countries));
    }

    @Test
    public void testArrayInitializerVar() {
        Foo foo = Mockito.mock(Foo.class);
        String a = "foo";
        String b = "bar";
        String c = "baz";
        String[] cities = {a, b};
        // String[] states = {a, b};
        String[] countries = {b, c};
        methodInvocation.arrayInitializerVar(foo);

        verify(foo, times(2)).append(Foo.valueOf(cities));
        verify(foo).append(Foo.valueOf(countries));
    }

    @Test
    public void testBooleanLiteral() {
        Foo foo = Mockito.mock(Foo.class);
        methodInvocation.booleanLiteral(foo);

        verify(foo, times(2)).append(Foo.valueOf(true));
        verify(foo).append(Foo.valueOf(false));
    }

    @Test
    public void testBooleanLiteralVar() {
        Foo foo = Mockito.mock(Foo.class);
        boolean a = true;
        // boolean b = true;
        boolean c = false;
        methodInvocation.booleanLiteralVar(foo);

        verify(foo, times(2)).append(Foo.valueOf(a));
        verify(foo).append(Foo.valueOf(c));
    }

    @Test
    public void testCast() {
        Foo foo = Mockito.mock(Foo.class);
        methodInvocation.cast(foo);

        verify(foo, times(2)).append(Foo.valueOf((Object) "foo"));
        verify(foo).append(Foo.valueOf((Object) "bar"));
    }

    @Test
    public void testCastVar() {
        Foo foo = Mockito.mock(Foo.class);
        String city = "foo";
        // String state = "foo";
        String country = "bar";
        methodInvocation.castVar(foo);

        verify(foo, times(2)).append(Foo.valueOf(city));
        verify(foo).append(Foo.valueOf(country));
    }

    @Test
    public void testCharLiteral() {
        Foo foo = Mockito.mock(Foo.class);
        methodInvocation.charLiteral(foo);

        verify(foo, times(2)).append(Foo.valueOf('f'));
        verify(foo).append(Foo.valueOf('b'));
    }

    @Test
    public void testCharLiteralVar() {
        Foo foo = Mockito.mock(Foo.class);
        char a = 'f';
        // char b = 'f';
        char c = 'b';
        methodInvocation.charLiteralVar(foo);

        verify(foo, times(2)).append(Foo.valueOf(a));
        verify(foo).append(Foo.valueOf(c));
    }

    @Test
    public void testClassInstanceCreation() {
        Foo foo = Mockito.mock(Foo.class);
        methodInvocation.classInstanceCreation(foo);

        verify(foo, times(2)).append(Foo.valueOf(new String("foo")));
        verify(foo).append(Foo.valueOf(new String("bar")));
    }

    @Test
    public void testClassInstanceCreationVar() {
        Foo foo = Mockito.mock(Foo.class);
        String city = "foo";
        // String state = "foo";
        String country = "bar";
        methodInvocation.classInstanceCreationVar(foo);

        verify(foo, times(2)).append(Foo.valueOf(new String(city)));
        verify(foo).append(Foo.valueOf(new String(country)));
    }

    @Test
    public void testConditional() {
        Foo foo = Mockito.mock(Foo.class);
        // boolean a = true;
        // boolean b = false;
        methodInvocation.conditional(foo);

        // verify(foo, times(2)).append(Foo.valueOf(a ? "foo" : "foo"));
        // verify(foo).append(Foo.valueOf(a ? "foo" : "bar"));
        // verify(foo, times(2)).append(Foo.valueOf(b ? "foo" : "foo"));
        verify(foo, times(5)).append(Foo.valueOf("foo"));
        verify(foo).append(Foo.valueOf("bar"));
    }

    @Test
    public void testConditionalVar() {
        Foo foo = Mockito.mock(Foo.class);
        // boolean a = true;
        // boolean b = false;
        String city = "foo";
        // String state = "foo";
        String country = "bar";
        methodInvocation.conditionalVar(foo);

        // verify(foo, times(2)).append(Foo.valueOf(a ? city : state));
        // verify(foo).append(Foo.valueOf(a ? state : country));
        // verify(foo, times(2)).append(Foo.valueOf(b ? city : state));
        // verify(foo).append(Foo.valueOf(b ? state : country));
        verify(foo, times(5)).append(Foo.valueOf(city));
        verify(foo).append(Foo.valueOf(country));
    }

    @Test
    public void testFieldaccess() {
        Foo foo = Mockito.mock(Foo.class);
        Person person = Mockito.mock(Person.class);
        // int lid = 3;
        // int id = 2;
        methodInvocation.fieldaccess(foo, person);

        verify(foo, times(2)).append(Foo.valueOf((person).id));
        verify(foo).append(Foo.valueOf((person).lid));
    }

    @Test
    public void testFieldaccessVar() {
        Foo foo = Mockito.mock(Foo.class);
        Person person = Mockito.mock(Person.class);
        int pid = (person).id;
        long plid = (person).lid;
        methodInvocation.fieldaccessVar(foo, person);

        verify(foo, times(2)).append(Foo.valueOf(pid));
        verify(foo).append(Foo.valueOf(plid));
    }

    @Test
    public void testInfix() {
        Foo foo = Mockito.mock(Foo.class);
        Person person = Mockito.mock(Person.class);
        // int lid = 3;
        // int id = 2;
        methodInvocation.infix(foo, person);

        verify(foo, times(2)).append(Foo.valueOf(person.id == person.lid));
        verify(foo).append(Foo.valueOf(person.id > person.lid));
    }

    @Test
    public void testInfixVar() {
        Foo foo = Mockito.mock(Foo.class);
        Person person = Mockito.mock(Person.class);
        boolean a = person.id == person.lid;
        boolean b = person.id > person.lid;
        /// boolean c = person.id == person.lid;
        methodInvocation.infixVar(foo, person);

        verify(foo, times(2)).append(Foo.valueOf(a));
        verify(foo).append(Foo.valueOf(b));
    }

    @Test
    public void testInstanceOf() {
        Foo foo = Mockito.mock(Foo.class);
        Object person = Mockito.mock(Object.class);
        methodInvocation.instanceOf(foo, person);

        // verify(foo, times(2)).append(Foo.valueOf(person instanceof Person));
        // verify(foo).append(Foo.valueOf(person instanceof String));
        verify(foo, times(3)).append(Foo.valueOf(person instanceof Person));
    }

    @Test
    public void testInstanceOfVar() {
        Foo foo = Mockito.mock(Foo.class);
        Object person = Mockito.mock(Person.class);
        boolean a = person instanceof Person;
        // boolean b = person instanceof Person;
        boolean c = person instanceof String;
        methodInvocation.instanceOfVar(foo, person);

        verify(foo, times(2)).append(Foo.valueOf(a));
        verify(foo).append(Foo.valueOf(c));
    }

    @Test
    public void testInvoke() {
        Foo foo = Mockito.mock(Foo.class);
        methodInvocation.invoke(foo);

        verify(foo, times(2)).append(Foo.valueOf(Foo.valueOf("foo")));
        verify(foo).append(Foo.valueOf(Foo.valueOf("baz")));
    }

    @Test
    public void testInvokeVar() {
        Foo foo = Mockito.mock(Foo.class);
        String city = "foo";
        // String state = "foo";
        String country = "baz";
        methodInvocation.invokeVar(foo);

        verify(foo, times(2)).append(Foo.valueOf(Foo.valueOf(city)));
        verify(foo).append(Foo.valueOf(Foo.valueOf(country)));
    }

    @Test
    public void testNullLiteral() {
        Foo foo = Mockito.mock(Foo.class);
        methodInvocation.nullLiteral(foo);

        verify(foo, times(2)).append(Foo.valueOf(null));
        verify(foo).append(Foo.valueOf("foo"));
    }

    @Test
    public void testNullLiteralVar() {
        Foo foo = Mockito.mock(Foo.class);
        String a = null;
        // String b = null;
        String c = "foo";
        methodInvocation.nullLiteralVar(foo);

        verify(foo, times(2)).append(Foo.valueOf(a));
        verify(foo).append(Foo.valueOf(c));
    }

    @Test
    public void testNumberLiteral() {
        Foo foo = Mockito.mock(Foo.class);
        methodInvocation.numberLiteral(foo);

        verify(foo, times(2)).append(Foo.valueOf(1));
        verify(foo).append(Foo.valueOf(2));
    }

    @Test
    public void testNumberLiteralVar() {
        Foo foo = Mockito.mock(Foo.class);
        int a = 1;
        // int b = 1;
        int c = 2;
        methodInvocation.numberLiteralVar(foo);

        verify(foo, times(2)).append(Foo.valueOf(a));
        verify(foo).append(Foo.valueOf(c));
    }

    @Test
    public void testParenthesized() {
        Foo foo = Mockito.mock(Foo.class);
        methodInvocation.parenthesized(foo);

        verify(foo, times(3)).append(Foo.valueOf("foo"));
    }

    @Test
    public void testParenthesizedVar() {
        Foo foo = Mockito.mock(Foo.class);
        String a = "foo";
        // String b = "foo";
        // String c = "foo";
        methodInvocation.parenthesizedVar(foo);

        verify(foo, times(3)).append(Foo.valueOf(a));
    }

    @Test
    public void testPostfix() {
        Foo foo = Mockito.mock(Foo.class);
        int apple = 1;
        int orange = 2;
        int mango = 1;
        methodInvocation.postfix(foo);

        // verify(foo, times(2)).append(Foo.valueOf(apple++));
        verify(foo).append(Foo.valueOf(apple++));
        verify(foo).append(Foo.valueOf(mango++));
        verify(foo, times(2)).append(Foo.valueOf(orange++));
    }

    @Test
    public void testPostfixVar() {
        Foo foo = Mockito.mock(Foo.class);
        Person person = Mockito.mock(Person.class);
        int a = 1;
        int b = 2;
        // int c = 1;
        methodInvocation.postfixVar(foo, person);

        verify(foo, times(2)).append(Foo.valueOf(a++));
        verify(foo).append(Foo.valueOf(b++));
    }

    @Test
    public void testPrefix() {
        Foo foo = Mockito.mock(Foo.class);
        int apple = 1;
        int orange = 2;
        int mango = 1;
        methodInvocation.prefix(foo);

        // verify(foo, times(2)).append(Foo.valueOf(--apple));
        verify(foo).append(Foo.valueOf(--apple));
        verify(foo).append(Foo.valueOf(--mango));
        verify(foo).append(Foo.valueOf(--orange));
    }

    @Test
    public void testPrefixVar() {
        Foo foo = Mockito.mock(Foo.class);
        Person person = Mockito.mock(Person.class);
        int a = 1;
        int b = 2;
        // int c = 1;
        methodInvocation.prefixVar(foo, person);

        verify(foo, times(2)).append(Foo.valueOf(--a));
        verify(foo).append(Foo.valueOf(++b));
    }

    @Test
    public void testVar() {
        Foo foo = Mockito.mock(Foo.class);
        String city = "foo";
        // String state = "foo";
        String country = "bar";
        methodInvocation.var(foo);

        // verify(foo).append(city);
        // verify(foo, times(2)).append(Foo.valueOf(city));
        verify(foo, times(3)).append(city);
        verify(foo).append(Foo.valueOf(country));
    }

    @Test
    public void testLiteral() {
        Foo foo = Mockito.mock(Foo.class);
        methodInvocation.literal(foo);

        // verify(foo).append("foo");
        // verify(foo, times(2)).append(Foo.valueOf("foo"));
        verify(foo, times(3)).append("foo");
        verify(foo).append(Foo.valueOf("bar"));
    }

    @Test
    public void testThisExp() {
        Foo foo = Mockito.mock(Foo.class);
        Person person = Mockito.mock(Person.class);
        // int lid = 3;
        // int id = 2;
        methodInvocation.thisExp(foo, person);

        verify(foo, times(2)).append(Foo.valueOf(methodInvocation.id));
        verify(foo).append(Foo.valueOf(methodInvocation.lid));
    }

    @Test
    public void testThisExpVar() {
        Foo foo = Mockito.mock(Foo.class);
        Person person = Mockito.mock(Person.class);
        int a = methodInvocation.id;
        int b = methodInvocation.lid;
        methodInvocation.thisExpVar(foo, person);

        verify(foo, times(2)).append(Foo.valueOf(a));
        verify(foo).append(Foo.valueOf(b));
    }

    @Test
    public void testTypeLiteral() {
        Foo foo = Mockito.mock(Foo.class);
        Person person = Mockito.mock(Person.class);
        methodInvocation.typeLiteral(foo, person);

        verify(foo, times(2)).append(Foo.valueOf(Person.class));
        verify(foo).append(Foo.valueOf(String.class));
    }

    @Test
    public void testTypeLiteralVar() {
        Foo foo = Mockito.mock(Foo.class);
        Person person = Mockito.mock(Person.class);
        Class<?> a = Person.class;
        Class<?> b = String.class;
        // Class<?> c = Person.class;
        methodInvocation.typeLiteralVar(foo, person);

        verify(foo, times(2)).append(Foo.valueOf(a));
        verify(foo).append(Foo.valueOf(b));
    }
}
