package org.codetab.uknit.itest.ctlflow.trycatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.model.Duck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class TryCatchFinallyTest {
    @InjectMocks
    private TryCatchFinally tryCatchFinally;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTryFooTry() {
        Duck duck = Mockito.mock(Duck.class);
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryCatchFinally.tryFoo(duck);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("catch illegal arg");
        verify(duck).swim("finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryFooCatchIllegalArgumentException() {
        Duck duck = Mockito.mock(Duck.class);
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalArgumentException.class).when(duck).swim("try");

        String actual = tryCatchFinally.tryFoo(duck);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("catch illegal arg");
        verify(duck).swim("finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryMultiCatchFinallyFooTry3() {
        Duck duck = Mockito.mock(Duck.class);
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryCatchFinally.tryMultiCatchFinallyFoo(duck);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("catch illegal state");
        verify(duck, never()).swim("catch illegal arg");
        verify(duck).swim("finally");
        verify(duck).swim("try 2");
        verify(duck, never()).swim("catch 2 illegal state");
        verify(duck, never()).swim("catch 2 illegal arg");
        verify(duck).swim("finally 2");
        verify(duck).swim("try 3");
        verify(duck).swim("finally 3");
        verify(duck).swim("end");
    }

    @Test
    public void testTryMultiCatchFinallyFooTry32() {
        Duck duck = Mockito.mock(Duck.class);
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("try 2");

        String actual = tryCatchFinally.tryMultiCatchFinallyFoo(duck);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("catch illegal state");
        verify(duck, never()).swim("catch illegal arg");
        verify(duck).swim("finally");
        verify(duck).swim("try 2");
        verify(duck).swim("catch 2 illegal state");
        verify(duck, never()).swim("catch 2 illegal arg");
        verify(duck).swim("finally 2");
        verify(duck).swim("try 3");
        verify(duck).swim("finally 3");
        verify(duck).swim("end");
    }

    @Test
    public void testTryMultiCatchFinallyFooTry33() {
        Duck duck = Mockito.mock(Duck.class);
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalArgumentException.class).when(duck).swim("try 2");

        String actual = tryCatchFinally.tryMultiCatchFinallyFoo(duck);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("catch illegal state");
        verify(duck, never()).swim("catch illegal arg");
        verify(duck).swim("finally");
        verify(duck).swim("try 2");
        verify(duck, never()).swim("catch 2 illegal state");
        verify(duck).swim("catch 2 illegal arg");
        verify(duck).swim("finally 2");
        verify(duck).swim("try 3");
        verify(duck).swim("finally 3");
        verify(duck).swim("end");
    }

    @Test
    public void testTryMultiCatchFinallyFooTry34() {
        Duck duck = Mockito.mock(Duck.class);
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("try");

        String actual = tryCatchFinally.tryMultiCatchFinallyFoo(duck);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("catch illegal state");
        verify(duck, never()).swim("catch illegal arg");
        verify(duck).swim("finally");
        verify(duck).swim("try 2");
        verify(duck, never()).swim("catch 2 illegal state");
        verify(duck, never()).swim("catch 2 illegal arg");
        verify(duck).swim("finally 2");
        verify(duck).swim("try 3");
        verify(duck).swim("finally 3");
        verify(duck).swim("end");
    }

    @Test
    public void testTryMultiCatchFinallyFooTry35() {
        Duck duck = Mockito.mock(Duck.class);
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalArgumentException.class).when(duck).swim("try");

        String actual = tryCatchFinally.tryMultiCatchFinallyFoo(duck);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("catch illegal state");
        verify(duck).swim("catch illegal arg");
        verify(duck).swim("finally");
        verify(duck).swim("try 2");
        verify(duck, never()).swim("catch 2 illegal state");
        verify(duck, never()).swim("catch 2 illegal arg");
        verify(duck).swim("finally 2");
        verify(duck).swim("try 3");
        verify(duck).swim("finally 3");
        verify(duck).swim("end");
    }
}
