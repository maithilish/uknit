package org.codetab.uknit.itest.flow.ifelse.when;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.flow.ifelse.when.Model.Duck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class IfElseIfTest {
    @InjectMocks
    private IfElseIf ifElseIf;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIfElseIfIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String state = "Foo";
        String state2 = "Bar";

        when(duck.fly("if canSwim")).thenReturn(state);

        String actual = ifElseIf.ifElseIf(duck, canSwim, done);

        assertEquals(state, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck, never()).swim("else if done");
        verify(duck, never()).fly("else if done");
        verify(duck, never()).dive(state2);
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfElseCanSwimIfElse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = true;
        String state2 = "Foo";
        String state = "Bar";

        when(duck.fly("else if done")).thenReturn(state);

        String actual = ifElseIf.ifElseIf(duck, canSwim, done);

        assertEquals(state, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck).swim("else if done");
        verify(duck).dive(state);
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfElseCanSwimElseElse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = false;
        String state = null;
        String state2 = "Foo";
        String state3 = "Bar";

        String actual = ifElseIf.ifElseIf(duck, canSwim, done);

        assertEquals(state, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("else if done");
        verify(duck, never()).fly("else if done");
        verify(duck, never()).dive(state3);
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfElseIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";

        when(duck.fly("if canSwim")).thenReturn(state);

        String actual = ifElseIf.ifElseIfElse(duck, canSwim, done);

        assertEquals(state, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck, never()).swim("else if done");
        verify(duck, never()).fly("else if done");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck, never()).dive(state3);
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfElseElseCanSwimIfElse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = true;
        String state2 = "Foo";
        String state = "Bar";
        String state3 = "Baz";

        when(duck.fly("else if done")).thenReturn(state);

        String actual = ifElseIf.ifElseIfElse(duck, canSwim, done);

        assertEquals(state, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck).swim("else if done");
        verify(duck).dive(state);
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck, never()).dive(state3);
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfElseElseCanSwimElseElse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = false;
        String state2 = "Foo";
        String state3 = "Bar";
        String state = "Baz";

        when(duck.fly("else")).thenReturn(state);

        String actual = ifElseIf.ifElseIfElse(duck, canSwim, done);

        assertEquals(state, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("else if done");
        verify(duck, never()).fly("else if done");
        verify(duck, never()).dive(state3);
        verify(duck).swim("else");
        verify(duck).dive(state);
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = true;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";

        when(duck.fly("if canSwim")).thenReturn(state);

        String actual = ifElseIf.ifTwiceElseIf(duck, canSwim, canDive, done);

        assertEquals(state, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).fly("else if canDive");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("else if done");
        verify(duck, never()).fly("else if done");
        verify(duck, never()).dive(state3);
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseCanSwimIfElse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean done = false;
        String state2 = "Foo";
        String state = "Bar";
        String state3 = "Baz";

        when(duck.fly("else if canDive")).thenReturn(state);

        String actual = ifElseIf.ifTwiceElseIf(duck, canSwim, canDive, done);

        assertEquals(state, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck).swim("else if canDive");
        verify(duck).dive(state);
        verify(duck, never()).swim("else if done");
        verify(duck, never()).fly("else if done");
        verify(duck, never()).dive(state3);
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseCanSwimElseElseIfElse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = false;
        boolean done = true;
        String state2 = "Foo";
        String state3 = "Bar";
        String state = "Baz";

        when(duck.fly("else if done")).thenReturn(state);

        String actual = ifElseIf.ifTwiceElseIf(duck, canSwim, canDive, done);

        assertEquals(state, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).fly("else if canDive");
        verify(duck, never()).dive(state3);
        verify(duck).swim("else if done");
        verify(duck).dive(state);
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseCanSwimElseElseElseElse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = false;
        boolean done = false;
        String state = null;
        String state2 = "Foo";
        String state3 = "Bar";
        String state4 = "Baz";

        String actual = ifElseIf.ifTwiceElseIf(duck, canSwim, canDive, done);

        assertEquals(state, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).fly("else if canDive");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("else if done");
        verify(duck, never()).fly("else if done");
        verify(duck, never()).dive(state4);
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = true;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";

        when(duck.fly("if canSwim")).thenReturn(state);

        String actual =
                ifElseIf.ifTwiceElseIfElse(duck, canSwim, canDive, done);

        assertEquals(state, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).fly("else if canDive");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("else if done");
        verify(duck, never()).fly("else if done");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck, never()).dive(state4);
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseElseCanSwimIfElse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean done = false;
        String state2 = "Foo";
        String state = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";

        when(duck.fly("else if canDive")).thenReturn(state);

        String actual =
                ifElseIf.ifTwiceElseIfElse(duck, canSwim, canDive, done);

        assertEquals(state, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck).swim("else if canDive");
        verify(duck).dive(state);
        verify(duck, never()).swim("else if done");
        verify(duck, never()).fly("else if done");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck, never()).dive(state4);
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseElseCanSwimElseElseIfElse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = false;
        boolean done = true;
        String state2 = "Foo";
        String state3 = "Bar";
        String state = "Baz";
        String state4 = "Qux";

        when(duck.fly("else if done")).thenReturn(state);

        String actual =
                ifElseIf.ifTwiceElseIfElse(duck, canSwim, canDive, done);

        assertEquals(state, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).fly("else if canDive");
        verify(duck, never()).dive(state3);
        verify(duck).swim("else if done");
        verify(duck).dive(state);
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck, never()).dive(state4);
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseElseCanSwimElseElseElseElse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = false;
        boolean done = false;
        String state2 = "Foo";
        String state3 = "Bar";
        String state4 = "Baz";
        String state = "Qux";

        when(duck.fly("else")).thenReturn(state);

        String actual =
                ifElseIf.ifTwiceElseIfElse(duck, canSwim, canDive, done);

        assertEquals(state, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).fly("else if canDive");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("else if done");
        verify(duck, never()).fly("else if done");
        verify(duck, never()).dive(state4);
        verify(duck).swim("else");
        verify(duck).dive(state);
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfTwiceIfCanSwimIfCanSwim2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean canSwim2 = true;
        boolean canDive2 = true;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";

        when(duck.fly("if canSwim")).thenReturn(state);
        when(duck.fly("if canSwim2")).thenReturn(state3);

        String actual = ifElseIf.ifElseIfTwice(duck, canSwim, canDive, canSwim2,
                canDive2);

        assertEquals(state3, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).fly("else if canDive");
        verify(duck, never()).dive(state2);
        verify(duck).swim("if canSwim2");
        verify(duck).dive(state3);
        verify(duck, never()).swim("else if canDive2");
        verify(duck, never()).fly("else if canDive2");
        verify(duck, never()).dive(state4);
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfTwiceIfCanSwimElseCanSwim2IfElse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = false;
        boolean canSwim2 = false;
        boolean canDive2 = true;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";

        when(duck.fly("if canSwim")).thenReturn(state);
        when(duck.fly("else if canDive2")).thenReturn(state4);

        String actual = ifElseIf.ifElseIfTwice(duck, canSwim, canDive, canSwim2,
                canDive2);

        assertEquals(state4, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).fly("else if canDive");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("if canSwim2");
        verify(duck, never()).fly("if canSwim2");
        verify(duck, never()).dive(state3);
        verify(duck).swim("else if canDive2");
        verify(duck).dive(state4);
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfTwiceIfCanSwimElseCanSwim2ElseElse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = false;
        boolean canSwim2 = false;
        boolean canDive2 = false;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";

        when(duck.fly("if canSwim")).thenReturn(state);

        String actual = ifElseIf.ifElseIfTwice(duck, canSwim, canDive, canSwim2,
                canDive2);

        assertEquals(state, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).fly("else if canDive");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("if canSwim2");
        verify(duck, never()).fly("if canSwim2");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("else if canDive2");
        verify(duck, never()).fly("else if canDive2");
        verify(duck, never()).dive(state4);
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfTwiceElseCanSwimIfElse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean canSwim2 = true;
        boolean canDive2 = false;
        String state2 = "Foo";
        String state = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";

        when(duck.fly("else if canDive")).thenReturn(state);
        when(duck.fly("if canSwim2")).thenReturn(state3);

        String actual = ifElseIf.ifElseIfTwice(duck, canSwim, canDive, canSwim2,
                canDive2);

        assertEquals(state3, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck).swim("else if canDive");
        verify(duck).dive(state);
        verify(duck).swim("if canSwim2");
        verify(duck).dive(state3);
        verify(duck, never()).swim("else if canDive2");
        verify(duck, never()).fly("else if canDive2");
        verify(duck, never()).dive(state4);
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfTwiceElseCanSwimElseElse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = false;
        boolean canSwim2 = true;
        boolean canDive2 = false;
        String state2 = "Foo";
        String state3 = "Bar";
        String state = "Baz";
        String state4 = "Qux";

        when(duck.fly("if canSwim2")).thenReturn(state);

        String actual = ifElseIf.ifElseIfTwice(duck, canSwim, canDive, canSwim2,
                canDive2);

        assertEquals(state, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).fly("else if canDive");
        verify(duck, never()).dive(state3);
        verify(duck).swim("if canSwim2");
        verify(duck).dive(state);
        verify(duck, never()).swim("else if canDive2");
        verify(duck, never()).fly("else if canDive2");
        verify(duck, never()).dive(state4);
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseTwiceIfCanSwimIfCanSwim2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = true;
        boolean canSwim2 = true;
        boolean canDive2 = true;
        boolean done2 = true;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";
        String state5 = "Quux";
        String state6 = "Corge";
        String state7 = "Grault";
        String state8 = "Garply";

        when(duck.fly("if canSwim")).thenReturn(state);
        when(duck.fly("if canSwim2")).thenReturn(state5);

        String actual = ifElseIf.ifTwiceElseIfElseTwice(duck, canSwim, canDive,
                done, canSwim2, canDive2, done2);

        assertEquals(state5, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).fly("else if canDive");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("else if done");
        verify(duck, never()).fly("else if done");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck, never()).dive(state4);
        verify(duck).swim("if canSwim2");
        verify(duck).dive(state5);
        verify(duck, never()).swim("else if canDive2");
        verify(duck, never()).fly("else if canDive2");
        verify(duck, never()).dive(state6);
        verify(duck, never()).swim("else if done2");
        verify(duck, never()).fly("else if done2");
        verify(duck, never()).dive(state7);
        verify(duck, never()).swim("else 2");
        verify(duck, never()).fly("else 2");
        verify(duck, never()).dive(state8);
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseTwiceIfCanSwimElseCanSwim2IfElse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = false;
        boolean done = false;
        boolean canSwim2 = false;
        boolean canDive2 = true;
        boolean done2 = false;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";
        String state5 = "Quux";
        String state6 = "Corge";
        String state7 = "Grault";
        String state8 = "Garply";

        when(duck.fly("if canSwim")).thenReturn(state);
        when(duck.fly("else if canDive2")).thenReturn(state6);

        String actual = ifElseIf.ifTwiceElseIfElseTwice(duck, canSwim, canDive,
                done, canSwim2, canDive2, done2);

        assertEquals(state6, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).fly("else if canDive");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("else if done");
        verify(duck, never()).fly("else if done");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck, never()).dive(state4);
        verify(duck, never()).swim("if canSwim2");
        verify(duck, never()).fly("if canSwim2");
        verify(duck, never()).dive(state5);
        verify(duck).swim("else if canDive2");
        verify(duck).dive(state6);
        verify(duck, never()).swim("else if done2");
        verify(duck, never()).fly("else if done2");
        verify(duck, never()).dive(state7);
        verify(duck, never()).swim("else 2");
        verify(duck, never()).fly("else 2");
        verify(duck, never()).dive(state8);
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseTwiceElseCanSwim2ElseElseIfElse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = false;
        boolean done = false;
        boolean canSwim2 = false;
        boolean canDive2 = false;
        boolean done2 = true;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";
        String state5 = "Quux";
        String state6 = "Corge";
        String state7 = "Grault";
        String state8 = "Garply";

        when(duck.fly("if canSwim")).thenReturn(state);
        when(duck.fly("else if done2")).thenReturn(state7);

        String actual = ifElseIf.ifTwiceElseIfElseTwice(duck, canSwim, canDive,
                done, canSwim2, canDive2, done2);

        assertEquals(state7, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).fly("else if canDive");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("else if done");
        verify(duck, never()).fly("else if done");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck, never()).dive(state4);
        verify(duck, never()).swim("if canSwim2");
        verify(duck, never()).fly("if canSwim2");
        verify(duck, never()).dive(state5);
        verify(duck, never()).swim("else if canDive2");
        verify(duck, never()).fly("else if canDive2");
        verify(duck, never()).dive(state6);
        verify(duck).swim("else if done2");
        verify(duck).dive(state7);
        verify(duck, never()).swim("else 2");
        verify(duck, never()).fly("else 2");
        verify(duck, never()).dive(state8);
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseTwiceElseCanSwim2ElseElseElseElse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = false;
        boolean done = false;
        boolean canSwim2 = false;
        boolean canDive2 = false;
        boolean done2 = false;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";
        String state5 = "Quux";
        String state6 = "Corge";
        String state7 = "Grault";
        String state8 = "Garply";

        when(duck.fly("if canSwim")).thenReturn(state);
        when(duck.fly("else 2")).thenReturn(state8);

        String actual = ifElseIf.ifTwiceElseIfElseTwice(duck, canSwim, canDive,
                done, canSwim2, canDive2, done2);

        assertEquals(state8, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).fly("else if canDive");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("else if done");
        verify(duck, never()).fly("else if done");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck, never()).dive(state4);
        verify(duck, never()).swim("if canSwim2");
        verify(duck, never()).fly("if canSwim2");
        verify(duck, never()).dive(state5);
        verify(duck, never()).swim("else if canDive2");
        verify(duck, never()).fly("else if canDive2");
        verify(duck, never()).dive(state6);
        verify(duck, never()).swim("else if done2");
        verify(duck, never()).fly("else if done2");
        verify(duck, never()).dive(state7);
        verify(duck).swim("else 2");
        verify(duck).dive(state8);
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseTwiceElseCanSwimIfElse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean done = false;
        boolean canSwim2 = true;
        boolean canDive2 = false;
        boolean done2 = false;
        String state2 = "Foo";
        String state = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";
        String state5 = "Quux";
        String state6 = "Corge";
        String state7 = "Grault";
        String state8 = "Garply";

        when(duck.fly("else if canDive")).thenReturn(state);
        when(duck.fly("if canSwim2")).thenReturn(state5);

        String actual = ifElseIf.ifTwiceElseIfElseTwice(duck, canSwim, canDive,
                done, canSwim2, canDive2, done2);

        assertEquals(state5, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck).swim("else if canDive");
        verify(duck).dive(state);
        verify(duck, never()).swim("else if done");
        verify(duck, never()).fly("else if done");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck, never()).dive(state4);
        verify(duck).swim("if canSwim2");
        verify(duck).dive(state5);
        verify(duck, never()).swim("else if canDive2");
        verify(duck, never()).fly("else if canDive2");
        verify(duck, never()).dive(state6);
        verify(duck, never()).swim("else if done2");
        verify(duck, never()).fly("else if done2");
        verify(duck, never()).dive(state7);
        verify(duck, never()).swim("else 2");
        verify(duck, never()).fly("else 2");
        verify(duck, never()).dive(state8);
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseTwiceElseCanSwimElseElseIfElse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = false;
        boolean done = true;
        boolean canSwim2 = true;
        boolean canDive2 = false;
        boolean done2 = false;
        String state2 = "Foo";
        String state3 = "Bar";
        String state = "Baz";
        String state4 = "Qux";
        String state5 = "Quux";
        String state6 = "Corge";
        String state7 = "Grault";
        String state8 = "Garply";

        when(duck.fly("else if done")).thenReturn(state);
        when(duck.fly("if canSwim2")).thenReturn(state5);

        String actual = ifElseIf.ifTwiceElseIfElseTwice(duck, canSwim, canDive,
                done, canSwim2, canDive2, done2);

        assertEquals(state5, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).fly("else if canDive");
        verify(duck, never()).dive(state3);
        verify(duck).swim("else if done");
        verify(duck).dive(state);
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck, never()).dive(state4);
        verify(duck).swim("if canSwim2");
        verify(duck).dive(state5);
        verify(duck, never()).swim("else if canDive2");
        verify(duck, never()).fly("else if canDive2");
        verify(duck, never()).dive(state6);
        verify(duck, never()).swim("else if done2");
        verify(duck, never()).fly("else if done2");
        verify(duck, never()).dive(state7);
        verify(duck, never()).swim("else 2");
        verify(duck, never()).fly("else 2");
        verify(duck, never()).dive(state8);
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseTwiceElseCanSwimElseElseElseElse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = false;
        boolean done = false;
        boolean canSwim2 = true;
        boolean canDive2 = false;
        boolean done2 = false;
        String state2 = "Foo";
        String state3 = "Bar";
        String state4 = "Baz";
        String state = "Qux";
        String state5 = "Quux";
        String state6 = "Corge";
        String state7 = "Grault";
        String state8 = "Garply";

        when(duck.fly("else")).thenReturn(state);
        when(duck.fly("if canSwim2")).thenReturn(state5);

        String actual = ifElseIf.ifTwiceElseIfElseTwice(duck, canSwim, canDive,
                done, canSwim2, canDive2, done2);

        assertEquals(state5, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).fly("else if canDive");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("else if done");
        verify(duck, never()).fly("else if done");
        verify(duck, never()).dive(state4);
        verify(duck).swim("else");
        verify(duck).dive(state);
        verify(duck).swim("if canSwim2");
        verify(duck).dive(state5);
        verify(duck, never()).swim("else if canDive2");
        verify(duck, never()).fly("else if canDive2");
        verify(duck, never()).dive(state6);
        verify(duck, never()).swim("else if done2");
        verify(duck, never()).fly("else if done2");
        verify(duck, never()).dive(state7);
        verify(duck, never()).swim("else 2");
        verify(duck, never()).fly("else 2");
        verify(duck, never()).dive(state8);
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfPlusIfIfCanSwimIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = true;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";

        when(duck.fly("if canSwim")).thenReturn(state);
        when(duck.fly("if done")).thenReturn(state3);

        String actual = ifElseIf.ifElseIfPlusIf(duck, canSwim, canDive, done);

        assertEquals(state3, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).fly("else if canDive");
        verify(duck, never()).dive(state2);
        verify(duck).swim("if done");
        verify(duck).dive(state3);
        verify(duck, never()).swim("else done");
        verify(duck, never()).fly("else done");
        verify(duck, never()).dive(state4);
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfPlusIfIfCanSwimElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = false;
        boolean done = false;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";

        when(duck.fly("if canSwim")).thenReturn(state);
        when(duck.fly("else done")).thenReturn(state4);

        String actual = ifElseIf.ifElseIfPlusIf(duck, canSwim, canDive, done);

        assertEquals(state4, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).fly("else if canDive");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("if done");
        verify(duck, never()).fly("if done");
        verify(duck, never()).dive(state3);
        verify(duck).swim("else done");
        verify(duck).dive(state4);
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfPlusIfElseCanSwimIfElse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean done = true;
        String state2 = "Foo";
        String state = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";

        when(duck.fly("else if canDive")).thenReturn(state);
        when(duck.fly("if done")).thenReturn(state3);

        String actual = ifElseIf.ifElseIfPlusIf(duck, canSwim, canDive, done);

        assertEquals(state3, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck).swim("else if canDive");
        verify(duck).dive(state);
        verify(duck).swim("if done");
        verify(duck).dive(state3);
        verify(duck, never()).swim("else done");
        verify(duck, never()).fly("else done");
        verify(duck, never()).dive(state4);
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfPlusIfElseCanSwimElseElse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = false;
        boolean done = true;
        String state2 = "Foo";
        String state3 = "Bar";
        String state = "Baz";
        String state4 = "Qux";

        when(duck.fly("if done")).thenReturn(state);

        String actual = ifElseIf.ifElseIfPlusIf(duck, canSwim, canDive, done);

        assertEquals(state, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).fly("else if canDive");
        verify(duck, never()).dive(state3);
        verify(duck).swim("if done");
        verify(duck).dive(state);
        verify(duck, never()).swim("else done");
        verify(duck, never()).fly("else done");
        verify(duck, never()).dive(state4);
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfElsePlusIfIfCanSwimIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = true;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";
        String state5 = "Quux";

        when(duck.fly("if canSwim")).thenReturn(state);
        when(duck.fly("if done")).thenReturn(state4);

        String actual =
                ifElseIf.ifElseIfElsePlusIf(duck, canSwim, canDive, done);

        assertEquals(state4, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).fly("else if canDive");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck, never()).dive(state3);
        verify(duck).swim("if done");
        verify(duck).dive(state4);
        verify(duck, never()).swim("else done");
        verify(duck, never()).fly("else done");
        verify(duck, never()).dive(state5);
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfElsePlusIfIfCanSwimElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = false;
        boolean done = false;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";
        String state5 = "Quux";

        when(duck.fly("if canSwim")).thenReturn(state);
        when(duck.fly("else done")).thenReturn(state5);

        String actual =
                ifElseIf.ifElseIfElsePlusIf(duck, canSwim, canDive, done);

        assertEquals(state5, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).fly("else if canDive");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("if done");
        verify(duck, never()).fly("if done");
        verify(duck, never()).dive(state4);
        verify(duck).swim("else done");
        verify(duck).dive(state5);
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfElsePlusIfElseCanSwimIfElse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean done = true;
        String state2 = "Foo";
        String state = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";
        String state5 = "Quux";

        when(duck.fly("else if canDive")).thenReturn(state);
        when(duck.fly("if done")).thenReturn(state4);

        String actual =
                ifElseIf.ifElseIfElsePlusIf(duck, canSwim, canDive, done);

        assertEquals(state4, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck).swim("else if canDive");
        verify(duck).dive(state);
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck, never()).dive(state3);
        verify(duck).swim("if done");
        verify(duck).dive(state4);
        verify(duck, never()).swim("else done");
        verify(duck, never()).fly("else done");
        verify(duck, never()).dive(state5);
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfElsePlusIfElseCanSwimElseElse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = false;
        boolean done = true;
        String state2 = "Foo";
        String state3 = "Bar";
        String state = "Baz";
        String state4 = "Qux";
        String state5 = "Quux";

        when(duck.fly("else")).thenReturn(state);
        when(duck.fly("if done")).thenReturn(state4);

        String actual =
                ifElseIf.ifElseIfElsePlusIf(duck, canSwim, canDive, done);

        assertEquals(state4, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).fly("else if canDive");
        verify(duck, never()).dive(state3);
        verify(duck).swim("else");
        verify(duck).dive(state);
        verify(duck).swim("if done");
        verify(duck).dive(state4);
        verify(duck, never()).swim("else done");
        verify(duck, never()).fly("else done");
        verify(duck, never()).dive(state5);
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfPlusIfIfCanSwimIfDone2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean canFlip = true;
        boolean canFly = true;
        boolean done = true;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";
        String state5 = "Quux";
        String state6 = "Corge";

        when(duck.fly("if canSwim")).thenReturn(state);
        when(duck.fly("if done")).thenReturn(state5);

        String actual = ifElseIf.ifElseIfPlusIf(duck, canSwim, canDive, canFlip,
                canFly, done);

        assertEquals(state5, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).fly("else if canDive");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("else if canFlip nest");
        verify(duck, never()).fly("else if canFlip nest");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("else if canFly nest");
        verify(duck, never()).fly("else if canFly nest");
        verify(duck, never()).dive(state4);
        verify(duck).swim("if done");
        verify(duck).dive(state5);
        verify(duck, never()).swim("else done");
        verify(duck, never()).fly("else done");
        verify(duck, never()).dive(state6);
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfPlusIfIfCanSwimElseDone2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = false;
        boolean canFlip = false;
        boolean canFly = false;
        boolean done = false;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";
        String state5 = "Quux";
        String state6 = "Corge";

        when(duck.fly("if canSwim")).thenReturn(state);
        when(duck.fly("else done")).thenReturn(state6);

        String actual = ifElseIf.ifElseIfPlusIf(duck, canSwim, canDive, canFlip,
                canFly, done);

        assertEquals(state6, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).fly("else if canDive");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("else if canFlip nest");
        verify(duck, never()).fly("else if canFlip nest");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("else if canFly nest");
        verify(duck, never()).fly("else if canFly nest");
        verify(duck, never()).dive(state4);
        verify(duck, never()).swim("if done");
        verify(duck, never()).fly("if done");
        verify(duck, never()).dive(state5);
        verify(duck).swim("else done");
        verify(duck).dive(state6);
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfPlusIfElseCanSwimIfElseIfCanFlip() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean canFlip = true;
        boolean canFly = false;
        boolean done = true;
        String state2 = "Foo";
        String state = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";
        String state5 = "Quux";
        String state6 = "Corge";

        when(duck.fly("else if canDive")).thenReturn(state);
        when(duck.fly("else if canFlip nest")).thenReturn(state3);
        when(duck.fly("if done")).thenReturn(state5);

        String actual = ifElseIf.ifElseIfPlusIf(duck, canSwim, canDive, canFlip,
                canFly, done);

        assertEquals(state5, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck).swim("else if canDive");
        verify(duck).dive(state);
        verify(duck).swim("else if canFlip nest");
        verify(duck).dive(state3);
        verify(duck, never()).swim("else if canFly nest");
        verify(duck, never()).fly("else if canFly nest");
        verify(duck, never()).dive(state4);
        verify(duck).swim("if done");
        verify(duck).dive(state5);
        verify(duck, never()).swim("else done");
        verify(duck, never()).fly("else done");
        verify(duck, never()).dive(state6);
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfPlusIfIfElseElseCanFlipIfElse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean canFlip = false;
        boolean canFly = true;
        boolean done = true;
        String state2 = "Foo";
        String state = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";
        String state5 = "Quux";
        String state6 = "Corge";

        when(duck.fly("else if canDive")).thenReturn(state);
        when(duck.fly("else if canFly nest")).thenReturn(state4);
        when(duck.fly("if done")).thenReturn(state5);

        String actual = ifElseIf.ifElseIfPlusIf(duck, canSwim, canDive, canFlip,
                canFly, done);

        assertEquals(state5, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck).swim("else if canDive");
        verify(duck).dive(state);
        verify(duck, never()).swim("else if canFlip nest");
        verify(duck, never()).fly("else if canFlip nest");
        verify(duck, never()).dive(state3);
        verify(duck).swim("else if canFly nest");
        verify(duck).dive(state4);
        verify(duck).swim("if done");
        verify(duck).dive(state5);
        verify(duck, never()).swim("else done");
        verify(duck, never()).fly("else done");
        verify(duck, never()).dive(state6);
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfPlusIfIfElseElseCanFlipElseElse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean canFlip = false;
        boolean canFly = false;
        boolean done = true;
        String state2 = "Foo";
        String state = "Bar";
        String state3 = "Baz";
        String state4 = "Qux";
        String state5 = "Quux";
        String state6 = "Corge";

        when(duck.fly("else if canDive")).thenReturn(state);
        when(duck.fly("if done")).thenReturn(state5);

        String actual = ifElseIf.ifElseIfPlusIf(duck, canSwim, canDive, canFlip,
                canFly, done);

        assertEquals(state5, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck).swim("else if canDive");
        verify(duck).dive(state);
        verify(duck, never()).swim("else if canFlip nest");
        verify(duck, never()).fly("else if canFlip nest");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("else if canFly nest");
        verify(duck, never()).fly("else if canFly nest");
        verify(duck, never()).dive(state4);
        verify(duck).swim("if done");
        verify(duck).dive(state5);
        verify(duck, never()).swim("else done");
        verify(duck, never()).fly("else done");
        verify(duck, never()).dive(state6);
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfPlusIfElseCanSwimElseElse2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = false;
        boolean canFlip = false;
        boolean canFly = false;
        boolean done = true;
        String state2 = "Foo";
        String state3 = "Bar";
        String state4 = "Baz";
        String state5 = "Qux";
        String state = "Quux";
        String state6 = "Corge";

        when(duck.fly("if done")).thenReturn(state);

        String actual = ifElseIf.ifElseIfPlusIf(duck, canSwim, canDive, canFlip,
                canFly, done);

        assertEquals(state, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).dive(state2);
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).fly("else if canDive");
        verify(duck, never()).dive(state3);
        verify(duck, never()).swim("else if canFlip nest");
        verify(duck, never()).fly("else if canFlip nest");
        verify(duck, never()).dive(state4);
        verify(duck, never()).swim("else if canFly nest");
        verify(duck, never()).fly("else if canFly nest");
        verify(duck, never()).dive(state5);
        verify(duck).swim("if done");
        verify(duck).dive(state);
        verify(duck, never()).swim("else done");
        verify(duck, never()).fly("else done");
        verify(duck, never()).dive(state6);
        verify(duck).swim("end");
    }
}
