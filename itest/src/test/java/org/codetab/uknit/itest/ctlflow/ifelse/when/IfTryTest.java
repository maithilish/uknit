package org.codetab.uknit.itest.ctlflow.ifelse.when;

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

public class IfTryTest {
    @InjectMocks
    private IfTry ifTry;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIfTryFooTry() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String state = "Foo";

        when(duck.fly("if")).thenReturn(state);
        when(duck.fly("if try")).thenReturn(state);
        when(duck.fly("if finally")).thenReturn(state);

        String actual = ifTry.ifTryFoo(duck, canSwim);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("if try");
        verify(duck, never()).swim("if catch");
        verify(duck, never()).fly("if catch");
        verify(duck).swim("if finally");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTryFooCatchIllegalStateException() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String state = "Foo";

        when(duck.fly("if")).thenReturn(state);
        when(duck.fly("if try")).thenReturn(state);
        when(duck.fly("if catch")).thenReturn(state);
        when(duck.fly("if finally")).thenReturn(state);
        doThrow(IllegalStateException.class).when(duck).swim("if try");

        String actual = ifTry.ifTryFoo(duck, canSwim);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("if try");
        verify(duck).swim("if catch");
        verify(duck).swim("if finally");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTryFooTry2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String state = "Foo";

        when(duck.fly("else")).thenReturn(state);
        when(duck.fly("if try")).thenReturn(state);
        when(duck.fly("if finally")).thenReturn(state);

        String actual = ifTry.ifTryFoo(duck, canSwim);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck).swim("else");
        verify(duck).swim("if try");
        verify(duck, never()).swim("if catch");
        verify(duck, never()).fly("if catch");
        verify(duck).swim("if finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryIfFooIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String state = "Foo";

        when(duck.fly("if try")).thenReturn(state);
        when(duck.fly("if finally")).thenReturn(state);
        when(duck.fly("if")).thenReturn(state);

        String actual = ifTry.tryIfFoo(duck, canSwim);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if try");
        verify(duck, never()).swim("if catch");
        verify(duck, never()).fly("if catch");
        verify(duck).swim("if finally");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryIfFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String state = "Foo";

        when(duck.fly("if try")).thenReturn(state);
        when(duck.fly("if finally")).thenReturn(state);
        when(duck.fly("else")).thenReturn(state);

        String actual = ifTry.tryIfFoo(duck, canSwim);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if try");
        verify(duck, never()).swim("if catch");
        verify(duck, never()).fly("if catch");
        verify(duck).swim("if finally");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryIfFooIfCanSwim2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String state = "Foo";

        when(duck.fly("if try")).thenReturn(state);
        when(duck.fly("if catch")).thenReturn(state);
        when(duck.fly("if finally")).thenReturn(state);
        when(duck.fly("if")).thenReturn(state);
        doThrow(IllegalStateException.class).when(duck).swim("if try");

        String actual = ifTry.tryIfFoo(duck, canSwim);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if try");
        verify(duck).swim("if catch");
        verify(duck).swim("if finally");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTryNoAssignFooTry() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifTry.ifTryNoAssignFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck).fly("if");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("if try");
        verify(duck).fly("if try");
        verify(duck, never()).swim("if catch");
        verify(duck, never()).fly("if catch");
        verify(duck).swim("if finally");
        verify(duck).fly("if finally");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTryNoAssignFooCatchIllegalStateException() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).fly("if try");

        String actual = ifTry.ifTryNoAssignFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck).fly("if");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("if try");
        verify(duck).fly("if try");
        verify(duck).swim("if catch");
        verify(duck).fly("if catch");
        verify(duck).swim("if finally");
        verify(duck).fly("if finally");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTryNoAssignFooTry2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifTry.ifTryNoAssignFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck).swim("else");
        verify(duck).fly("else");
        verify(duck).swim("if try");
        verify(duck).fly("if try");
        verify(duck, never()).swim("if catch");
        verify(duck, never()).fly("if catch");
        verify(duck).swim("if finally");
        verify(duck).fly("if finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryIfNoAssignFooIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifTry.tryIfNoAssignFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if try");
        verify(duck).fly("if try");
        verify(duck, never()).swim("if catch");
        verify(duck, never()).fly("if catch");
        verify(duck).swim("if finally");
        verify(duck).fly("if finally");
        verify(duck).swim("if");
        verify(duck).fly("if");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryIfNoAssignFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifTry.tryIfNoAssignFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if try");
        verify(duck).fly("if try");
        verify(duck, never()).swim("if catch");
        verify(duck, never()).fly("if catch");
        verify(duck).swim("if finally");
        verify(duck).fly("if finally");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck).swim("else");
        verify(duck).fly("else");
        verify(duck).swim("end");
    }

    @Test
    public void testTryIfNoAssignFooIfCanSwim2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(duck).fly("if try");

        String actual = ifTry.tryIfNoAssignFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if try");
        verify(duck).fly("if try");
        verify(duck).swim("if catch");
        verify(duck).fly("if catch");
        verify(duck).swim("if finally");
        verify(duck).fly("if finally");
        verify(duck).swim("if");
        verify(duck).fly("if");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("end");
    }
}