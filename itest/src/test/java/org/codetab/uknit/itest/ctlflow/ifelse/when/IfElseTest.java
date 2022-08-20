package org.codetab.uknit.itest.ctlflow.ifelse.when;

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

public class IfElseTest {
    @InjectMocks
    private IfElse ifElse;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIfFooIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String state = "Foo";

        when(duck.fly("if")).thenReturn(state);

        String actual = ifElse.ifFoo(duck, canSwim);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck).swim("end");
    }

    @Test
    public void testIfFooCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String state = null;

        String actual = ifElse.ifFoo(duck, canSwim);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseFooIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String state = "Foo";

        when(duck.fly("if")).thenReturn(state);

        String actual = ifElse.ifElseFoo(duck, canSwim);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String state = "Foo";

        when(duck.fly("else")).thenReturn(state);

        String actual = ifElse.ifElseFoo(duck, canSwim);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfFooIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String state = "Foo";

        when(duck.fly("if")).thenReturn(state);
        when(duck.fly("if if")).thenReturn(state);

        String actual = ifElse.ifNestIfFoo(duck, canSwim, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck).swim("if if");
        verify(duck, never()).swim("if else");
        verify(duck, never()).fly("if else");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfFooElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = false;
        String state = "Foo";

        when(duck.fly("if")).thenReturn(state);
        when(duck.fly("if else")).thenReturn(state);

        String actual = ifElse.ifNestIfFoo(duck, canSwim, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("if if");
        verify(duck, never()).fly("if if");
        verify(duck).swim("if else");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = true;
        String state = "Foo";

        when(duck.fly("else")).thenReturn(state);

        String actual = ifElse.ifNestIfFoo(duck, canSwim, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck, never()).swim("if if");
        verify(duck, never()).fly("if if");
        verify(duck, never()).swim("if else");
        verify(duck, never()).fly("if else");
        verify(duck).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfIfFooIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String state = "Foo";

        when(duck.fly("if")).thenReturn(state);
        when(duck.fly("if if")).thenReturn(state);

        String actual = ifElse.ifIfFoo(duck, canSwim, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("if if");
        verify(duck, never()).swim("if else");
        verify(duck, never()).fly("if else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfIfFooElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = false;
        String state = "Foo";

        when(duck.fly("if")).thenReturn(state);
        when(duck.fly("if else")).thenReturn(state);

        String actual = ifElse.ifIfFoo(duck, canSwim, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck, never()).swim("if if");
        verify(duck, never()).fly("if if");
        verify(duck).swim("if else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfIfFooIfDone2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = true;
        String state = "Foo";

        when(duck.fly("else")).thenReturn(state);
        when(duck.fly("if if")).thenReturn(state);

        String actual = ifElse.ifIfFoo(duck, canSwim, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck).swim("else");
        verify(duck).swim("if if");
        verify(duck, never()).swim("if else");
        verify(duck, never()).fly("if else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfIfFooIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = true;
        String state = "Foo";

        when(duck.fly("if")).thenReturn(state);
        when(duck.fly("nest if")).thenReturn(state);
        when(duck.fly("nest if if")).thenReturn(state);

        String actual = ifElse.ifNestIfIfFoo(duck, canSwim, canDive, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck).swim("nest if");
        verify(duck, never()).swim("nest else");
        verify(duck, never()).fly("nest else");
        verify(duck).swim("nest if if");
        verify(duck, never()).swim("nest if else");
        verify(duck, never()).fly("nest if else");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfIfFooElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = false;
        String state = "Foo";

        when(duck.fly("if")).thenReturn(state);
        when(duck.fly("nest if")).thenReturn(state);
        when(duck.fly("nest if else")).thenReturn(state);

        String actual = ifElse.ifNestIfIfFoo(duck, canSwim, canDive, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck).swim("nest if");
        verify(duck, never()).swim("nest else");
        verify(duck, never()).fly("nest else");
        verify(duck, never()).swim("nest if if");
        verify(duck, never()).fly("nest if if");
        verify(duck).swim("nest if else");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfIfFooIfDone2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = false;
        boolean done = true;
        String state = "Foo";

        when(duck.fly("if")).thenReturn(state);
        when(duck.fly("nest else")).thenReturn(state);
        when(duck.fly("nest if if")).thenReturn(state);

        String actual = ifElse.ifNestIfIfFoo(duck, canSwim, canDive, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("nest if");
        verify(duck, never()).fly("nest if");
        verify(duck).swim("nest else");
        verify(duck).swim("nest if if");
        verify(duck, never()).swim("nest if else");
        verify(duck, never()).fly("nest if else");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfIfFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean done = true;
        String state = "Foo";

        when(duck.fly("else")).thenReturn(state);

        String actual = ifElse.ifNestIfIfFoo(duck, canSwim, canDive, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck, never()).swim("nest if");
        verify(duck, never()).fly("nest if");
        verify(duck, never()).swim("nest else");
        verify(duck, never()).fly("nest else");
        verify(duck, never()).swim("nest if if");
        verify(duck, never()).fly("nest if if");
        verify(duck, never()).swim("nest if else");
        verify(duck, never()).fly("nest if else");
        verify(duck).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfNoAssignFooIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElse.ifNoAssignFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck).fly("if");
        verify(duck).swim("end");
    }

    @Test
    public void testIfNoAssignFooCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElse.ifNoAssignFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseNoAssignFooIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElse.ifElseNoAssignFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck).fly("if");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseNoAssignFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElse.ifElseNoAssignFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck).swim("else");
        verify(duck).fly("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfNoAssignFooIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElse.ifNestIfNoAssignFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck).fly("if");
        verify(duck).swim("if if");
        verify(duck).fly("if if");
        verify(duck, never()).swim("if else");
        verify(duck, never()).fly("if else");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfNoAssignFooElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElse.ifNestIfNoAssignFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck).fly("if");
        verify(duck, never()).swim("if if");
        verify(duck, never()).fly("if if");
        verify(duck).swim("if else");
        verify(duck).fly("if else");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfNoAssignFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElse.ifNestIfNoAssignFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck, never()).swim("if if");
        verify(duck, never()).fly("if if");
        verify(duck, never()).swim("if else");
        verify(duck, never()).fly("if else");
        verify(duck).swim("else");
        verify(duck).fly("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfIfNoAssignFooIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElse.ifIfNoAssignFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck).fly("if");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("if if");
        verify(duck).fly("if if");
        verify(duck, never()).swim("if else");
        verify(duck, never()).fly("if else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfIfNoAssignFooElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElse.ifIfNoAssignFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck).fly("if");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck, never()).swim("if if");
        verify(duck, never()).fly("if if");
        verify(duck).swim("if else");
        verify(duck).fly("if else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfIfNoAssignFooIfDone2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElse.ifIfNoAssignFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck).swim("else");
        verify(duck).fly("else");
        verify(duck).swim("if if");
        verify(duck).fly("if if");
        verify(duck, never()).swim("if else");
        verify(duck, never()).fly("if else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfIfNoAssignFooIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual =
                ifElse.ifNestIfIfNoAssignFoo(duck, canSwim, canDive, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck).fly("if");
        verify(duck).swim("nest if");
        verify(duck).fly("nest if");
        verify(duck, never()).swim("nest else");
        verify(duck, never()).fly("nest else");
        verify(duck).swim("nest if if");
        verify(duck).fly("nest if if");
        verify(duck, never()).swim("nest if else");
        verify(duck, never()).fly("nest if else");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfIfNoAssignFooElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual =
                ifElse.ifNestIfIfNoAssignFoo(duck, canSwim, canDive, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck).fly("if");
        verify(duck).swim("nest if");
        verify(duck).fly("nest if");
        verify(duck, never()).swim("nest else");
        verify(duck, never()).fly("nest else");
        verify(duck, never()).swim("nest if if");
        verify(duck, never()).fly("nest if if");
        verify(duck).swim("nest if else");
        verify(duck).fly("nest if else");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfIfNoAssignFooIfDone2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = false;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual =
                ifElse.ifNestIfIfNoAssignFoo(duck, canSwim, canDive, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck).fly("if");
        verify(duck, never()).swim("nest if");
        verify(duck, never()).fly("nest if");
        verify(duck).swim("nest else");
        verify(duck).fly("nest else");
        verify(duck).swim("nest if if");
        verify(duck).fly("nest if if");
        verify(duck, never()).swim("nest if else");
        verify(duck, never()).fly("nest if else");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfIfNoAssignFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual =
                ifElse.ifNestIfIfNoAssignFoo(duck, canSwim, canDive, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck, never()).swim("nest if");
        verify(duck, never()).fly("nest if");
        verify(duck, never()).swim("nest else");
        verify(duck, never()).fly("nest else");
        verify(duck, never()).swim("nest if if");
        verify(duck, never()).fly("nest if if");
        verify(duck, never()).swim("nest if else");
        verify(duck, never()).fly("nest if else");
        verify(duck).swim("else");
        verify(duck).fly("else");
        verify(duck).swim("end");
    }
}