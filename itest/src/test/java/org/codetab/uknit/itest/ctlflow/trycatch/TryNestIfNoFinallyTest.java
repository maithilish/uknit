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

public class TryNestIfNoFinallyTest {
    @InjectMocks
    private TryNestIfNoFinally tryNestIfNoFinally;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTryNestIfNoFinallyFooIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryNestIfNoFinally.tryNestIfNoFinallyFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("try if");
        verify(duck, never()).swim("try else");
        verify(duck, never()).swim("catch");
        verify(duck).swim("end");
    }

    @Test
    public void testTryNestIfNoFinallyFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryNestIfNoFinally.tryNestIfNoFinallyFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("try if");
        verify(duck).swim("try else");
        verify(duck, never()).swim("catch");
        verify(duck).swim("end");
    }

    @Test
    public void testTryNestIfNoFinallyFooCatchIllegalStateException() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("try");

        String actual = tryNestIfNoFinally.tryNestIfNoFinallyFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("try if");
        verify(duck, never()).swim("try else");
        verify(duck).swim("catch");
        verify(duck).swim("end");
    }

    @Test
    public void testTryCatchNestIfNoFinallyFooTry() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual =
                tryNestIfNoFinally.tryCatchNestIfNoFinallyFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("catch");
        verify(duck, never()).swim("catch if");
        verify(duck, never()).swim("catch else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryCatchNestIfNoFinallyFooIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("try");

        String actual =
                tryNestIfNoFinally.tryCatchNestIfNoFinallyFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("catch");
        verify(duck).swim("catch if");
        verify(duck, never()).swim("catch else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryCatchNestIfNoFinallyFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("try");

        String actual =
                tryNestIfNoFinally.tryCatchNestIfNoFinallyFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("catch");
        verify(duck, never()).swim("catch if");
        verify(duck).swim("catch else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryNestIfPlusIfNoFinallyFooIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryNestIfNoFinally.tryNestIfPlusIfNoFinallyFoo(duck,
                canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("try if");
        verify(duck, never()).swim("try else");
        verify(duck, never()).swim("catch");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryNestIfPlusIfNoFinallyFooElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryNestIfNoFinally.tryNestIfPlusIfNoFinallyFoo(duck,
                canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("try if");
        verify(duck, never()).swim("try else");
        verify(duck, never()).swim("catch");
        verify(duck, never()).swim("if");
        verify(duck).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryNestIfPlusIfNoFinallyFooIfDone2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryNestIfNoFinally.tryNestIfPlusIfNoFinallyFoo(duck,
                canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("try if");
        verify(duck).swim("try else");
        verify(duck, never()).swim("catch");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryNestIfPlusIfNoFinallyFooIfDone3() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("try");

        String actual = tryNestIfNoFinally.tryNestIfPlusIfNoFinallyFoo(duck,
                canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("try if");
        verify(duck, never()).swim("try else");
        verify(duck).swim("catch");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryCatchNestIfPlusIfNoFinallyFooIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryNestIfNoFinally
                .tryCatchNestIfPlusIfNoFinallyFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("catch");
        verify(duck, never()).swim("catch if");
        verify(duck, never()).swim("catch else");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryCatchNestIfPlusIfNoFinallyFooElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryNestIfNoFinally
                .tryCatchNestIfPlusIfNoFinallyFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("catch");
        verify(duck, never()).swim("catch if");
        verify(duck, never()).swim("catch else");
        verify(duck, never()).swim("if");
        verify(duck).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryCatchNestIfPlusIfNoFinallyFooIfDone2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("try");

        String actual = tryNestIfNoFinally
                .tryCatchNestIfPlusIfNoFinallyFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("catch");
        verify(duck).swim("catch if");
        verify(duck, never()).swim("catch else");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryCatchNestIfPlusIfNoFinallyFooIfDone3() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("try");

        String actual = tryNestIfNoFinally
                .tryCatchNestIfPlusIfNoFinallyFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("catch");
        verify(duck, never()).swim("catch if");
        verify(duck).swim("catch else");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }
}
