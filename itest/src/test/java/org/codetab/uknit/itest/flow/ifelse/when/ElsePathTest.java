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

class ElsePathTest {
    @InjectMocks
    private ElsePath elsePath;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIfFooIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String state = "Foo";

        when(duck.fly("if canSwim")).thenReturn(state);

        String actual = elsePath.ifFoo(duck, canSwim);

        assertEquals(state, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).swim("end");
    }

    @Test
    public void testIfFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String state = null;

        String actual = elsePath.ifFoo(duck, canSwim);

        assertEquals(state, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck).swim("end");
    }

    @Test
    public void testIfPlusIfFooIfCanSwimIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String state = "Foo";
        String state2 = "Bar";

        when(duck.fly("if canSwim")).thenReturn(state);
        when(duck.fly("if done")).thenReturn(state2);

        String actual = elsePath.ifPlusIfFoo(duck, canSwim, done);

        assertEquals(state2, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).swim("if done");
        verify(duck).swim("end");
    }

    @Test
    public void testIfPlusIfFooIfCanSwimElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = false;
        String state = "Foo";

        when(duck.fly("if canSwim")).thenReturn(state);

        String actual = elsePath.ifPlusIfFoo(duck, canSwim, done);

        assertEquals(state, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("if done");
        verify(duck, never()).fly("if done");
        verify(duck).swim("end");
    }

    @Test
    public void testIfPlusIfFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = true;
        String state2 = "Bar";

        when(duck.fly("if done")).thenReturn(state2);

        String actual = elsePath.ifPlusIfFoo(duck, canSwim, done);

        assertEquals(state2, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck).swim("if done");
        verify(duck).swim("end");
    }

    @Test
    public void testTryPlusIfFooTryIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";

        when(duck.fly("try")).thenReturn(state);
        when(duck.fly("finally")).thenReturn(state2);
        when(duck.fly("if canSwim")).thenReturn(state3);

        String actual = elsePath.tryPlusIfFoo(duck, canSwim);

        assertEquals(state3, actual);

        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("finally");
        verify(duck).swim("if canSwim");
        verify(duck).swim("end");
    }

    @Test
    public void testTryPlusIfFooTryElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String state = "Foo";
        String state2 = "Bar";

        when(duck.fly("try")).thenReturn(state);
        when(duck.fly("finally")).thenReturn(state2);

        String actual = elsePath.tryPlusIfFoo(duck, canSwim);

        assertEquals(state2, actual);

        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("finally");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck).swim("end");
    }

    @Test
    public void testIfIfPlusIfFooIfCanSwimIfDoneIfEof() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        boolean eof = true;
        String state = "Foo";
        String apple = "Bar";
        String state2 = "Baz";
        String grape = "Qux";
        String state3 = "Quux";
        String orange = "Corge";

        when(duck.fly("if canSwim")).thenReturn(state);
        when(duck.fly(state)).thenReturn(apple);
        when(duck.fly("if done")).thenReturn(state2);
        when(duck.fly(state2)).thenReturn(grape);
        when(duck.fly("if eof")).thenReturn(state3);
        when(duck.fly(state3)).thenReturn(orange);

        String actual = elsePath.ifIfPlusIfFoo(duck, canSwim, done, eof);

        assertEquals(state3, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck).swim("if done");
        verify(duck).dive(state2);
        verify(duck).swim("if eof");
        verify(duck).dive(state3);
        verify(duck).swim("end");
    }

    @Test
    public void testIfIfPlusIfFooIfCanSwimIfDoneElseEof() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        boolean eof = false;
        String state = "Foo";
        String apple = "Bar";
        String state2 = "Baz";
        String grape = "Qux";
        String state3 = "Quux";

        when(duck.fly("if canSwim")).thenReturn(state);
        when(duck.fly(state)).thenReturn(apple);
        when(duck.fly("if done")).thenReturn(state2);
        when(duck.fly(state2)).thenReturn(grape);

        String actual = elsePath.ifIfPlusIfFoo(duck, canSwim, done, eof);

        assertEquals(state2, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck).swim("if done");
        verify(duck).dive(state2);
        verify(duck, never()).swim("if eof");
        verify(duck, never()).fly("if eof");
        verify(duck, never()).fly(state3);
        verify(duck, never()).dive(state3);
        verify(duck).swim("end");
    }

    @Test
    public void testIfIfPlusIfFooIfCanSwimElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = false;
        boolean eof = true;
        String state = "Foo";
        String apple = "Bar";
        String state2 = "Baz";
        String state3 = "Quux";
        String orange = "Corge";

        when(duck.fly("if canSwim")).thenReturn(state);
        when(duck.fly(state)).thenReturn(apple);
        when(duck.fly("if eof")).thenReturn(state3);
        when(duck.fly(state3)).thenReturn(orange);

        String actual = elsePath.ifIfPlusIfFoo(duck, canSwim, done, eof);

        assertEquals(state3, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).dive(state);
        verify(duck, never()).swim("if done");
        verify(duck, never()).fly("if done");
        verify(duck, never()).fly(state2);
        verify(duck, never()).dive(state2);
        verify(duck).swim("if eof");
        verify(duck).dive(state3);
        verify(duck).swim("end");
    }

    @Test
    public void testIfIfPlusIfFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = false;
        boolean eof = true;
        String state = "Foo";
        String state2 = "Baz";
        String state3 = "Quux";
        String orange = "Corge";

        when(duck.fly("if eof")).thenReturn(state3);
        when(duck.fly(state3)).thenReturn(orange);

        String actual = elsePath.ifIfPlusIfFoo(duck, canSwim, done, eof);

        assertEquals(state3, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).fly(state);
        verify(duck, never()).dive(state);
        verify(duck, never()).swim("if done");
        verify(duck, never()).fly("if done");
        verify(duck, never()).fly(state2);
        verify(duck, never()).dive(state2);
        verify(duck).swim("if eof");
        verify(duck).dive(state3);
        verify(duck).swim("end");
    }

    @Test
    public void testAssignAndReturnIfCanSwimIfDoneIfEof() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        boolean eof = true;
        String state = "Foo";
        String state2 = "Bar";
        String state3 = "Baz";
        String result = state3;

        when(duck.fly("if canSwim")).thenReturn(state);
        when(duck.fly("if done")).thenReturn(state2);
        when(duck.fly("if eof")).thenReturn(state3);

        String actual = elsePath.assignAndReturn(duck, canSwim, done, eof);

        assertEquals(result, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).swim("if done");
        verify(duck).swim("if eof");
        verify(duck).swim("end");
    }

    @Test
    public void testAssignAndReturnIfCanSwimIfDoneElseEof() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        boolean eof = false;
        String state = "Foo";
        String state2 = "Bar";
        String result = state2;

        when(duck.fly("if canSwim")).thenReturn(state);
        when(duck.fly("if done")).thenReturn(state2);

        String actual = elsePath.assignAndReturn(duck, canSwim, done, eof);

        assertEquals(result, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).swim("if done");
        verify(duck, never()).swim("if eof");
        verify(duck, never()).fly("if eof");
        verify(duck).swim("end");
    }

    @Test
    public void testAssignAndReturnIfCanSwimElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = false;
        boolean eof = true;
        String state = "Foo";
        String state3 = "Baz";
        String result = state3;

        when(duck.fly("if canSwim")).thenReturn(state);
        when(duck.fly("if eof")).thenReturn(state3);

        String actual = elsePath.assignAndReturn(duck, canSwim, done, eof);

        assertEquals(result, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("if done");
        verify(duck, never()).fly("if done");
        verify(duck).swim("if eof");
        verify(duck).swim("end");
    }

    @Test
    public void testAssignAndReturnElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = false;
        boolean eof = true;
        String state3 = "Baz";
        String result = state3;

        when(duck.fly("if eof")).thenReturn(state3);

        String actual = elsePath.assignAndReturn(duck, canSwim, done, eof);

        assertEquals(result, actual);

        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).fly("if canSwim");
        verify(duck, never()).swim("if done");
        verify(duck, never()).fly("if done");
        verify(duck).swim("if eof");
        verify(duck).swim("end");
    }
}
