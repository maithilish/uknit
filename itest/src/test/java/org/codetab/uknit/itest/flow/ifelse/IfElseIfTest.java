package org.codetab.uknit.itest.flow.ifelse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.flow.ifelse.Model.Duck;
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
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifElseIf(duck, canSwim, done);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("else if canSwim");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfElseCanSwimIfelse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifElseIf(duck, canSwim, done);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck).swim("else if canSwim");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfElseCanSwimElseelse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifElseIf(duck, canSwim, done);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).swim("else if canSwim");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfElseIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifElseIfElse(duck, canSwim, done);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("else if done");
        verify(duck, never()).swim("else canSwim done");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfElseElseCanSwimIfelse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifElseIfElse(duck, canSwim, done);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck).swim("else if done");
        verify(duck, never()).swim("else canSwim done");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfElseElseCanSwimElseelse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifElseIfElse(duck, canSwim, done);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).swim("else if done");
        verify(duck).swim("else canSwim done");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifTwiceElseIf(duck, canSwim, canDive, done);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("else if  canDive");
        verify(duck, never()).swim("else if done");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseCanSwimIfelse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean done = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifTwiceElseIf(duck, canSwim, canDive, done);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck).swim("else if  canDive");
        verify(duck, never()).swim("else if done");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseCanSwimElseelseIfelse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = false;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifTwiceElseIf(duck, canSwim, canDive, done);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).swim("else if  canDive");
        verify(duck).swim("else if done");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseCanSwimElseelseElseelse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = false;
        boolean done = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifTwiceElseIf(duck, canSwim, canDive, done);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).swim("else if  canDive");
        verify(duck, never()).swim("else if done");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual =
                ifElseIf.ifTwiceElseIfElse(duck, canSwim, canDive, done);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).swim("else if done");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseElseCanSwimIfelse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual =
                ifElseIf.ifTwiceElseIfElse(duck, canSwim, canDive, done);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck).swim("else if canDive");
        verify(duck, never()).swim("else if done");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseElseCanSwimElseelseIfelse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = false;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual =
                ifElseIf.ifTwiceElseIfElse(duck, canSwim, canDive, done);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).swim("else if canDive");
        verify(duck).swim("else if done");
        verify(duck, never()).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseElseCanSwimElseelseElseelse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = false;
        boolean done = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual =
                ifElseIf.ifTwiceElseIfElse(duck, canSwim, canDive, done);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).swim("else if done");
        verify(duck).swim("else");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfTwiceIfCanSwimIfCanSwim2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean canSwim2 = true;
        boolean canDive2 = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifElseIfTwice(duck, canSwim, canDive, canSwim2,
                canDive2);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("else if canDive");
        verify(duck).swim("if canSwim2");
        verify(duck, never()).swim("else if canSwim2");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfTwiceIfCanSwimElseCanSwim2Ifelse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = false;
        boolean canSwim2 = false;
        boolean canDive2 = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifElseIfTwice(duck, canSwim, canDive, canSwim2,
                canDive2);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).swim("if canSwim2");
        verify(duck).swim("else if canSwim2");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfTwiceIfCanSwimElseCanSwim2Elseelse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = false;
        boolean canSwim2 = false;
        boolean canDive2 = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifElseIfTwice(duck, canSwim, canDive, canSwim2,
                canDive2);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).swim("if canSwim2");
        verify(duck, never()).swim("else if canSwim2");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfTwiceElseCanSwimIfelse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean canSwim2 = true;
        boolean canDive2 = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifElseIfTwice(duck, canSwim, canDive, canSwim2,
                canDive2);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck).swim("else if canDive");
        verify(duck).swim("if canSwim2");
        verify(duck, never()).swim("else if canSwim2");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfTwiceElseCanSwimElseelse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = false;
        boolean canSwim2 = true;
        boolean canDive2 = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifElseIfTwice(duck, canSwim, canDive, canSwim2,
                canDive2);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).swim("else if canDive");
        verify(duck).swim("if canSwim2");
        verify(duck, never()).swim("else if canSwim2");
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
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifTwiceElseIfElseTwice(duck, canSwim, canDive,
                done, canSwim2, canDive2, done2);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).swim("else if done");
        verify(duck, never()).swim("else");
        verify(duck).swim("if canSwim2");
        verify(duck, never()).swim("else if canDive2");
        verify(duck, never()).swim("else if done2");
        verify(duck, never()).swim("else 2");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseTwiceIfCanSwimElseCanSwim2Ifelse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = false;
        boolean done = false;
        boolean canSwim2 = false;
        boolean canDive2 = true;
        boolean done2 = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifTwiceElseIfElseTwice(duck, canSwim, canDive,
                done, canSwim2, canDive2, done2);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).swim("else if done");
        verify(duck, never()).swim("else");
        verify(duck, never()).swim("if canSwim2");
        verify(duck).swim("else if canDive2");
        verify(duck, never()).swim("else if done2");
        verify(duck, never()).swim("else 2");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseTwiceElseCanSwim2ElseelseIfelse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = false;
        boolean done = false;
        boolean canSwim2 = false;
        boolean canDive2 = false;
        boolean done2 = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifTwiceElseIfElseTwice(duck, canSwim, canDive,
                done, canSwim2, canDive2, done2);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).swim("else if done");
        verify(duck, never()).swim("else");
        verify(duck, never()).swim("if canSwim2");
        verify(duck, never()).swim("else if canDive2");
        verify(duck).swim("else if done2");
        verify(duck, never()).swim("else 2");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseTwiceElseCanSwim2ElseelseElseelse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = false;
        boolean done = false;
        boolean canSwim2 = false;
        boolean canDive2 = false;
        boolean done2 = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifTwiceElseIfElseTwice(duck, canSwim, canDive,
                done, canSwim2, canDive2, done2);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).swim("else if done");
        verify(duck, never()).swim("else");
        verify(duck, never()).swim("if canSwim2");
        verify(duck, never()).swim("else if canDive2");
        verify(duck, never()).swim("else if done2");
        verify(duck).swim("else 2");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseTwiceElseCanSwimIfelse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean done = false;
        boolean canSwim2 = true;
        boolean canDive2 = false;
        boolean done2 = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifTwiceElseIfElseTwice(duck, canSwim, canDive,
                done, canSwim2, canDive2, done2);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck).swim("else if canDive");
        verify(duck, never()).swim("else if done");
        verify(duck, never()).swim("else");
        verify(duck).swim("if canSwim2");
        verify(duck, never()).swim("else if canDive2");
        verify(duck, never()).swim("else if done2");
        verify(duck, never()).swim("else 2");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseTwiceElseCanSwimElseelseIfelse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = false;
        boolean done = true;
        boolean canSwim2 = true;
        boolean canDive2 = false;
        boolean done2 = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifTwiceElseIfElseTwice(duck, canSwim, canDive,
                done, canSwim2, canDive2, done2);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).swim("else if canDive");
        verify(duck).swim("else if done");
        verify(duck, never()).swim("else");
        verify(duck).swim("if canSwim2");
        verify(duck, never()).swim("else if canDive2");
        verify(duck, never()).swim("else if done2");
        verify(duck, never()).swim("else 2");
        verify(duck).swim("end");
    }

    @Test
    public void testIfTwiceElseIfElseTwiceElseCanSwimElseelseElseelse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = false;
        boolean done = false;
        boolean canSwim2 = true;
        boolean canDive2 = false;
        boolean done2 = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifTwiceElseIfElseTwice(duck, canSwim, canDive,
                done, canSwim2, canDive2, done2);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).swim("else if done");
        verify(duck).swim("else");
        verify(duck).swim("if canSwim2");
        verify(duck, never()).swim("else if canDive2");
        verify(duck, never()).swim("else if done2");
        verify(duck, never()).swim("else 2");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfPlusIfIfCanSwimIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifElseIfPlusIf(duck, canSwim, canDive, done);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("else if canDive");
        verify(duck).swim("plus if done");
        verify(duck, never()).swim("plus else done");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfPlusIfIfCanSwimElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = false;
        boolean done = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifElseIfPlusIf(duck, canSwim, canDive, done);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).swim("plus if done");
        verify(duck).swim("plus else done");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfPlusIfElseCanSwimIfelse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifElseIfPlusIf(duck, canSwim, canDive, done);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck).swim("else if canDive");
        verify(duck).swim("plus if done");
        verify(duck, never()).swim("plus else done");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfPlusIfElseCanSwimElseelse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = false;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifElseIfPlusIf(duck, canSwim, canDive, done);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).swim("else if canDive");
        verify(duck).swim("plus if done");
        verify(duck, never()).swim("plus else done");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfElsePlusIfIfCanSwimIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual =
                ifElseIf.ifElseIfElsePlusIf(duck, canSwim, canDive, done);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).swim("else");
        verify(duck).swim("plus if done");
        verify(duck, never()).swim("plus else done");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfElsePlusIfIfCanSwimElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = false;
        boolean done = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual =
                ifElseIf.ifElseIfElsePlusIf(duck, canSwim, canDive, done);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).swim("else");
        verify(duck, never()).swim("plus if done");
        verify(duck).swim("plus else done");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfElsePlusIfElseCanSwimIfelse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual =
                ifElseIf.ifElseIfElsePlusIf(duck, canSwim, canDive, done);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck).swim("else if canDive");
        verify(duck, never()).swim("else");
        verify(duck).swim("plus if done");
        verify(duck, never()).swim("plus else done");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfElsePlusIfElseCanSwimElseelse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = false;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual =
                ifElseIf.ifElseIfElsePlusIf(duck, canSwim, canDive, done);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).swim("else if canDive");
        verify(duck).swim("else");
        verify(duck).swim("plus if done");
        verify(duck, never()).swim("plus else done");
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
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifElseIfPlusIf(duck, canSwim, canDive, canFlip,
                canFly, done);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).swim("if canFlip nest");
        verify(duck, never()).swim("else if canFly nest");
        verify(duck).swim("plus if done");
        verify(duck, never()).swim("plus else done");
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
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifElseIfPlusIf(duck, canSwim, canDive, canFlip,
                canFly, done);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).swim("if canFlip nest");
        verify(duck, never()).swim("else if canFly nest");
        verify(duck, never()).swim("plus if done");
        verify(duck).swim("plus else done");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfPlusIfElseCanSwimIfelseIfCanFlip() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean canFlip = true;
        boolean canFly = false;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifElseIfPlusIf(duck, canSwim, canDive, canFlip,
                canFly, done);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck).swim("else if canDive");
        verify(duck).swim("if canFlip nest");
        verify(duck, never()).swim("else if canFly nest");
        verify(duck).swim("plus if done");
        verify(duck, never()).swim("plus else done");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfPlusIfIfelseElseCanFlipIfelse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean canFlip = false;
        boolean canFly = true;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifElseIfPlusIf(duck, canSwim, canDive, canFlip,
                canFly, done);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck).swim("else if canDive");
        verify(duck, never()).swim("if canFlip nest");
        verify(duck).swim("else if canFly nest");
        verify(duck).swim("plus if done");
        verify(duck, never()).swim("plus else done");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfPlusIfIfelseElseCanFlipElseelse() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean canFlip = false;
        boolean canFly = false;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifElseIfPlusIf(duck, canSwim, canDive, canFlip,
                canFly, done);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck).swim("else if canDive");
        verify(duck, never()).swim("if canFlip nest");
        verify(duck, never()).swim("else if canFly nest");
        verify(duck).swim("plus if done");
        verify(duck, never()).swim("plus else done");
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseIfPlusIfElseCanSwimElseelse2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = false;
        boolean canFlip = false;
        boolean canFly = false;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifElseIf.ifElseIfPlusIf(duck, canSwim, canDive, canFlip,
                canFly, done);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).swim("else if canDive");
        verify(duck, never()).swim("if canFlip nest");
        verify(duck, never()).swim("else if canFly nest");
        verify(duck).swim("plus if done");
        verify(duck, never()).swim("plus else done");
        verify(duck).swim("end");
    }
}
