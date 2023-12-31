package org.codetab.uknit.itest.brace;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.brace.Model.Contacts;
import org.codetab.uknit.itest.brace.Model.Foo;
import org.codetab.uknit.itest.brace.Model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class FieldAccessTest {
    @InjectMocks
    private FieldAccess fieldAccess;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAssignFieldAccess() {
        Person person = Mockito.mock(Person.class);
        int id = (person).id;

        int actual = fieldAccess.assignFieldAccess(person);

        assertEquals(id, actual);
    }

    @Test
    public void testReturnFieldAccess() {
        Person person = Mockito.mock(Person.class);
        int apple = (person).id;

        int actual = fieldAccess.returnFieldAccess(person);

        assertEquals(apple, actual);
    }

    @Test
    public void testAssignFieldAccessInInvokeArg() {
        Foo foo = Mockito.mock(Foo.class);
        Person person = Mockito.mock(Person.class);
        String name = "Foo";

        when(foo.get((person).id)).thenReturn(name);

        String actual = fieldAccess.assignFieldAccessInInvokeArg(foo, person);

        assertEquals(name, actual);
    }

    @Test
    public void testReturnFieldAccessInInvokeArg() {
        Foo foo = Mockito.mock(Foo.class);
        Person person = Mockito.mock(Person.class);
        String apple = "Foo";

        when(foo.get((person).id)).thenReturn(apple);

        String actual = fieldAccess.returnFieldAccessInInvokeArg(foo, person);

        assertEquals(apple, actual);
    }

    /*
     * Not possible to test with mocks. The exp person.contacts in invoke
     * person.contacts.getHome(), is field access and Mockito when is not
     * allowed.
     */
    @Test
    public void testAssignFieldAccessInInvokeExp() {
        Foo foo = Mockito.mock(Foo.class);
        Person person = new Person(1);
        person.contacts = new Contacts() {
            @Override
            public String getHome() {
                return "Foo";
            }
        };
        String home = "Foo";

        String actual = fieldAccess.assignFieldAccessInInvokeExp(foo, person);

        assertEquals(home, actual);
    }

    @Test
    public void testReturnFieldAccessInInvokeExp() {
        Foo foo = Mockito.mock(Foo.class);
        Person person = new Person(1);
        person.contacts = new Contacts() {
            @Override
            public String getHome() {
                return "Foo";
            }
        };
        String apple = "Foo";

        String actual = fieldAccess.returnFieldAccessInInvokeExp(foo, person);

        assertEquals(apple, actual);
    }

    @Test
    public void testAssignInfixFieldAccess() {
        Foo foo = Mockito.mock(Foo.class);
        Person person1 = Mockito.mock(Person.class);
        Person person2 = Mockito.mock(Person.class);
        int id = ((person1).id) + ((person2).id);

        int actual = fieldAccess.assignInfixFieldAccess(foo, person1, person2);

        assertEquals(id, actual);
    }

    @Test
    public void testReturnInfixFieldAccess() {
        Foo foo = Mockito.mock(Foo.class);
        Person person1 = Mockito.mock(Person.class);
        Person person2 = Mockito.mock(Person.class);
        int apple = (((person1).id)) + (((person2).id));

        int actual = fieldAccess.returnInfixFieldAccess(foo, person1, person2);

        assertEquals(apple, actual);
    }

    @Test
    public void testAssignConditionalFieldAccess() {
        Foo foo = Mockito.mock(Foo.class);
        Person person = Mockito.mock(Person.class);
        int id = ((person).id) > 1 ? 1 : 0;

        int actual = fieldAccess.assignConditionalFieldAccess(foo, person);

        assertEquals(id, actual);
    }

    @Test
    public void testReturnConditionalFieldAccess() {
        Foo foo = Mockito.mock(Foo.class);
        Person person = Mockito.mock(Person.class);
        int apple = (((person).id)) > 1 ? 1 : 0;

        int actual = fieldAccess.returnConditionalFieldAccess(foo, person);

        assertEquals(apple, actual);
    }

    @Test
    public void testAssignPostfixFieldAccess() {
        Foo foo = Mockito.mock(Foo.class);
        Person person = Mockito.mock(Person.class);
        int id = 0;

        int actual = fieldAccess.assignPostfixFieldAccess(foo, person);

        assertEquals(id, actual);
    }

    @Test
    public void testReturnPostfixFieldAccess() {
        Foo foo = Mockito.mock(Foo.class);
        Person person = Mockito.mock(Person.class);
        int apple = 0;

        int actual = fieldAccess.returnPostfixFieldAccess(foo, person);

        assertEquals(apple, actual);
    }

    @Test
    public void testAssignPrefixFieldAccess() {
        Foo foo = Mockito.mock(Foo.class);
        Person person = Mockito.mock(Person.class);
        int id = 1;

        int actual = fieldAccess.assignPrefixFieldAccess(foo, person);

        assertEquals(id, actual);
    }

    @Test
    public void testReturnPrefixFieldAccess() {
        Foo foo = Mockito.mock(Foo.class);
        Person person = Mockito.mock(Person.class);
        int apple = -1;

        int actual = fieldAccess.returnPrefixFieldAccess(foo, person);

        assertEquals(apple, actual);
    }

    @Test
    public void testAssignFieldAccessInCreation() {
        Person person = Mockito.mock(Person.class);
        Person clone = new Person(((person).id));

        Person actual = fieldAccess.assignFieldAccessInCreation(person);

        assertEquals(clone, actual);
    }

    @Test
    public void testReturnFieldAccessInCreation() {
        Person person = Mockito.mock(Person.class);
        Person person2 = new Person((((person).id)));

        Person actual = fieldAccess.returnFieldAccessInCreation(person);

        assertEquals(person2, actual);
    }

    @Test
    public void testAssignFieldAccessInArrayCreation() {
        Person person = Mockito.mock(Person.class);
        String[] names = new String[((person).id)];

        String[] actual = fieldAccess.assignFieldAccessInArrayCreation(person);

        assertArrayEquals(names, actual);
    }

    @Test
    public void testReturnFieldAccessInArrayCreation() {
        Person person = Mockito.mock(Person.class);
        String[] apple = new String[0];

        String[] actual = fieldAccess.returnFieldAccessInArrayCreation(person);

        assertArrayEquals(apple, actual);
    }

    @Test
    public void testAssignFieldAccessInArrayAccess() {
        String[] names = {"foo"};
        Person person = Mockito.mock(Person.class);
        String name = names[0];

        String actual =
                fieldAccess.assignFieldAccessInArrayAccess(names, person);

        assertEquals(name, actual);
    }

    @Test
    public void testReturnFieldAccessInArrayAccess() {
        String[] names = {"foo"};
        Person person = Mockito.mock(Person.class);
        String apple = names[0];

        String actual =
                fieldAccess.returnFieldAccessInArrayAccess(names, person);

        assertEquals(apple, actual);
    }
}
