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

public class ElsePathTest {
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
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = elsePath.ifFoo(duck, canSwim);

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

        String actual = elsePath.ifFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck).swim("end");
    }

    @Test
    public void testIfPlusIfFooIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = elsePath.ifPlusIfFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).swim("if done");
        verify(duck).swim("end");
    }

    @Test
    public void testIfPlusIfFooDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = elsePath.ifPlusIfFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("if done");
        verify(duck).swim("end");
    }

    @Test
    public void testIfPlusIfFooIfDone2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = elsePath.ifPlusIfFoo(duck, canSwim, done);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck).swim("if done");
        verify(duck).swim("end");
    }

    @Test
    public void testTryPlusIfFooIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = elsePath.tryPlusIfFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("finally");
        verify(duck).swim("if");
        verify(duck).swim("end");
    }

    @Test
    public void testTryPlusIfFooCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = elsePath.tryPlusIfFoo(duck, canSwim);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("try");
        verify(duck).swim("finally");
        verify(duck, never()).swim("if");
        verify(duck).swim("end");
    }

    @Test
    public void testIfIfPlusIfFooIfEof() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        boolean eof = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = elsePath.ifIfPlusIfFoo(duck, canSwim, done, eof);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).swim("if done");
        verify(duck).swim("if eof");
        verify(duck).swim("end");
    }

    @Test
    public void testIfIfPlusIfFooEof() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        boolean eof = false;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = elsePath.ifIfPlusIfFoo(duck, canSwim, done, eof);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck).swim("if done");
        verify(duck, never()).swim("if eof");
        verify(duck).swim("end");
    }

    @Test
    public void testIfIfPlusIfFooIfEof2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = false;
        boolean eof = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = elsePath.ifIfPlusIfFoo(duck, canSwim, done, eof);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("if done");
        verify(duck).swim("if eof");
        verify(duck).swim("end");
    }

    @Test
    public void testIfIfPlusIfFooIfEof3() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = false;
        boolean eof = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = elsePath.ifIfPlusIfFoo(duck, canSwim, done, eof);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if canSwim");
        verify(duck, never()).swim("if done");
        verify(duck).swim("if eof");
        verify(duck).swim("end");
    }
}
