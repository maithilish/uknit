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

public class TryNestTryTest {
    @InjectMocks
    private TryNestTry tryNestTry;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTryNestTryFooTry2() {
        Duck duck = Mockito.mock(Duck.class);
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryNestTry.tryNestTryFoo(duck);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("try try");
        verify(duck, never()).swim("try try catch");
        verify(duck).swim("try try finally");
        verify(duck, never()).swim("catch");
        verify(duck).swim("finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryNestTryFooCatchIllegalStateException() {
        Duck duck = Mockito.mock(Duck.class);
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("try try");

        String actual = tryNestTry.tryNestTryFoo(duck);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("try try");
        verify(duck).swim("try try catch");
        verify(duck).swim("try try finally");
        verify(duck, never()).swim("catch");
        verify(duck).swim("finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryNestTryFooCatchIllegalStateException2() {
        Duck duck = Mockito.mock(Duck.class);
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("try");

        String actual = tryNestTry.tryNestTryFoo(duck);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("try try");
        verify(duck, never()).swim("try try catch");
        verify(duck, never()).swim("try try finally");
        verify(duck).swim("catch");
        verify(duck).swim("finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryCatchNestTryFooTry() {
        Duck duck = Mockito.mock(Duck.class);
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryNestTry.tryCatchNestTryFoo(duck);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("catch");
        verify(duck, never()).swim("catch try");
        verify(duck, never()).swim("catch catch");
        verify(duck, never()).swim("catch finally");
        verify(duck).swim("finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryCatchNestTryFooTry2() {
        Duck duck = Mockito.mock(Duck.class);
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("try");

        String actual = tryNestTry.tryCatchNestTryFoo(duck);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("catch");
        verify(duck).swim("catch try");
        verify(duck, never()).swim("catch catch");
        verify(duck).swim("catch finally");
        verify(duck).swim("finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryCatchNestTryFooCatchIllegalStateException() {
        Duck duck = Mockito.mock(Duck.class);
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("try");
        doThrow(IllegalStateException.class).when(duck).swim("catch try");

        String actual = tryNestTry.tryCatchNestTryFoo(duck);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("catch");
        verify(duck).swim("catch try");
        verify(duck).swim("catch catch");
        verify(duck).swim("catch finally");
        verify(duck).swim("finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryFinallyNestTryFooTry2() {
        Duck duck = Mockito.mock(Duck.class);
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryNestTry.tryFinallyNestTryFoo(duck);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("catch");
        verify(duck).swim("finally");
        verify(duck).swim("finally try");
        verify(duck, never()).swim("finally catch");
        verify(duck).swim("finally finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryFinallyNestTryFooCatchIllegalStateException() {
        Duck duck = Mockito.mock(Duck.class);
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("finally try");

        String actual = tryNestTry.tryFinallyNestTryFoo(duck);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("catch");
        verify(duck).swim("finally");
        verify(duck).swim("finally try");
        verify(duck).swim("finally catch");
        verify(duck).swim("finally finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryFinallyNestTryFooTry3() {
        Duck duck = Mockito.mock(Duck.class);
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("try");

        String actual = tryNestTry.tryFinallyNestTryFoo(duck);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("catch");
        verify(duck).swim("finally");
        verify(duck).swim("finally try");
        verify(duck, never()).swim("finally catch");
        verify(duck).swim("finally finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryNestTryPlusIfFooIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryNestTry.tryNestTryPlusIfFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("try try");
        verify(duck, never()).swim("try try catch");
        verify(duck).swim("try try finally");
        verify(duck, never()).swim("catch");
        verify(duck).swim("finally");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryNestTryPlusIfFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryNestTry.tryNestTryPlusIfFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("try try");
        verify(duck, never()).swim("try try catch");
        verify(duck).swim("try try finally");
        verify(duck, never()).swim("catch");
        verify(duck).swim("finally");
        verify(duck, never()).swim("if");
        verify(duck).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryNestTryPlusIfFooIfCanSwim2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("try try");

        String actual = tryNestTry.tryNestTryPlusIfFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("try try");
        verify(duck).swim("try try catch");
        verify(duck).swim("try try finally");
        verify(duck, never()).swim("catch");
        verify(duck).swim("finally");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryNestTryPlusIfFooIfCanSwim3() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("try");

        String actual = tryNestTry.tryNestTryPlusIfFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("try try");
        verify(duck, never()).swim("try try catch");
        verify(duck, never()).swim("try try finally");
        verify(duck).swim("catch");
        verify(duck).swim("finally");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryCatchNestTryPlusIfFooIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryNestTry.tryCatchNestTryPlusIfFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("catch");
        verify(duck, never()).swim("catch try");
        verify(duck, never()).swim("catch catch");
        verify(duck, never()).swim("catch finally");
        verify(duck).swim("finally");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryCatchNestTryPlusIfFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryNestTry.tryCatchNestTryPlusIfFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("catch");
        verify(duck, never()).swim("catch try");
        verify(duck, never()).swim("catch catch");
        verify(duck, never()).swim("catch finally");
        verify(duck).swim("finally");
        verify(duck, never()).swim("if");
        verify(duck).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryCatchNestTryPlusIfFooIfCanSwim2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("try");

        String actual = tryNestTry.tryCatchNestTryPlusIfFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("catch");
        verify(duck).swim("catch try");
        verify(duck, never()).swim("catch catch");
        verify(duck).swim("catch finally");
        verify(duck).swim("finally");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryCatchNestTryPlusIfFooIfCanSwim3() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("try");
        doThrow(IllegalStateException.class).when(duck).swim("catch try");

        String actual = tryNestTry.tryCatchNestTryPlusIfFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("catch");
        verify(duck).swim("catch try");
        verify(duck).swim("catch catch");
        verify(duck).swim("catch finally");
        verify(duck).swim("finally");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryFinallyNestTryPlusIfFooIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryNestTry.tryFinallyNestTryPlusIfFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("catch");
        verify(duck).swim("finally");
        verify(duck).swim("finally try");
        verify(duck, never()).swim("finally catch");
        verify(duck).swim("finally finally");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryFinallyNestTryPlusIfFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = tryNestTry.tryFinallyNestTryPlusIfFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("catch");
        verify(duck).swim("finally");
        verify(duck).swim("finally try");
        verify(duck, never()).swim("finally catch");
        verify(duck).swim("finally finally");
        verify(duck, never()).swim("if");
        verify(duck).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryFinallyNestTryPlusIfFooIfCanSwim2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("finally try");

        String actual = tryNestTry.tryFinallyNestTryPlusIfFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck, never()).swim("catch");
        verify(duck).swim("finally");
        verify(duck).swim("finally try");
        verify(duck).swim("finally catch");
        verify(duck).swim("finally finally");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryFinallyNestTryPlusIfFooIfCanSwim3() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("try");

        String actual = tryNestTry.tryFinallyNestTryPlusIfFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("catch");
        verify(duck).swim("finally");
        verify(duck).swim("finally try");
        verify(duck, never()).swim("finally catch");
        verify(duck).swim("finally finally");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }
}
