package org.codetab.uknit.itest.ctlflow.trycatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.model.Duck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class TryNestIfNoCatchTest {
    @InjectMocks
    private TryNestIfNoCatch tryNestIfNoCatch;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTryNestIfNoCatchFooIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryNestIfNoCatch.tryNestIfNoCatchFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("try if");
        verify(duck, never()).swim("try else");
        verify(duck).swim("finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryNestIfNoCatchFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryNestIfNoCatch.tryNestIfNoCatchFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("try if");
        verify(duck).swim("try else");
        verify(duck).swim("finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryFinallyNestIfNoCatchFooIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual =
                tryNestIfNoCatch.tryFinallyNestIfNoCatchFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("finally");
        verify(duck).swim("finally if");
        verify(duck, never()).swim("finally else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryFinallyNestIfNoCatchFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual =
                tryNestIfNoCatch.tryFinallyNestIfNoCatchFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("finally");
        verify(duck, never()).swim("finally if");
        verify(duck).swim("finally else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryNestIfPlusIfNoCatchFooIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual =
                tryNestIfNoCatch.tryNestIfPlusIfNoCatchFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("try if");
        verify(duck, never()).swim("try else");
        verify(duck).swim("finally");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryNestIfPlusIfNoCatchFooElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual =
                tryNestIfNoCatch.tryNestIfPlusIfNoCatchFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("try if");
        verify(duck, never()).swim("try else");
        verify(duck).swim("finally");
        verify(duck, never()).swim("if");
        verify(duck).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryNestIfPlusIfNoCatchFooIfDone2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual =
                tryNestIfNoCatch.tryNestIfPlusIfNoCatchFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("try if");
        verify(duck).swim("try else");
        verify(duck).swim("finally");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryFinallyNestIfPlusIfNoCatchFooIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryNestIfNoCatch.tryFinallyNestIfPlusIfNoCatchFoo(duck,
                canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("finally");
        verify(duck).swim("finally if");
        verify(duck, never()).swim("finally else");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryFinallyNestIfPlusIfNoCatchFooElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryNestIfNoCatch.tryFinallyNestIfPlusIfNoCatchFoo(duck,
                canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("finally");
        verify(duck).swim("finally if");
        verify(duck, never()).swim("finally else");
        verify(duck, never()).swim("if");
        verify(duck).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryFinallyNestIfPlusIfNoCatchFooIfDone2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryNestIfNoCatch.tryFinallyNestIfPlusIfNoCatchFoo(duck,
                canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("finally");
        verify(duck, never()).swim("finally if");
        verify(duck).swim("finally else");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }
}
