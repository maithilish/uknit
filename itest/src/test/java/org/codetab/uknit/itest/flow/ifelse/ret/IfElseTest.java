package org.codetab.uknit.itest.flow.ifelse.ret;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.flow.ifelse.ret.Model.Duck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class IfElseTest {
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
        String state = null;
        String state2 = "Foo";

        when(duck.fly("if canSwim")).thenReturn(state2);

        String actual = ifElse.ifFoo(duck, canSwim);

        assertEquals(state2, actual);

        verify(duck).swim("start");
        verify(duck).dive(state);
        verify(duck).swim("if canSwim");
        verify(duck).dive(state2);
        verify(duck, never()).swim("end");
    }

    @Test
    public void testIfFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String state = null;
        String state2 = "Foo";

        String actual = ifElse.ifFoo(duck, canSwim);

        assertEquals(state, actual);

        verify(duck).swim("start");
        verify(duck).dive(state);
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseFooIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String state = null;
        String state2 = "Foo";
        String state3 = "Bar";

        when(duck.fly("if canSwim")).thenReturn(state2);

        String actual = ifElse.ifElseFoo(duck, canSwim);

        assertEquals(state2, actual);

        verify(duck).swim("start");
        verify(duck).dive(state);
        verify(duck).swim("if canSwim");
        verify(duck).dive(state2);
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
        verify(duck, never()).dive(state3);
    }

    @Test
    public void testIfElseFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String state = null;
        String state2 = "Foo";
        String state3 = "Bar";

        when(duck.fly("else canSwim")).thenReturn(state3);

        String actual = ifElse.ifElseFoo(duck, canSwim);

        assertEquals(state3, actual);

        verify(duck).swim("start");
        verify(duck).dive(state);
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck).swim("else canSwim");
        verify(duck).dive(state3);
    }

    @Test
    public void testIfElseFoo2IfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String state = null;
        String state2 = "Foo";
        String state3 = "Bar";

        when(duck.fly("if canSwim")).thenReturn(state2);

        String actual = ifElse.ifElseFoo2(duck, canSwim);

        assertEquals(state2, actual);

        verify(duck).swim("start");
        verify(duck).dive(state);
        verify(duck).swim("if canSwim");
        verify(duck).dive(state2);
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("end");
    }

    @Test
    public void testIfElseFoo2ElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String state = null;
        String state2 = "Foo";
        String state3 = "Bar";

        when(duck.fly("else canSwim")).thenReturn(state3);

        String actual = ifElse.ifElseFoo2(duck, canSwim);

        assertEquals(state3, actual);

        verify(duck).swim("start");
        verify(duck).dive(state);
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck).swim("else canSwim");
        verify(duck).dive(state3);
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfFooIfCanSwimIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";

        when(duck.fly("if canSwim")).thenReturn(state);
        when(duck.fly("if done nest")).thenReturn(state2);

        String actual = ifElse.ifNestIfFoo(duck, canSwim, done);

        assertEquals(state2, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck).swim("if done nest");
        verify(duck).dive(state2);
        verify(duck, never()).swim("else done nest");
        verify(duck, never()).fly("else done nest");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
        verify(duck, never()).dive(state4);
    }

    @Test
    public void testIfNestIfFooIfCanSwimElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = false;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";

        when(duck.fly("if canSwim")).thenReturn(state);
        when(duck.fly("else done nest")).thenReturn(state3);

        String actual = ifElse.ifNestIfFoo(duck, canSwim, done);

        assertEquals(state3, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck, never()).swim("if done nest");
        verify(duck, never()).fly("if done nest");
        verify(duck, never()).dive(state2);
        verify(duck).swim("else done nest");
        verify(duck).dive(state3);
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
        verify(duck, never()).dive(state4);
    }

    @Test
    public void testIfNestIfFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = true;
        String state2 = "Foo";
        String state3 = "Bar";
        String state4 = "Baz";
        String state = "Qux";

        when(duck.fly("else canSwim")).thenReturn(state);

        String actual = ifElse.ifNestIfFoo(duck, canSwim, done);

        assertEquals(state, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("if done nest");
        verify(duck, never()).fly("if done nest");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("else done nest");
        verify(duck, never()).fly("else done nest");
        verify(duck, never()).dive(state4);
        verify(duck).swim("else canSwim");
        verify(duck).dive(state);
    }

    @Test
    public void testIfNestIfFoo2IfCanSwimIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";

        when(duck.fly("if canSwim")).thenReturn(state);
        when(duck.fly("if done nest")).thenReturn(state2);

        String actual = ifElse.ifNestIfFoo2(duck, canSwim, done);

        assertEquals(state2, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck).swim("if done nest");
        verify(duck).dive(state2);
        verify(duck, never()).swim("else done nest");
        verify(duck, never()).fly("else done nest");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
        verify(duck, never()).dive(state4);
        verify(duck, never()).swim("end");
    }

    @Test
    public void testIfNestIfFoo2IfCanSwimElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = false;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";

        when(duck.fly("if canSwim")).thenReturn(state);
        when(duck.fly("else done nest")).thenReturn(state3);

        String actual = ifElse.ifNestIfFoo2(duck, canSwim, done);

        assertEquals(state3, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck, never()).swim("if done nest");
        verify(duck, never()).fly("if done nest");
        verify(duck, never()).dive(state2);
        verify(duck).swim("else done nest");
        verify(duck).dive(state3);
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
        verify(duck, never()).dive(state4);
        verify(duck, never()).swim("end");
    }

    @Test
    public void testIfNestIfFoo2ElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = true;
        String state2 = "Foo";
        String state3 = "Bar";
        String state4 = "Baz";
        String state = "Qux";

        when(duck.fly("else canSwim")).thenReturn(state);

        String actual = ifElse.ifNestIfFoo2(duck, canSwim, done);

        assertEquals(state, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("if done nest");
        verify(duck, never()).fly("if done nest");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("else done nest");
        verify(duck, never()).fly("else done nest");
        verify(duck, never()).dive(state4);
        verify(duck).swim("else canSwim");
        verify(duck).dive(state);
        verify(duck).swim("end");
    }

    @Test
    public void testIfIfFooIfCanSwimIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";

        when(duck.fly("if canSwim")).thenReturn(state);

        String actual = ifElse.ifIfFoo(duck, canSwim, done);

        assertEquals(state, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("if done");
        verify(duck, never()).fly("if done");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("else done");
        verify(duck, never()).fly("else done");
        verify(duck, never()).dive(state4);
    }

    @Test
    public void testIfIfFooIfCanSwimElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";

        when(duck.fly("if canSwim")).thenReturn(state);

        String actual = ifElse.ifIfFoo(duck, canSwim, done);

        assertEquals(state, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("if done");
        verify(duck, never()).fly("if done");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("else done");
        verify(duck, never()).fly("else done");
        verify(duck, never()).dive(state4);
    }

    @Test
    public void testIfIfFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = true;
        String state2 = "Foo";
        String state = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";

        when(duck.fly("else canSwim")).thenReturn(state);
        when(duck.fly("if done")).thenReturn(state3);

        String actual = ifElse.ifIfFoo(duck, canSwim, done);

        assertEquals(state3, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck).swim("else canSwim");
        verify(duck).dive(state);
        verify(duck).swim("if done");
        verify(duck).dive(state3);
        verify(duck, never()).swim("else done");
        verify(duck, never()).fly("else done");
        verify(duck, never()).dive(state4);
    }

    @Test
    public void testIfIfFoo2IfCanSwimIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";

        when(duck.fly("if canSwim")).thenReturn(state);

        String actual = ifElse.ifIfFoo2(duck, canSwim, done);

        assertEquals(state, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("if done");
        verify(duck, never()).fly("if done");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("else done");
        verify(duck, never()).fly("else done");
        verify(duck, never()).dive(state4);
        verify(duck, never()).swim("end");
    }

    @Test
    public void testIfIfFoo2IfCanSwimElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";

        when(duck.fly("if canSwim")).thenReturn(state);

        String actual = ifElse.ifIfFoo2(duck, canSwim, done);

        assertEquals(state, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("if done");
        verify(duck, never()).fly("if done");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("else done");
        verify(duck, never()).fly("else done");
        verify(duck, never()).dive(state4);
        verify(duck, never()).swim("end");
    }

    @Test
    public void testIfIfFoo2ElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = true;
        String state2 = "Foo";
        String state = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";

        when(duck.fly("else canSwim")).thenReturn(state);
        when(duck.fly("if done")).thenReturn(state3);

        String actual = ifElse.ifIfFoo2(duck, canSwim, done);

        assertEquals(state3, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck).swim("else canSwim");
        verify(duck).dive(state);
        verify(duck).swim("if done");
        verify(duck).dive(state3);
        verify(duck, never()).swim("else done");
        verify(duck, never()).fly("else done");
        verify(duck, never()).dive(state4);
        verify(duck, never()).swim("end");
    }

    @Test
    public void testIfNestIfIfFooIfCanSwimIfCanDiveIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = true;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";
        String state5 = "Quux";
        String state6 = "Corge";

        when(duck.fly("if canSwim")).thenReturn(state);
        when(duck.fly("if canDive nest")).thenReturn(state2);

        String actual = ifElse.ifNestIfIfFoo(duck, canSwim, canDive, done);

        assertEquals(state2, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck).swim("if canDive nest");
        verify(duck).dive(state2);
        verify(duck, never()).swim("else canDive nest");
        verify(duck, never()).fly("else canDive nest");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("if done nest");
        verify(duck, never()).fly("if done nest");
        verify(duck, never()).dive(state4);
        verify(duck, never()).swim("else done nest");
        verify(duck, never()).fly("else done nest");
        verify(duck, never()).dive(state5);
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
        verify(duck, never()).dive(state6);
    }

    @Test
    public void testIfNestIfIfFooIfCanSwimIfCanDiveElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = true;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";
        String state5 = "Quux";
        String state6 = "Corge";

        when(duck.fly("if canSwim")).thenReturn(state);
        when(duck.fly("if canDive nest")).thenReturn(state2);

        String actual = ifElse.ifNestIfIfFoo(duck, canSwim, canDive, done);

        assertEquals(state2, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck).swim("if canDive nest");
        verify(duck).dive(state2);
        verify(duck, never()).swim("else canDive nest");
        verify(duck, never()).fly("else canDive nest");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("if done nest");
        verify(duck, never()).fly("if done nest");
        verify(duck, never()).dive(state4);
        verify(duck, never()).swim("else done nest");
        verify(duck, never()).fly("else done nest");
        verify(duck, never()).dive(state5);
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
        verify(duck, never()).dive(state6);
    }

    @Test
    public void testIfNestIfIfFooIfCanSwimElseCanDive() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = false;
        boolean done = true;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";
        String state5 = "Quux";
        String state6 = "Corge";

        when(duck.fly("if canSwim")).thenReturn(state);
        when(duck.fly("else canDive nest")).thenReturn(state3);
        when(duck.fly("if done nest")).thenReturn(state4);

        String actual = ifElse.ifNestIfIfFoo(duck, canSwim, canDive, done);

        assertEquals(state4, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck, never()).swim("if canDive nest");
        verify(duck, never()).fly("if canDive nest");
        verify(duck, never()).dive(state2);
        verify(duck).swim("else canDive nest");
        verify(duck).dive(state3);
        verify(duck).swim("if done nest");
        verify(duck).dive(state4);
        verify(duck, never()).swim("else done nest");
        verify(duck, never()).fly("else done nest");
        verify(duck, never()).dive(state5);
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
        verify(duck, never()).dive(state6);
    }

    @Test
    public void testIfNestIfIfFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean done = true;
        String state2 = "Foo";
        String state3 = "Bar";
        String state4 = "Baz";
        String state5 = "Qux";
        String state6 = "Quux";
        String state = "Corge";

        when(duck.fly("else canSwim")).thenReturn(state);

        String actual = ifElse.ifNestIfIfFoo(duck, canSwim, canDive, done);

        assertEquals(state, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("if canDive nest");
        verify(duck, never()).fly("if canDive nest");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("else canDive nest");
        verify(duck, never()).fly("else canDive nest");
        verify(duck, never()).dive(state4);
        verify(duck, never()).swim("if done nest");
        verify(duck, never()).fly("if done nest");
        verify(duck, never()).dive(state5);
        verify(duck, never()).swim("else done nest");
        verify(duck, never()).fly("else done nest");
        verify(duck, never()).dive(state6);
        verify(duck).swim("else canSwim");
        verify(duck).dive(state);
    }

    @Test
    public void testIfNestIfIfFoo2IfCanSwimIfCanDiveIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = true;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";
        String state5 = "Quux";
        String state6 = "Corge";

        when(duck.fly("if canSwim")).thenReturn(state);
        when(duck.fly("if canDive nest")).thenReturn(state2);

        String actual = ifElse.ifNestIfIfFoo2(duck, canSwim, canDive, done);

        assertEquals(state2, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck).swim("if canDive nest");
        verify(duck).dive(state2);
        verify(duck, never()).swim("else canDive nest");
        verify(duck, never()).fly("else canDive nest");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("if done nest");
        verify(duck, never()).fly("if done nest");
        verify(duck, never()).dive(state4);
        verify(duck, never()).swim("else done nest");
        verify(duck, never()).fly("else done nest");
        verify(duck, never()).dive(state5);
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
        verify(duck, never()).dive(state6);
        verify(duck, never()).swim("end");
    }

    @Test
    public void testIfNestIfIfFoo2IfCanSwimIfCanDiveElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = true;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";
        String state5 = "Quux";
        String state6 = "Corge";

        when(duck.fly("if canSwim")).thenReturn(state);
        when(duck.fly("if canDive nest")).thenReturn(state2);

        String actual = ifElse.ifNestIfIfFoo2(duck, canSwim, canDive, done);

        assertEquals(state2, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck).swim("if canDive nest");
        verify(duck).dive(state2);
        verify(duck, never()).swim("else canDive nest");
        verify(duck, never()).fly("else canDive nest");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("if done nest");
        verify(duck, never()).fly("if done nest");
        verify(duck, never()).dive(state4);
        verify(duck, never()).swim("else done nest");
        verify(duck, never()).fly("else done nest");
        verify(duck, never()).dive(state5);
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
        verify(duck, never()).dive(state6);
        verify(duck, never()).swim("end");
    }

    @Test
    public void testIfNestIfIfFoo2IfCanSwimElseCanDive() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = false;
        boolean done = true;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";
        String state5 = "Quux";
        String state6 = "Corge";

        when(duck.fly("if canSwim")).thenReturn(state);
        when(duck.fly("else canDive nest")).thenReturn(state3);
        when(duck.fly("if done nest")).thenReturn(state4);

        String actual = ifElse.ifNestIfIfFoo2(duck, canSwim, canDive, done);

        assertEquals(state4, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck, never()).swim("if canDive nest");
        verify(duck, never()).fly("if canDive nest");
        verify(duck, never()).dive(state2);
        verify(duck).swim("else canDive nest");
        verify(duck).dive(state3);
        verify(duck).swim("if done nest");
        verify(duck).dive(state4);
        verify(duck, never()).swim("else done nest");
        verify(duck, never()).fly("else done nest");
        verify(duck, never()).dive(state5);
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
        verify(duck, never()).dive(state6);
        verify(duck, never()).swim("end");
    }

    @Test
    public void testIfNestIfIfFoo2ElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean done = true;
        String state2 = "Foo";
        String state3 = "Bar";
        String state4 = "Baz";
        String state5 = "Qux";
        String state6 = "Quux";
        String state = "Corge";

        when(duck.fly("else canSwim")).thenReturn(state);

        String actual = ifElse.ifNestIfIfFoo2(duck, canSwim, canDive, done);

        assertEquals(state, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("if canDive nest");
        verify(duck, never()).fly("if canDive nest");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("else canDive nest");
        verify(duck, never()).fly("else canDive nest");
        verify(duck, never()).dive(state4);
        verify(duck, never()).swim("if done nest");
        verify(duck, never()).fly("if done nest");
        verify(duck, never()).dive(state5);
        verify(duck, never()).swim("else done nest");
        verify(duck, never()).fly("else done nest");
        verify(duck, never()).dive(state6);
        verify(duck).swim("else canSwim");
        verify(duck).dive(state);
        verify(duck, never()).swim("end");
    }

    @Test
    public void testIfNoAssignFooIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";
        String orange = "Bar";

        when(duck.fly("if canSwim")).thenReturn(apple);
        when(duck.toString()).thenReturn(orange);

        String actual = ifElse.ifNoAssignFoo(duck, canSwim);

        assertEquals(orange, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("end");
    }

    @Test
    public void testIfNoAssignFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String orange = "Baz";

        when(duck.toString()).thenReturn(orange);

        String actual = ifElse.ifNoAssignFoo(duck, canSwim);

        assertEquals(orange, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseNoAssignFooIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";
        String kiwi = "Bar";

        when(duck.fly("if canSwim")).thenReturn(apple);
        when(duck.toString()).thenReturn(kiwi);

        String actual = ifElse.ifElseNoAssignFoo(duck, canSwim);

        assertEquals(kiwi, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
    }

    @Test
    public void testIfElseNoAssignFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String orange = "Baz";
        String kiwi = "Qux";

        when(duck.fly("else canSwim")).thenReturn(orange);
        when(duck.toString()).thenReturn(kiwi);

        String actual = ifElse.ifElseNoAssignFoo(duck, canSwim);

        assertEquals(kiwi, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck).swim("else canSwim");
    }

    @Test
    public void testIfElseNoAssignFoo2IfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";
        String kiwi = "Bar";

        when(duck.fly("if canSwim")).thenReturn(apple);
        when(duck.toString()).thenReturn(kiwi);

        String actual = ifElse.ifElseNoAssignFoo2(duck, canSwim);

        assertEquals(kiwi, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
        verify(duck, never()).swim("end");
    }

    @Test
    public void testIfElseNoAssignFoo2ElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String orange = "Baz";
        String kiwi = "Qux";

        when(duck.fly("else canSwim")).thenReturn(orange);
        when(duck.toString()).thenReturn(kiwi);

        String actual = ifElse.ifElseNoAssignFoo2(duck, canSwim);

        assertEquals(kiwi, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck).swim("else canSwim");
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfNoAssignFooIfCanSwimIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String apple = "Foo";
        String grape = "Bar";
        String cherry = "Baz";

        when(duck.fly("if canSwim")).thenReturn(apple);
        when(duck.fly("if done nest")).thenReturn(grape);
        when(duck.toString()).thenReturn(cherry);

        String actual = ifElse.ifNestIfNoAssignFoo(duck, canSwim, done);

        assertEquals(cherry, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).swim("if done nest");
        verify(duck).swim("if done nest end");
        verify(duck, never()).swim("else done nest");
        verify(duck, never()).fly("else done nest");
        verify(duck, never()).swim("else done nest end");
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
        verify(duck, never()).swim("else canSwim end");
    }

    @Test
    public void testIfNestIfNoAssignFooIfCanSwimElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = false;
        String apple = "Foo";
        String kiwi = "Qux";
        String cherry = "Quux";

        when(duck.fly("if canSwim")).thenReturn(apple);
        when(duck.fly("else done nest")).thenReturn(kiwi);
        when(duck.toString()).thenReturn(cherry);

        String actual = ifElse.ifNestIfNoAssignFoo(duck, canSwim, done);

        assertEquals(cherry, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("if done nest");
        verify(duck, never()).fly("if done nest");
        verify(duck, never()).swim("if done nest end");
        verify(duck).swim("else done nest");
        verify(duck).swim("else done nest end");
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
        verify(duck, never()).swim("else canSwim end");
    }

    @Test
    public void testIfNestIfNoAssignFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = true;
        String banana = "Corge";
        String cherry = "Grault";

        when(duck.fly("else canSwim")).thenReturn(banana);
        when(duck.toString()).thenReturn(cherry);

        String actual = ifElse.ifNestIfNoAssignFoo(duck, canSwim, done);

        assertEquals(cherry, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).swim("if done nest");
        verify(duck, never()).fly("if done nest");
        verify(duck, never()).swim("if done nest end");
        verify(duck, never()).swim("else done nest");
        verify(duck, never()).fly("else done nest");
        verify(duck, never()).swim("else done nest end");
        verify(duck).swim("else canSwim");
        verify(duck).swim("else canSwim end");
    }

    @Test
    public void testIfNestIfNoAssignFoo2IfCanSwimIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String apple = "Foo";
        String grape = "Bar";
        String cherry = "Baz";

        when(duck.fly("if canSwim")).thenReturn(apple);
        when(duck.fly("if done nest")).thenReturn(grape);
        when(duck.toString()).thenReturn(cherry);

        String actual = ifElse.ifNestIfNoAssignFoo2(duck, canSwim, done);

        assertEquals(cherry, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).swim("if done nest");
        verify(duck).swim("if done nest end");
        verify(duck, never()).swim("else done nest");
        verify(duck, never()).fly("else done nest");
        verify(duck, never()).swim("else done nest end");
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
        verify(duck, never()).swim("else canSwim end");
        verify(duck, never()).swim("end");
    }

    @Test
    public void testIfNestIfNoAssignFoo2IfCanSwimElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = false;
        String apple = "Foo";
        String kiwi = "Qux";
        String cherry = "Grault";

        when(duck.fly("if canSwim")).thenReturn(apple);
        when(duck.fly("else done nest")).thenReturn(kiwi);
        when(duck.toString()).thenReturn(cherry);

        String actual = ifElse.ifNestIfNoAssignFoo2(duck, canSwim, done);

        assertEquals(cherry, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("if done nest");
        verify(duck, never()).fly("if done nest");
        verify(duck, never()).swim("if done nest end");
        verify(duck).swim("else done nest");
        verify(duck).swim("else done nest end");
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
        verify(duck, never()).swim("else canSwim end");
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfNoAssignFoo2ElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = true;
        String mango = "Quux";
        String cherry = "Corge";

        when(duck.fly("else canSwim")).thenReturn(mango);
        when(duck.toString()).thenReturn(cherry);

        String actual = ifElse.ifNestIfNoAssignFoo2(duck, canSwim, done);

        assertEquals(cherry, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).swim("if done nest");
        verify(duck, never()).fly("if done nest");
        verify(duck, never()).swim("if done nest end");
        verify(duck, never()).swim("else done nest");
        verify(duck, never()).fly("else done nest");
        verify(duck, never()).swim("else done nest end");
        verify(duck).swim("else canSwim");
        verify(duck).swim("else canSwim end");
        verify(duck, never()).swim("end");
    }

    @Test
    public void testIfIfNoAssignFooIfCanSwimIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String apple = "Foo";
        String cherry = "Bar";

        when(duck.fly("if canSwim")).thenReturn(apple);
        when(duck.toString()).thenReturn(cherry);

        String actual = ifElse.ifIfNoAssignFoo(duck, canSwim, done);

        assertEquals(cherry, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
        verify(duck, never()).swim("if done");
        verify(duck, never()).fly("if done");
        verify(duck, never()).swim("else done");
        verify(duck, never()).fly("else done");
    }

    @Test
    public void testIfIfNoAssignFooIfCanSwimElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String apple = "Foo";
        String cherry = "Bar";

        when(duck.fly("if canSwim")).thenReturn(apple);
        when(duck.toString()).thenReturn(cherry);

        String actual = ifElse.ifIfNoAssignFoo(duck, canSwim, done);

        assertEquals(cherry, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
        verify(duck, never()).swim("if done");
        verify(duck, never()).fly("if done");
        verify(duck, never()).swim("else done");
        verify(duck, never()).fly("else done");
    }

    @Test
    public void testIfIfNoAssignFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = true;
        String orange = "Baz";
        String kiwi = "Qux";
        String cherry = "Quux";

        when(duck.fly("else canSwim")).thenReturn(orange);
        when(duck.fly("if done")).thenReturn(kiwi);
        when(duck.toString()).thenReturn(cherry);

        String actual = ifElse.ifIfNoAssignFoo(duck, canSwim, done);

        assertEquals(cherry, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck).swim("else canSwim");
        verify(duck).swim("if done");
        verify(duck, never()).swim("else done");
        verify(duck, never()).fly("else done");
    }

    @Test
    public void testIfIfNoAssignFoo2IfCanSwimIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String apple = "Foo";
        String cherry = "Bar";

        when(duck.fly("if canSwim")).thenReturn(apple);
        when(duck.toString()).thenReturn(cherry);

        String actual = ifElse.ifIfNoAssignFoo2(duck, canSwim, done);

        assertEquals(cherry, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
        verify(duck, never()).swim("if done");
        verify(duck, never()).fly("if done");
        verify(duck, never()).swim("else done");
        verify(duck, never()).fly("else done");
        verify(duck, never()).swim("end");
    }

    @Test
    public void testIfIfNoAssignFoo2IfCanSwimElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String apple = "Foo";
        String cherry = "Bar";

        when(duck.fly("if canSwim")).thenReturn(apple);
        when(duck.toString()).thenReturn(cherry);

        String actual = ifElse.ifIfNoAssignFoo2(duck, canSwim, done);

        assertEquals(cherry, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
        verify(duck, never()).swim("if done");
        verify(duck, never()).fly("if done");
        verify(duck, never()).swim("else done");
        verify(duck, never()).fly("else done");
        verify(duck, never()).swim("end");
    }

    @Test
    public void testIfIfNoAssignFoo2ElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = true;
        String orange = "Baz";
        String kiwi = "Qux";
        String cherry = "Quux";

        when(duck.fly("else canSwim")).thenReturn(orange);
        when(duck.fly("if done")).thenReturn(kiwi);
        when(duck.toString()).thenReturn(cherry);

        String actual = ifElse.ifIfNoAssignFoo2(duck, canSwim, done);

        assertEquals(cherry, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck).swim("else canSwim");
        verify(duck).swim("if done");
        verify(duck, never()).swim("else done");
        verify(duck, never()).fly("else done");
        verify(duck, never()).swim("end");
    }

    @Test
    public void testIfNestIfIfNoAssignFooIfCanSwimIfCanDiveIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = true;
        String apple = "Foo";
        String grape = "Bar";
        String fig = "Baz";

        when(duck.fly("if canSwim")).thenReturn(apple);
        when(duck.fly("if canDive nest")).thenReturn(grape);
        when(duck.toString()).thenReturn(fig);

        String actual =
                ifElse.ifNestIfIfNoAssignFoo(duck, canSwim, canDive, done);

        assertEquals(fig, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).swim("if canDive nest");
        verify(duck, never()).swim("else canDive nest");
        verify(duck, never()).fly("else canDive nest");
        verify(duck, never()).swim("if done nest");
        verify(duck, never()).fly("if done nest");
        verify(duck, never()).swim("else done nest");
        verify(duck, never()).fly("else done nest");
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
    }

    @Test
    public void testIfNestIfIfNoAssignFooIfCanSwimIfCanDiveElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = true;
        String apple = "Foo";
        String grape = "Bar";
        String fig = "Baz";

        when(duck.fly("if canSwim")).thenReturn(apple);
        when(duck.fly("if canDive nest")).thenReturn(grape);
        when(duck.toString()).thenReturn(fig);

        String actual =
                ifElse.ifNestIfIfNoAssignFoo(duck, canSwim, canDive, done);

        assertEquals(fig, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).swim("if canDive nest");
        verify(duck, never()).swim("else canDive nest");
        verify(duck, never()).fly("else canDive nest");
        verify(duck, never()).swim("if done nest");
        verify(duck, never()).fly("if done nest");
        verify(duck, never()).swim("else done nest");
        verify(duck, never()).fly("else done nest");
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
    }

    @Test
    public void testIfNestIfIfNoAssignFooIfCanSwimElseCanDive() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = false;
        boolean done = true;
        String apple = "Foo";
        String kiwi = "Qux";
        String mango = "Quux";
        String fig = "Corge";

        when(duck.fly("if canSwim")).thenReturn(apple);
        when(duck.fly("else canDive nest")).thenReturn(kiwi);
        when(duck.fly("if done nest")).thenReturn(mango);
        when(duck.toString()).thenReturn(fig);

        String actual =
                ifElse.ifNestIfIfNoAssignFoo(duck, canSwim, canDive, done);

        assertEquals(fig, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("if canDive nest");
        verify(duck, never()).fly("if canDive nest");
        verify(duck).swim("else canDive nest");
        verify(duck).swim("if done nest");
        verify(duck, never()).swim("else done nest");
        verify(duck, never()).fly("else done nest");
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
    }

    @Test
    public void testIfNestIfIfNoAssignFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean done = true;
        String peach = "Waldo";
        String fig = "Fred";

        when(duck.fly("else canSwim")).thenReturn(peach);
        when(duck.toString()).thenReturn(fig);

        String actual =
                ifElse.ifNestIfIfNoAssignFoo(duck, canSwim, canDive, done);

        assertEquals(fig, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).swim("if canDive nest");
        verify(duck, never()).fly("if canDive nest");
        verify(duck, never()).swim("else canDive nest");
        verify(duck, never()).fly("else canDive nest");
        verify(duck, never()).swim("if done nest");
        verify(duck, never()).fly("if done nest");
        verify(duck, never()).swim("else done nest");
        verify(duck, never()).fly("else done nest");
        verify(duck).swim("else canSwim");
    }

    @Test
    public void testIfNestIfIfNoAssignFoo2IfCanSwimIfCanDiveIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = true;
        String apple = "Foo";
        String grape = "Bar";
        String fig = "Baz";

        when(duck.fly("if canSwim")).thenReturn(apple);
        when(duck.fly("if canDive nest")).thenReturn(grape);
        when(duck.toString()).thenReturn(fig);

        String actual =
                ifElse.ifNestIfIfNoAssignFoo2(duck, canSwim, canDive, done);

        assertEquals(fig, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).swim("if canDive nest");
        verify(duck, never()).swim("else canDive nest");
        verify(duck, never()).fly("else canDive nest");
        verify(duck, never()).swim("if done nest");
        verify(duck, never()).fly("if done nest");
        verify(duck, never()).swim("else done nest");
        verify(duck, never()).fly("else done nest");
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
        verify(duck, never()).swim("end");
    }

    @Test
    public void testIfNestIfIfNoAssignFoo2IfCanSwimIfCanDiveElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = true;
        String apple = "Foo";
        String grape = "Bar";
        String fig = "Baz";

        when(duck.fly("if canSwim")).thenReturn(apple);
        when(duck.fly("if canDive nest")).thenReturn(grape);
        when(duck.toString()).thenReturn(fig);

        String actual =
                ifElse.ifNestIfIfNoAssignFoo2(duck, canSwim, canDive, done);

        assertEquals(fig, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).swim("if canDive nest");
        verify(duck, never()).swim("else canDive nest");
        verify(duck, never()).fly("else canDive nest");
        verify(duck, never()).swim("if done nest");
        verify(duck, never()).fly("if done nest");
        verify(duck, never()).swim("else done nest");
        verify(duck, never()).fly("else done nest");
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
        verify(duck, never()).swim("end");
    }

    @Test
    public void testIfNestIfIfNoAssignFoo2IfCanSwimElseCanDive() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = false;
        boolean done = true;
        String apple = "Foo";
        String kiwi = "Qux";
        String mango = "Quux";
        String fig = "Corge";

        when(duck.fly("if canSwim")).thenReturn(apple);
        when(duck.fly("else canDive nest")).thenReturn(kiwi);
        when(duck.fly("if done nest")).thenReturn(mango);
        when(duck.toString()).thenReturn(fig);

        String actual =
                ifElse.ifNestIfIfNoAssignFoo2(duck, canSwim, canDive, done);

        assertEquals(fig, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("if canDive nest");
        verify(duck, never()).fly("if canDive nest");
        verify(duck).swim("else canDive nest");
        verify(duck).swim("if done nest");
        verify(duck, never()).swim("else done nest");
        verify(duck, never()).fly("else done nest");
        verify(duck, never()).swim("else canSwim");
        verify(duck, never()).fly("else canSwim");
        verify(duck, never()).swim("end");
    }

    @Test
    public void testIfNestIfIfNoAssignFoo2ElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean done = true;
        String peach = "Waldo";
        String fig = "Fred";

        when(duck.fly("else canSwim")).thenReturn(peach);
        when(duck.toString()).thenReturn(fig);

        String actual =
                ifElse.ifNestIfIfNoAssignFoo2(duck, canSwim, canDive, done);

        assertEquals(fig, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).swim("if canDive nest");
        verify(duck, never()).fly("if canDive nest");
        verify(duck, never()).swim("else canDive nest");
        verify(duck, never()).fly("else canDive nest");
        verify(duck, never()).swim("if done nest");
        verify(duck, never()).fly("if done nest");
        verify(duck, never()).swim("else done nest");
        verify(duck, never()).fly("else done nest");
        verify(duck).swim("else canSwim");
        verify(duck).swim("end");
    }
}
