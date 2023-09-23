package org.codetab.uknit.itest.flow.ifelse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.flow.ifelse.Model.Duck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class IfTryTest {
    @InjectMocks
    private IfTry ifTry;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIfTryFooIfCanSwimTry() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifTry.ifTryFoo(duck, canSwim);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("else canSwim");
        verify(duck).swim("if try");
        verify(duck, never()).swim("if catch");
        verify(duck).swim("if finally");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTryFooIfCanSwimTryCatchIllegalStateException() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("if try");

        String actual = ifTry.ifTryFoo(duck, canSwim);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("else canSwim");
        verify(duck).swim("if try");
        verify(duck).swim("if catch");
        verify(duck).swim("if finally");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTryFooElseCanSwimTry() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifTry.ifTryFoo(duck, canSwim);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck).swim("else canSwim");
        verify(duck).swim("if try");
        verify(duck, never()).swim("if catch");
        verify(duck).swim("if finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryIfFooTryIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifTry.tryIfFoo(duck, canSwim);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck).swim("if try");
        verify(duck, never()).swim("if catch");
        verify(duck).swim("if finally");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("else canSwim");
        verify(duck).swim("end");
    }

    @Test
    public void testTryIfFooTryElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifTry.tryIfFoo(duck, canSwim);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck).swim("if try");
        verify(duck, never()).swim("if catch");
        verify(duck).swim("if finally");
        verify(duck, never()).swim("if canSwim");
        verify(duck).swim("else canSwim");
        verify(duck).swim("end");
    }

    @Test
    public void testTryIfFooTryCatchIllegalStateException() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).swim("if try");

        String actual = ifTry.tryIfFoo(duck, canSwim);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck).swim("if try");
        verify(duck).swim("if catch");
        verify(duck).swim("if finally");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("else canSwim");
        verify(duck).swim("end");
    }
}
