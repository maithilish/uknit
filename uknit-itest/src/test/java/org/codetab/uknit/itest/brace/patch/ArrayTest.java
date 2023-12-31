package org.codetab.uknit.itest.brace.patch;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.codetab.uknit.itest.brace.patch.Model.Bar;
import org.codetab.uknit.itest.brace.patch.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class ArrayTest {
    @InjectMocks
    private Array array;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAssignArrayCreation() {
        Foo foo = Mockito.mock(Foo.class);
        int apple = 1;
        Locale[] locales = new Locale[apple];

        when(foo.size()).thenReturn(apple);

        Locale[] actual = array.assignArrayCreation(foo);

        assertArrayEquals(locales, actual);
    }

    @Test
    public void testReturnArrayCreation() {
        Foo foo = Mockito.mock(Foo.class);
        int apple = 1;
        Locale[] grape = new Locale[apple];

        when(foo.size()).thenReturn(apple);

        Locale[] actual = array.returnArrayCreation(foo);

        assertArrayEquals(grape, actual);
    }

    @Test
    public void testAssignArrayAccess() {
        Foo foo = Mockito.mock(Foo.class);
        Locale[] locales = {new Locale("en")};
        int apple = 0;
        Locale locale = locales[apple];

        when(foo.index()).thenReturn(apple);

        Locale actual = array.assignArrayAccess(foo, locales);

        assertSame(locale, actual);
    }

    @Test
    public void testReturnArrayAccess() {
        Foo foo = Mockito.mock(Foo.class);
        Locale[] locales = {new Locale("en")};
        int apple = 0;
        Locale locale = locales[apple];

        when(foo.index()).thenReturn(apple);

        Locale actual = array.returnArrayAccess(foo, locales);

        assertSame(locale, actual);
    }

    @Test
    public void testAssignCastInArrayAccess() {
        Foo foo = Mockito.mock(Foo.class);
        Locale[] locales = {new Locale("en")};
        int apple = 0;
        Locale locale = locales[apple];

        when(foo.obj()).thenReturn(apple);

        Locale actual = array.assignCastInArrayAccess(foo, locales);

        assertSame(locale, actual);
    }

    @Test
    public void testReturnCastInArrayAccess() {
        Foo foo = Mockito.mock(Foo.class);
        Locale[] locales = {new Locale("en")};
        int apple = 0;
        Locale locale = locales[apple];

        when(foo.obj()).thenReturn(apple);

        Locale actual = array.returnCastInArrayAccess(foo, locales);

        assertSame(locale, actual);
    }

    @Test
    public void testAssignNewInitializer() {
        Foo foo = Mockito.mock(Foo.class);
        Bar bar = Mockito.mock(Bar.class);
        String apple = "Foo";
        String grape = "Bar";
        String[] names = new String[] {apple, grape};

        when(foo.name()).thenReturn(apple);
        when(bar.name()).thenReturn(grape);

        String[] actual = array.assignNewInitializer(foo, bar);

        assertArrayEquals(names, actual);
    }

    @Test
    public void testReturnNewInitializer() {
        Foo foo = Mockito.mock(Foo.class);
        Bar bar = Mockito.mock(Bar.class);
        String apple = "Foo";
        String grape = "Bar";
        String[] orange = new String[] {apple, grape};

        when(foo.name()).thenReturn(apple);
        when(bar.name()).thenReturn(grape);

        String[] actual = array.returnNewInitializer(foo, bar);

        assertArrayEquals(orange, actual);
    }

    @Test
    public void testAssignArrayInitializer() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "Foo";
        Locale[] locales = {new Locale(apple), new Locale(("foo"), ("bar"))};

        when(foo.lang()).thenReturn(apple);

        Locale[] actual = array.assignArrayInitializer(foo);

        assertArrayEquals(locales, actual);
    }
}
