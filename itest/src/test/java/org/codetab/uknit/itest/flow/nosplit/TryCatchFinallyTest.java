package org.codetab.uknit.itest.flow.nosplit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.flow.nosplit.Model.Duck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class TryCatchFinallyTest {
    @InjectMocks
    private TryCatchFinally tryCatchFinally;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTryFoo() {
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
    public void testTryMultiCatchFinallyFoo() {
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
}
