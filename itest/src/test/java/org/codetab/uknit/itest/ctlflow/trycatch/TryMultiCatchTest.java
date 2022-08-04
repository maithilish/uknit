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

public class TryMultiCatchTest {
    @InjectMocks
    private TryMultiCatch tryMultiCatch;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTryMultiCatchFooTry() {
        Duck duck = Mockito.mock(Duck.class);
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryMultiCatch.tryMultiCatchFoo(duck);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("multi catch");
        verify(duck).swim("finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryMultiCatchFooCatchIllegalStateException() {
        Duck duck = Mockito.mock(Duck.class);
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("try");

        String actual = tryMultiCatch.tryMultiCatchFoo(duck);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("multi catch");
        verify(duck).swim("finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryMultiCatchFooCatchIllegalStateException2() {
        Duck duck = Mockito.mock(Duck.class);
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("try");

        String actual = tryMultiCatch.tryMultiCatchFoo(duck);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("multi catch");
        verify(duck).swim("finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryCatchMultiCatchFooTry() {
        Duck duck = Mockito.mock(Duck.class);
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryMultiCatch.tryCatchMultiCatchFoo(duck);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("catch");
        verify(duck, never()).swim("multi catch");
        verify(duck).swim("finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryCatchMultiCatchFooCatchIllegalStateException() {
        Duck duck = Mockito.mock(Duck.class);
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("try");

        String actual = tryMultiCatch.tryCatchMultiCatchFoo(duck);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("catch");
        verify(duck, never()).swim("multi catch");
        verify(duck).swim("finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryCatchMultiCatchFooCatchIllegalAccessError() {
        Duck duck = Mockito.mock(Duck.class);
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalAccessError.class).when(duck).swim("try");

        String actual = tryMultiCatch.tryCatchMultiCatchFoo(duck);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("catch");
        verify(duck).swim("multi catch");
        verify(duck).swim("finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryCatchMultiCatchFooCatchIllegalAccessError2() {
        Duck duck = Mockito.mock(Duck.class);
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalAccessError.class).when(duck).swim("try");

        String actual = tryMultiCatch.tryCatchMultiCatchFoo(duck);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("catch");
        verify(duck).swim("multi catch");
        verify(duck).swim("finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryMultiCatchPlusIfFooIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryMultiCatch.tryMultiCatchPlusIfFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("multi catch");
        verify(duck).swim("finally");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryMultiCatchPlusIfFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryMultiCatch.tryMultiCatchPlusIfFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("multi catch");
        verify(duck).swim("finally");
        verify(duck, never()).swim("if");
        verify(duck).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryMultiCatchPlusIfFooIfCanSwim2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("try");

        String actual = tryMultiCatch.tryMultiCatchPlusIfFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("multi catch");
        verify(duck).swim("finally");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryMultiCatchPlusIfFooIfCanSwim3() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("try");

        String actual = tryMultiCatch.tryMultiCatchPlusIfFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("multi catch");
        verify(duck).swim("finally");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryMultiCatchIfPlusIfFooTry() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryMultiCatch.tryMultiCatchIfPlusIfFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("multi catch");
        verify(duck, never()).swim("multi catch if");
        verify(duck, never()).swim("multi catch else");
        verify(duck).swim("finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryMultiCatchIfPlusIfFooIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalArgumentException.class).when(duck).swim("try");

        String actual = tryMultiCatch.tryMultiCatchIfPlusIfFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("multi catch");
        verify(duck).swim("multi catch if");
        verify(duck, never()).swim("multi catch else");
        verify(duck).swim("finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryMultiCatchIfPlusIfFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("try");

        String actual = tryMultiCatch.tryMultiCatchIfPlusIfFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("multi catch");
        verify(duck, never()).swim("multi catch if");
        verify(duck).swim("multi catch else");
        verify(duck).swim("finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryMultiCatchIfPlusIfFooIfCanSwim2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("try");

        String actual = tryMultiCatch.tryMultiCatchIfPlusIfFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("multi catch");
        verify(duck).swim("multi catch if");
        verify(duck, never()).swim("multi catch else");
        verify(duck).swim("finally");
        verify(duck).swim("end");
    }
}
