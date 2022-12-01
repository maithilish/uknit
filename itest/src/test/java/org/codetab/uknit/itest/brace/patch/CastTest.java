package org.codetab.uknit.itest.brace.patch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.codetab.uknit.itest.brace.patch.Model.Foo;
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
    public void testCreateAssignCast() {
        Locale obj = new Locale("en");
        Locale locale = obj;

        Locale actual = cast.createAssignCast();

        assertEquals(locale, actual);
    }

    @Test
    public void testCreateReturnCast() {
        Locale obj = new Locale("en");

        Locale actual = cast.createReturnCast();

        assertEquals(obj, actual);
    }

    @Test
    public void testAssignCast() {
        Foo foo = Mockito.mock(Foo.class);
        Locale locale2 = Mockito.mock(Locale.class);
        Locale locale = locale2;

        when(foo.obj()).thenReturn(locale2);

        Locale actual = cast.assignCast(foo);

        assertSame(locale, actual);
    }

    @Test
    public void testReturnCast() {
        Foo foo = Mockito.mock(Foo.class);
        Locale locale = Mockito.mock(Locale.class);

        when(foo.obj()).thenReturn(locale);

        Locale actual = cast.returnCast(foo);

        assertSame(locale, actual);
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
