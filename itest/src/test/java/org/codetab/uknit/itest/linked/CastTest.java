package org.codetab.uknit.itest.linked;

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

public class CastTest {
    @InjectMocks
    private Cast cast;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCastCreated() {
        Locale locale = new Locale("en");

        Locale actual = cast.castCreated();

        assertEquals(locale, actual);
    }

    @Test
    public void testCastTwiceCreated() {
        Locale locale = new Locale("en");
        Locale locale2 = locale;

        Locale actual = cast.castTwiceCreated();

        assertEquals(locale2, actual);
    }

    @Test
    public void testCastThriceCreated() {
        Locale locale = new Locale("en");
        Locale locale2 = locale;
        Locale locale3 = locale2;

        Locale actual = cast.castThriceCreated();

        assertEquals(locale3, actual);
    }

    @Test
    public void testCastInvoke() {
        Foo foo = Mockito.mock(Foo.class);
        Locale locale = Mockito.mock(Locale.class);
        Locale locale2 = locale;
        Locale locale3 = locale2;

        when(foo.locale()).thenReturn(locale);

        Locale actual = cast.castInvoke(foo);

        assertSame(locale3, actual);
    }

    @Test
    public void testCastLiteral() {
        Foo foo = Mockito.mock(Foo.class);
        String name = "foo";
        String name2 = name;
        String name3 = name2;

        String actual = cast.castLiteral(foo);

        assertEquals(name3, actual);
    }

    @Test
    public void testCastArrayAccess() {
        Object[] names = {"foo"};
        String name = (String) names[0];
        String name2 = name;
        String name3 = name2;

        String actual = cast.castArrayAccess(names);

        assertEquals(name3, actual);
    }

    @Test
    public void testCreateAssignCast() {
        Foo foo = Mockito.mock(Foo.class);
        Locale obj = new Locale("");
        Locale locale = obj;

        Locale actual = cast.createAssignCast(foo);

        assertEquals(locale, actual);
    }

    @Test
    public void testCreateReturnCast() {
        Foo foo = Mockito.mock(Foo.class);
        Locale obj = new Locale("");

        Locale actual = cast.createReturnCast(foo);

        assertEquals(obj, actual);
    }

    @Test
    public void testInvokeAssignCast() {
        Foo foo = Mockito.mock(Foo.class);
        Locale obj = Mockito.mock(Locale.class);
        Locale locale = obj;

        when(foo.obj()).thenReturn(obj);

        Locale actual = cast.invokeAssignCast(foo);

        assertSame(locale, actual);
    }

    @Test
    public void testInvokeReturnCast() {
        Foo foo = Mockito.mock(Foo.class);
        Locale obj = Mockito.mock(Locale.class);

        when(foo.obj()).thenReturn(obj);

        Locale actual = cast.invokeReturnCast(foo);

        assertSame(obj, actual);
    }
}
