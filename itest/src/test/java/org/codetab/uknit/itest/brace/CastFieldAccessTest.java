package org.codetab.uknit.itest.brace;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.brace.Model.Foo;
import org.codetab.uknit.itest.brace.Model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class CastFieldAccessTest {
    @InjectMocks
    private CastFieldAccess castFieldAccess;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAssignQName() {
        Person person = Mockito.mock(Person.class);
        int lid = (int) ((person).lid);

        int actual = castFieldAccess.assignQName(person);

        assertEquals(lid, actual);
    }

    @Test
    public void testReturnQName() {
        Person person = Mockito.mock(Person.class);
        int apple = (int) (((person).lid));

        int actual = castFieldAccess.returnQName(person);

        assertEquals(apple, actual);
    }

    @Test
    public void testAssignQNameInInvokeArg() {
        Foo foo = Mockito.mock(Foo.class);
        Person person = Mockito.mock(Person.class);
        String name = "Foo";

        when(foo.get((int) ((person).lid))).thenReturn(name);

        String actual = castFieldAccess.assignQNameInInvokeArg(foo, person);

        assertEquals(name, actual);
    }

    @Test
    public void testReturnQNameInInvokeArg() {
        Foo foo = Mockito.mock(Foo.class);
        Person person = Mockito.mock(Person.class);
        String apple = "Foo";

        when(foo.get((int) (((person).lid)))).thenReturn(apple);

        String actual = castFieldAccess.returnQNameInInvokeArg(foo, person);

        assertEquals(apple, actual);
    }

    @Test
    public void testAssignInfixQName() {
        Foo foo = Mockito.mock(Foo.class);
        Person person1 = Mockito.mock(Person.class);
        Person person2 = Mockito.mock(Person.class);
        int lid = (int) (((person1).lid) + ((person2).lid));

        int actual = castFieldAccess.assignInfixQName(foo, person1, person2);

        assertEquals(lid, actual);
    }

    @Test
    public void testReturnInfixQName() {
        Foo foo = Mockito.mock(Foo.class);
        Person person1 = Mockito.mock(Person.class);
        Person person2 = Mockito.mock(Person.class);
        int apple = (int) ((((person1).lid) + (((person2).lid))));

        int actual = castFieldAccess.returnInfixQName(foo, person1, person2);

        assertEquals(apple, actual);
    }

    @Test
    public void testAssignConditionalQName() {
        Foo foo = Mockito.mock(Foo.class);
        Person person = Mockito.mock(Person.class);
        int lid = (int) ((person).lid) > 1 ? 1 : 0;

        int actual = castFieldAccess.assignConditionalQName(foo, person);

        assertEquals(lid, actual);
    }

    @Test
    public void testReturnConditionalQName() {
        Foo foo = Mockito.mock(Foo.class);
        Person person = Mockito.mock(Person.class);
        int apple = ((person).lid) > 1 ? 1 : 0;

        int actual = castFieldAccess.returnConditionalQName(foo, person);

        assertEquals(apple, actual);
    }

    @Test
    public void testAssignPostfixQName() {
        Foo foo = Mockito.mock(Foo.class);
        Person person = Mockito.mock(Person.class);
        int lid = 0;

        int actual = castFieldAccess.assignPostfixQName(foo, person);

        assertEquals(lid, actual);
    }

    @Test
    public void testReturnPostfixQName() {
        Foo foo = Mockito.mock(Foo.class);
        Person person = Mockito.mock(Person.class);
        int apple = 0;

        int actual = castFieldAccess.returnPostfixQName(foo, person);

        assertEquals(apple, actual);
    }

    @Test
    public void testAssignPrefixQName() {
        Foo foo = Mockito.mock(Foo.class);
        Person person = Mockito.mock(Person.class);
        int lid = 1;

        int actual = castFieldAccess.assignPrefixQName(foo, person);

        assertEquals(lid, actual);
    }

    @Test
    public void testReturnPrefixQName() {
        Foo foo = Mockito.mock(Foo.class);
        Person person = Mockito.mock(Person.class);
        int apple = -1;

        int actual = castFieldAccess.returnPrefixQName(foo, person);

        assertEquals(apple, actual);
    }

    @Test
    public void testAssignQNameInCreation() {
        Person person = Mockito.mock(Person.class);
        Person clone = new Person((int) ((person).lid));

        Person actual = castFieldAccess.assignQNameInCreation(person);

        assertEquals(clone, actual);
    }

    @Test
    public void testReturnQNameInCreation() {
        Person person = Mockito.mock(Person.class);
        Person person2 = new Person((int) (((person).lid)));

        Person actual = castFieldAccess.returnQNameInCreation(person);

        assertEquals(person2, actual);
    }

    @Test
    public void testAssignQNameInArrayCreation() {
        Person person = Mockito.mock(Person.class);
        String[] names = new String[(int) ((person).lid)];

        String[] actual = castFieldAccess.assignQNameInArrayCreation(person);

        assertArrayEquals(names, actual);
    }

    @Test
    public void testReturnQNameInArrayCreation() {
        Person person = Mockito.mock(Person.class);
        String[] apple = new String[(int) (((person).lid))];

        String[] actual = castFieldAccess.returnQNameInArrayCreation(person);

        assertArrayEquals(apple, actual);
    }

    @Test
    public void testAssignQNameInArrayAccess() {
        String[] names = {"foo"};
        Person person = Mockito.mock(Person.class);
        String name = names[(int) ((person).lid)];

        String actual = castFieldAccess.assignQNameInArrayAccess(names, person);

        assertEquals(name, actual);
    }

    @Test
    public void testReturnQNameInArrayAccess() {
        String[] names = {"foo"};
        Person person = Mockito.mock(Person.class);
        String apple = names[0];

        String actual = castFieldAccess.returnQNameInArrayAccess(names, person);

        assertEquals(apple, actual);
    }
}
