package org.codetab.uknit.itest.linked;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.codetab.uknit.itest.linked.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class AssignTest {
    @InjectMocks
    private Assign assign;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAssignCreated() {
        Locale Locale = new Locale("en");

        Locale actual = assign.assignCreated();

        assertEquals(Locale, actual);
    }

    @Test
    public void testAssignTwiceCreated() {
        Locale Locale = new Locale("en");
        Locale Locale2 = Locale;

        Locale actual = assign.assignTwiceCreated();

        assertEquals(Locale2, actual);
    }

    @Test
    public void testAssignThriceCreated() {
        Locale Locale = new Locale("en");
        Locale Locale2 = Locale;
        Locale Locale3 = Locale2;

        Locale actual = assign.assignThriceCreated();

        assertEquals(Locale3, actual);
    }

    @Test
    public void testAssignInvoke() {
        Foo foo = Mockito.mock(Foo.class);
        Locale Locale = Mockito.mock(Locale.class);
        Locale Locale2 = Locale;
        Locale Locale3 = Locale2;

        when(foo.locale()).thenReturn(Locale);

        Locale actual = assign.assignInvoke(foo);

        assertSame(Locale3, actual);
    }

    @Test
    public void testAssignLiteral() {
        Foo foo = Mockito.mock(Foo.class);
        String name = "foo";
        String name2 = name;
        String name3 = name2;

        String actual = assign.assignLiteral(foo);

        assertEquals(name3, actual);
    }

    @Test
    public void testAssignArray() {
        String[] names = {"foo"};
        String[] names2 = names;
        String[] names3 = names2;

        String[] actual = assign.assignArray();

        assertArrayEquals(names3, actual);
    }

    @Test
    public void testAssignArrayAccess() {
        String[] names = {"foo"};
        String name = names[0];
        String name2 = name;
        String name3 = name2;

        String actual = assign.assignArrayAccess(names);

        assertEquals(name3, actual);
    }
}
