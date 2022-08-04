package org.codetab.uknit.itest.ctlflow.ifelse;

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
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElse.ifFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck).swim("end");
    }

    @Test
    public void testIfFooCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElse.ifFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseFooIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElse.ifElseFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElse.ifElseFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfFooIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElse.ifNestIfFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck).swim("if if");
        verify(duck, never()).swim("if else");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfFooElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElse.ifNestIfFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("if if");
        verify(duck).swim("if else");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElse.ifNestIfFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).swim("if if");
        verify(duck, never()).swim("if else");
        verify(duck).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfIfFooIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElse.ifIfFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck).swim("if if");
        verify(duck, never()).swim("if else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfIfFooElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElse.ifIfFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck, never()).swim("if if");
        verify(duck).swim("if else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfIfFooIfDone2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElse.ifIfFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck).swim("else");
        verify(duck).swim("if if");
        verify(duck, never()).swim("if else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfIfFooIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElse.ifNestIfIfFoo(duck, canSwim, canDive, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck).swim("nest if");
        verify(duck, never()).swim("nest else");
        verify(duck).swim("nest if if");
        verify(duck, never()).swim("nest if else");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfIfFooElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElse.ifNestIfIfFoo(duck, canSwim, canDive, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck).swim("nest if");
        verify(duck, never()).swim("nest else");
        verify(duck, never()).swim("nest if if");
        verify(duck).swim("nest if else");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfIfFooIfDone2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = false;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElse.ifNestIfIfFoo(duck, canSwim, canDive, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("nest if");
        verify(duck).swim("nest else");
        verify(duck).swim("nest if if");
        verify(duck, never()).swim("nest if else");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfIfFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElse.ifNestIfIfFoo(duck, canSwim, canDive, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).swim("nest if");
        verify(duck, never()).swim("nest else");
        verify(duck, never()).swim("nest if if");
        verify(duck, never()).swim("nest if else");
        verify(duck).swim("else");
        verify(duck).swim("end");
    }
}
