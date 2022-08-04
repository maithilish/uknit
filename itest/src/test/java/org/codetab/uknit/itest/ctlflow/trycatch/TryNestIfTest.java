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

public class TryNestIfTest {
    @InjectMocks
    private TryNestIf tryNestIf;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIfNestTryFooTry() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryNestIf.ifNestTryFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck).swim("if try");
        verify(duck, never()).swim("if catch");
        verify(duck).swim("if finally");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestTryFooCatchIllegalStateException() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("if try");

        String actual = tryNestIf.ifNestTryFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck).swim("if try");
        verify(duck).swim("if catch");
        verify(duck).swim("if finally");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestTryFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryNestIf.ifNestTryFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).swim("if try");
        verify(duck, never()).swim("if catch");
        verify(duck, never()).swim("if finally");
        verify(duck).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryNestIfFooIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryNestIf.tryNestIfFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("try if");
        verify(duck).swim("try if if");
        verify(duck, never()).swim("try if else");
        verify(duck, never()).swim("try else");
        verify(duck, never()).swim("catch");
        verify(duck).swim("finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryNestIfFooElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryNestIf.tryNestIfFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("try if");
        verify(duck, never()).swim("try if if");
        verify(duck).swim("try if else");
        verify(duck, never()).swim("try else");
        verify(duck, never()).swim("catch");
        verify(duck).swim("finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryNestIfFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryNestIf.tryNestIfFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("try if");
        verify(duck, never()).swim("try if if");
        verify(duck, never()).swim("try if else");
        verify(duck).swim("try else");
        verify(duck, never()).swim("catch");
        verify(duck).swim("finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryNestIfFooCatchIllegalStateException() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("try");

        String actual = tryNestIf.tryNestIfFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("try if");
        verify(duck, never()).swim("try if if");
        verify(duck, never()).swim("try if else");
        verify(duck, never()).swim("try else");
        verify(duck).swim("catch");
        verify(duck).swim("finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryCatchNestIfFooTry() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryNestIf.tryCatchNestIfFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("catch");
        verify(duck, never()).swim("catch if");
        verify(duck, never()).swim("catch if if");
        verify(duck, never()).swim("catch if else");
        verify(duck, never()).swim("catch else");
        verify(duck).swim("finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryCatchNestIfFooIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("try");

        String actual = tryNestIf.tryCatchNestIfFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("catch");
        verify(duck).swim("catch if");
        verify(duck).swim("catch if if");
        verify(duck, never()).swim("catch if else");
        verify(duck, never()).swim("catch else");
        verify(duck).swim("finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryCatchNestIfFooElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("try");

        String actual = tryNestIf.tryCatchNestIfFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("catch");
        verify(duck).swim("catch if");
        verify(duck, never()).swim("catch if if");
        verify(duck).swim("catch if else");
        verify(duck, never()).swim("catch else");
        verify(duck).swim("finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryCatchNestIfFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("try");

        String actual = tryNestIf.tryCatchNestIfFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("catch");
        verify(duck, never()).swim("catch if");
        verify(duck, never()).swim("catch if if");
        verify(duck, never()).swim("catch if else");
        verify(duck).swim("catch else");
        verify(duck).swim("finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryFinallyNestIfFooIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryNestIf.tryFinallyNestIfFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("catch");
        verify(duck).swim("finally");
        verify(duck).swim("finally if");
        verify(duck).swim("finally if if");
        verify(duck, never()).swim("finally if else");
        verify(duck, never()).swim("finally else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryFinallyNestIfFooElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryNestIf.tryFinallyNestIfFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("catch");
        verify(duck).swim("finally");
        verify(duck).swim("finally if");
        verify(duck, never()).swim("finally if if");
        verify(duck).swim("finally if else");
        verify(duck, never()).swim("finally else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryFinallyNestIfFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryNestIf.tryFinallyNestIfFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("catch");
        verify(duck).swim("finally");
        verify(duck, never()).swim("finally if");
        verify(duck, never()).swim("finally if if");
        verify(duck, never()).swim("finally if else");
        verify(duck).swim("finally else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryFinallyNestIfFooIfDone2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("try");

        String actual = tryNestIf.tryFinallyNestIfFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("catch");
        verify(duck).swim("finally");
        verify(duck).swim("finally if");
        verify(duck).swim("finally if if");
        verify(duck, never()).swim("finally if else");
        verify(duck, never()).swim("finally else");
        verify(duck).swim("end");
    }
}
