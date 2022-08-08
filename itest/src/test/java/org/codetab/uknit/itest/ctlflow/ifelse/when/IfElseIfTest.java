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

public class IfElseIfTest {
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

        when(duck.fly("if")).thenReturn(state);

        String actual = ifElseIf.ifElseIf(duck, canSwim, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("else if");
        verify(duck, never()).fly("else if");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = true;
        String state = "Foo";

        when(duck.fly("else if")).thenReturn(state);

        String actual = ifElseIf.ifElseIf(duck, canSwim, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck).swim("else if");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = false;
        String state = null;

        String actual = ifElseIf.ifElseIf(duck, canSwim, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck, never()).swim("else if");
        verify(duck, never()).fly("else if");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfElseIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String state = "Foo";

        when(duck.fly("if")).thenReturn(state);

        String actual = ifElseIf.ifElseIfElse(duck, canSwim, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("else if");
        verify(duck, never()).fly("else if");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfElseIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = true;
        String state = "Foo";

        when(duck.fly("else if")).thenReturn(state);

        String actual = ifElseIf.ifElseIfElse(duck, canSwim, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck).swim("else if");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfElseElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = false;
        String state = "Foo";

        when(duck.fly("else")).thenReturn(state);

        String actual = ifElseIf.ifElseIfElse(duck, canSwim, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck, never()).swim("else if");
        verify(duck, never()).fly("else if");
        verify(duck).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = true;
        String state = "Foo";

        when(duck.fly("if")).thenReturn(state);

        String actual = ifElseIf.ifTwiceElseIf(duck, canSwim, canDive, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("else if once");
        verify(duck, never()).fly("else if once");
        verify(duck, never()).swim("else if twice");
        verify(duck, never()).fly("else if twice");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfIfCanDive() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean done = true;
        String state = "Foo";

        when(duck.fly("else if once")).thenReturn(state);

        String actual = ifElseIf.ifTwiceElseIf(duck, canSwim, canDive, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck).swim("else if once");
        verify(duck, never()).swim("else if twice");
        verify(duck, never()).fly("else if twice");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = false;
        boolean done = true;
        String state = "Foo";

        when(duck.fly("else if twice")).thenReturn(state);

        String actual = ifElseIf.ifTwiceElseIf(duck, canSwim, canDive, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck, never()).swim("else if once");
        verify(duck, never()).fly("else if once");
        verify(duck).swim("else if twice");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = false;
        boolean done = false;
        String state = null;

        String actual = ifElseIf.ifTwiceElseIf(duck, canSwim, canDive, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck, never()).swim("else if once");
        verify(duck, never()).fly("else if once");
        verify(duck, never()).swim("else if twice");
        verify(duck, never()).fly("else if twice");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = true;
        String state = "Foo";

        when(duck.fly("if")).thenReturn(state);

        String actual =
                ifElseIf.ifTwiceElseIfElse(duck, canSwim, canDive, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("else if once");
        verify(duck, never()).fly("else if once");
        verify(duck, never()).swim("else if twice");
        verify(duck, never()).fly("else if twice");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseIfCanDive() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean done = true;
        String state = "Foo";

        when(duck.fly("else if once")).thenReturn(state);

        String actual =
                ifElseIf.ifTwiceElseIfElse(duck, canSwim, canDive, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck).swim("else if once");
        verify(duck, never()).swim("else if twice");
        verify(duck, never()).fly("else if twice");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = false;
        boolean done = true;
        String state = "Foo";

        when(duck.fly("else if twice")).thenReturn(state);

        String actual =
                ifElseIf.ifTwiceElseIfElse(duck, canSwim, canDive, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck, never()).swim("else if once");
        verify(duck, never()).fly("else if once");
        verify(duck).swim("else if twice");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = false;
        boolean done = false;
        String state = null;

        when(duck.fly("else")).thenReturn(state);

        String actual =
                ifElseIf.ifTwiceElseIfElse(duck, canSwim, canDive, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck, never()).swim("else if once");
        verify(duck, never()).fly("else if once");
        verify(duck, never()).swim("else if twice");
        verify(duck, never()).fly("else if twice");
        verify(duck).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfTwiceIfCanSwim2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean canSwim2 = true;
        boolean canDive2 = true;
        String state = "Foo";

        when(duck.fly("if")).thenReturn(state);
        when(duck.fly("2 if")).thenReturn(state);

        String actual = ifElseIf.ifElseIfTwice(duck, canSwim, canDive, canSwim2,
                canDive2);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("else if once");
        verify(duck, never()).fly("else if once");
        verify(duck).swim("2 if");
        verify(duck, never()).swim("2 else if once");
        verify(duck, never()).fly("2 else if once");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfTwiceIfCanDive2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean canSwim2 = false;
        boolean canDive2 = true;
        String state = "Foo";

        when(duck.fly("if")).thenReturn(state);
        when(duck.fly("2 else if once")).thenReturn(state);

        String actual = ifElseIf.ifElseIfTwice(duck, canSwim, canDive, canSwim2,
                canDive2);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("else if once");
        verify(duck, never()).fly("else if once");
        verify(duck, never()).swim("2 if");
        verify(duck, never()).fly("2 if");
        verify(duck).swim("2 else if once");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfTwiceCanDive2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = false;
        boolean canSwim2 = false;
        boolean canDive2 = false;
        String state = "Foo";

        when(duck.fly("if")).thenReturn(state);

        String actual = ifElseIf.ifElseIfTwice(duck, canSwim, canDive, canSwim2,
                canDive2);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("else if once");
        verify(duck, never()).fly("else if once");
        verify(duck, never()).swim("2 if");
        verify(duck, never()).fly("2 if");
        verify(duck, never()).swim("2 else if once");
        verify(duck, never()).fly("2 else if once");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfTwiceIfCanSwim22() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean canSwim2 = true;
        boolean canDive2 = true;
        String state = "Foo";

        when(duck.fly("else if once")).thenReturn(state);
        when(duck.fly("2 if")).thenReturn(state);

        String actual = ifElseIf.ifElseIfTwice(duck, canSwim, canDive, canSwim2,
                canDive2);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck).swim("else if once");
        verify(duck).swim("2 if");
        verify(duck, never()).swim("2 else if once");
        verify(duck, never()).fly("2 else if once");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfTwiceIfCanSwim23() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = false;
        boolean canSwim2 = true;
        boolean canDive2 = true;
        String state = "Foo";

        when(duck.fly("2 if")).thenReturn(state);

        String actual = ifElseIf.ifElseIfTwice(duck, canSwim, canDive, canSwim2,
                canDive2);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck, never()).swim("else if once");
        verify(duck, never()).fly("else if once");
        verify(duck).swim("2 if");
        verify(duck, never()).swim("2 else if once");
        verify(duck, never()).fly("2 else if once");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseTwiceIfCanSwim2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = true;
        boolean canSwim2 = true;
        boolean canDive2 = true;
        boolean done2 = true;
        String state = "Foo";

        when(duck.fly("if")).thenReturn(state);
        when(duck.fly("2 if")).thenReturn(state);

        String actual = ifElseIf.ifTwiceElseIfElseTwice(duck, canSwim, canDive,
                done, canSwim2, canDive2, done2);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("else if once");
        verify(duck, never()).fly("else if once");
        verify(duck, never()).swim("else if twice");
        verify(duck, never()).fly("else if twice");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("2 if");
        verify(duck, never()).swim("2 else if once");
        verify(duck, never()).fly("2 else if once");
        verify(duck, never()).swim("2 else if twice");
        verify(duck, never()).fly("2 else if twice");
        verify(duck, never()).swim("2 else");
        verify(duck, never()).fly("2 else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseTwiceIfCanDive2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = true;
        boolean canSwim2 = false;
        boolean canDive2 = true;
        boolean done2 = true;
        String state = "Foo";

        when(duck.fly("if")).thenReturn(state);
        when(duck.fly("2 else if once")).thenReturn(state);

        String actual = ifElseIf.ifTwiceElseIfElseTwice(duck, canSwim, canDive,
                done, canSwim2, canDive2, done2);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("else if once");
        verify(duck, never()).fly("else if once");
        verify(duck, never()).swim("else if twice");
        verify(duck, never()).fly("else if twice");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck, never()).swim("2 if");
        verify(duck, never()).fly("2 if");
        verify(duck).swim("2 else if once");
        verify(duck, never()).swim("2 else if twice");
        verify(duck, never()).fly("2 else if twice");
        verify(duck, never()).swim("2 else");
        verify(duck, never()).fly("2 else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseTwiceIfDone2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = false;
        boolean done = false;
        boolean canSwim2 = false;
        boolean canDive2 = false;
        boolean done2 = true;
        String state = "Foo";

        when(duck.fly("if")).thenReturn(state);
        when(duck.fly("2 else if twice")).thenReturn(state);

        String actual = ifElseIf.ifTwiceElseIfElseTwice(duck, canSwim, canDive,
                done, canSwim2, canDive2, done2);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("else if once");
        verify(duck, never()).fly("else if once");
        verify(duck, never()).swim("else if twice");
        verify(duck, never()).fly("else if twice");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck, never()).swim("2 if");
        verify(duck, never()).fly("2 if");
        verify(duck, never()).swim("2 else if once");
        verify(duck, never()).fly("2 else if once");
        verify(duck).swim("2 else if twice");
        verify(duck, never()).swim("2 else");
        verify(duck, never()).fly("2 else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseTwiceElseDone2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = true;
        boolean canSwim2 = false;
        boolean canDive2 = false;
        boolean done2 = false;
        String state = "Foo";

        when(duck.fly("if")).thenReturn(state);
        when(duck.fly("2 else")).thenReturn(state);

        String actual = ifElseIf.ifTwiceElseIfElseTwice(duck, canSwim, canDive,
                done, canSwim2, canDive2, done2);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("else if once");
        verify(duck, never()).fly("else if once");
        verify(duck, never()).swim("else if twice");
        verify(duck, never()).fly("else if twice");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck, never()).swim("2 if");
        verify(duck, never()).fly("2 if");
        verify(duck, never()).swim("2 else if once");
        verify(duck, never()).fly("2 else if once");
        verify(duck, never()).swim("2 else if twice");
        verify(duck, never()).fly("2 else if twice");
        verify(duck).swim("2 else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseTwiceIfCanSwim22() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean done = false;
        boolean canSwim2 = true;
        boolean canDive2 = true;
        boolean done2 = true;
        String state = "Foo";

        when(duck.fly("else if once")).thenReturn(state);
        when(duck.fly("2 if")).thenReturn(state);

        String actual = ifElseIf.ifTwiceElseIfElseTwice(duck, canSwim, canDive,
                done, canSwim2, canDive2, done2);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck).swim("else if once");
        verify(duck, never()).swim("else if twice");
        verify(duck, never()).fly("else if twice");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("2 if");
        verify(duck, never()).swim("2 else if once");
        verify(duck, never()).fly("2 else if once");
        verify(duck, never()).swim("2 else if twice");
        verify(duck, never()).fly("2 else if twice");
        verify(duck, never()).swim("2 else");
        verify(duck, never()).fly("2 else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseTwiceIfCanSwim23() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = false;
        boolean done = true;
        boolean canSwim2 = true;
        boolean canDive2 = true;
        boolean done2 = true;
        String state = "Foo";

        when(duck.fly("else if twice")).thenReturn(state);
        when(duck.fly("2 if")).thenReturn(state);

        String actual = ifElseIf.ifTwiceElseIfElseTwice(duck, canSwim, canDive,
                done, canSwim2, canDive2, done2);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck, never()).swim("else if once");
        verify(duck, never()).fly("else if once");
        verify(duck).swim("else if twice");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("2 if");
        verify(duck, never()).swim("2 else if once");
        verify(duck, never()).fly("2 else if once");
        verify(duck, never()).swim("2 else if twice");
        verify(duck, never()).fly("2 else if twice");
        verify(duck, never()).swim("2 else");
        verify(duck, never()).fly("2 else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseTwiceIfCanSwim24() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = false;
        boolean done = false;
        boolean canSwim2 = true;
        boolean canDive2 = true;
        boolean done2 = true;
        String state = "Foo";

        when(duck.fly("else")).thenReturn(state);
        when(duck.fly("2 if")).thenReturn(state);

        String actual = ifElseIf.ifTwiceElseIfElseTwice(duck, canSwim, canDive,
                done, canSwim2, canDive2, done2);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck, never()).swim("else if once");
        verify(duck, never()).fly("else if once");
        verify(duck, never()).swim("else if twice");
        verify(duck, never()).fly("else if twice");
        verify(duck).swim("else");
        verify(duck).swim("2 if");
        verify(duck, never()).swim("2 else if once");
        verify(duck, never()).fly("2 else if once");
        verify(duck, never()).swim("2 else if twice");
        verify(duck, never()).fly("2 else if twice");
        verify(duck, never()).swim("2 else");
        verify(duck, never()).fly("2 else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfPlusIfIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = true;
        String state = "Foo";

        when(duck.fly("if")).thenReturn(state);
        when(duck.fly("plus if")).thenReturn(state);

        String actual = ifElseIf.ifElseIfPlusIf(duck, canSwim, canDive, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("else if");
        verify(duck, never()).fly("else if");
        verify(duck).swim("plus if");
        verify(duck, never()).swim("plus else");
        verify(duck, never()).fly("plus else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfPlusIfElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = false;
        String state = "Foo";

        when(duck.fly("if")).thenReturn(state);
        when(duck.fly("plus else")).thenReturn(state);

        String actual = ifElseIf.ifElseIfPlusIf(duck, canSwim, canDive, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("else if");
        verify(duck, never()).fly("else if");
        verify(duck, never()).swim("plus if");
        verify(duck, never()).fly("plus if");
        verify(duck).swim("plus else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfPlusIfIfDone2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean done = true;
        String state = "Foo";

        when(duck.fly("else if")).thenReturn(state);
        when(duck.fly("plus if")).thenReturn(state);

        String actual = ifElseIf.ifElseIfPlusIf(duck, canSwim, canDive, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck).swim("else if");
        verify(duck).swim("plus if");
        verify(duck, never()).swim("plus else");
        verify(duck, never()).fly("plus else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfPlusIfIfDone3() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = false;
        boolean done = true;
        String state = "Foo";

        when(duck.fly("plus if")).thenReturn(state);

        String actual = ifElseIf.ifElseIfPlusIf(duck, canSwim, canDive, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck, never()).swim("else if");
        verify(duck, never()).fly("else if");
        verify(duck).swim("plus if");
        verify(duck, never()).swim("plus else");
        verify(duck, never()).fly("plus else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfElsePlusIfIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = true;
        String state = "Foo";

        when(duck.fly("if")).thenReturn(state);
        when(duck.fly("plus if")).thenReturn(state);

        String actual =
                ifElseIf.ifElseIfElsePlusIf(duck, canSwim, canDive, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("else if");
        verify(duck, never()).fly("else if");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("plus if");
        verify(duck, never()).swim("plus else");
        verify(duck, never()).fly("plus else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfElsePlusIfElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = false;
        String state = "Foo";

        when(duck.fly("if")).thenReturn(state);
        when(duck.fly("plus else")).thenReturn(state);

        String actual =
                ifElseIf.ifElseIfElsePlusIf(duck, canSwim, canDive, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("else if");
        verify(duck, never()).fly("else if");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck, never()).swim("plus if");
        verify(duck, never()).fly("plus if");
        verify(duck).swim("plus else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfElsePlusIfIfDone2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean done = true;
        String state = "Foo";

        when(duck.fly("else if")).thenReturn(state);
        when(duck.fly("plus if")).thenReturn(state);

        String actual =
                ifElseIf.ifElseIfElsePlusIf(duck, canSwim, canDive, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck).swim("else if");
        verify(duck, never()).swim("else");
        verify(duck, never()).fly("else");
        verify(duck).swim("plus if");
        verify(duck, never()).swim("plus else");
        verify(duck, never()).fly("plus else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfElsePlusIfIfDone3() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = false;
        boolean done = true;
        String state = "Foo";

        when(duck.fly("else")).thenReturn(state);
        when(duck.fly("plus if")).thenReturn(state);

        String actual =
                ifElseIf.ifElseIfElsePlusIf(duck, canSwim, canDive, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck, never()).swim("else if");
        verify(duck, never()).fly("else if");
        verify(duck).swim("else");
        verify(duck).swim("plus if");
        verify(duck, never()).swim("plus else");
        verify(duck, never()).fly("plus else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfPlusIfIfDone4() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean canFlip = true;
        boolean canFly = true;
        boolean done = true;
        String state = "Foo";

        when(duck.fly("if")).thenReturn(state);
        when(duck.fly("plus if")).thenReturn(state);

        String actual = ifElseIf.ifElseIfPlusIf(duck, canSwim, canDive, canFlip,
                canFly, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("else if");
        verify(duck, never()).fly("else if");
        verify(duck, never()).swim("else if if");
        verify(duck, never()).fly("else if if");
        verify(duck, never()).swim("else if else");
        verify(duck, never()).fly("else if else");
        verify(duck).swim("plus if");
        verify(duck, never()).swim("plus else");
        verify(duck, never()).fly("plus else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfPlusIfElseDone2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = false;
        boolean canFlip = false;
        boolean canFly = false;
        boolean done = false;
        String state = "Foo";

        when(duck.fly("if")).thenReturn(state);
        when(duck.fly("plus else")).thenReturn(state);

        String actual = ifElseIf.ifElseIfPlusIf(duck, canSwim, canDive, canFlip,
                canFly, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("else if");
        verify(duck, never()).fly("else if");
        verify(duck, never()).swim("else if if");
        verify(duck, never()).fly("else if if");
        verify(duck, never()).swim("else if else");
        verify(duck, never()).fly("else if else");
        verify(duck, never()).swim("plus if");
        verify(duck, never()).fly("plus if");
        verify(duck).swim("plus else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfPlusIfIfDone5() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean canFlip = true;
        boolean canFly = true;
        boolean done = true;
        String state = "Foo";

        when(duck.fly("else if")).thenReturn(state);
        when(duck.fly("else if if")).thenReturn(state);
        when(duck.fly("plus if")).thenReturn(state);

        String actual = ifElseIf.ifElseIfPlusIf(duck, canSwim, canDive, canFlip,
                canFly, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck).swim("else if");
        verify(duck).swim("else if if");
        verify(duck, never()).swim("else if else");
        verify(duck, never()).fly("else if else");
        verify(duck).swim("plus if");
        verify(duck, never()).swim("plus else");
        verify(duck, never()).fly("plus else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfPlusIfIfDone6() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean canFlip = false;
        boolean canFly = true;
        boolean done = true;
        String state = "Foo";

        when(duck.fly("else if")).thenReturn(state);
        when(duck.fly("else if else")).thenReturn(state);
        when(duck.fly("plus if")).thenReturn(state);

        String actual = ifElseIf.ifElseIfPlusIf(duck, canSwim, canDive, canFlip,
                canFly, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck).swim("else if");
        verify(duck, never()).swim("else if if");
        verify(duck, never()).fly("else if if");
        verify(duck).swim("else if else");
        verify(duck).swim("plus if");
        verify(duck, never()).swim("plus else");
        verify(duck, never()).fly("plus else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfPlusIfIfDone7() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean canFlip = false;
        boolean canFly = false;
        boolean done = true;
        String state = "Foo";

        when(duck.fly("else if")).thenReturn(state);
        when(duck.fly("plus if")).thenReturn(state);

        String actual = ifElseIf.ifElseIfPlusIf(duck, canSwim, canDive, canFlip,
                canFly, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck).swim("else if");
        verify(duck, never()).swim("else if if");
        verify(duck, never()).fly("else if if");
        verify(duck, never()).swim("else if else");
        verify(duck, never()).fly("else if else");
        verify(duck).swim("plus if");
        verify(duck, never()).swim("plus else");
        verify(duck, never()).fly("plus else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfPlusIfIfDone8() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = false;
        boolean canFlip = false;
        boolean canFly = false;
        boolean done = true;
        String state = "Foo";

        when(duck.fly("plus if")).thenReturn(state);

        String actual = ifElseIf.ifElseIfPlusIf(duck, canSwim, canDive, canFlip,
                canFly, done);

        assertEquals(state, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).fly("if");
        verify(duck, never()).swim("else if");
        verify(duck, never()).fly("else if");
        verify(duck, never()).swim("else if if");
        verify(duck, never()).fly("else if if");
        verify(duck, never()).swim("else if else");
        verify(duck, never()).fly("else if else");
        verify(duck).swim("plus if");
        verify(duck, never()).swim("plus else");
        verify(duck, never()).fly("plus else");
        verify(duck).swim("end");
    }
}
