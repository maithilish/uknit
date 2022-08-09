package org.codetab.uknit.itest.ctlflow.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.codetab.uknit.itest.model.Duck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class IfPlusBlockTest {
    @InjectMocks
    private IfPlusBlock ifPlusBlock;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIfPlusBlockFooIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        List<String> names = new ArrayList<>();
        String name = "Foo";
        String apple = "Bar";
        names.add(name);

        when(duck.toString()).thenReturn(apple);

        String actual = ifPlusBlock.ifPlusBlockFoo(duck, canSwim, names);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if swim");
        verify(duck).swim(name);
        verify(duck).swim("end");
    }

    @Test
    public void testIfPlusBlockFooCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        List<String> names = new ArrayList<>();
        String name = "Foo";
        String apple = "Bar";
        names.add(name);

        when(duck.toString()).thenReturn(apple);

        String actual = ifPlusBlock.ifPlusBlockFoo(duck, canSwim, names);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if swim");
        verify(duck).swim(name);
        verify(duck).swim("end");
    }

    @Test
    public void testIfElsePlusBlockFooIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        List<String> names = new ArrayList<>();
        String name = "Foo";
        String apple = "Bar";
        names.add(name);

        when(duck.toString()).thenReturn(apple);

        String actual = ifPlusBlock.ifElsePlusBlockFoo(duck, canSwim, names);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck).swim(name);
        verify(duck).swim("end");
    }

    @Test
    public void testIfElsePlusBlockFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        List<String> names = new ArrayList<>();
        String name = "Foo";
        String apple = "Bar";
        names.add(name);

        when(duck.toString()).thenReturn(apple);

        String actual = ifPlusBlock.ifElsePlusBlockFoo(duck, canSwim, names);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck).swim("else");
        verify(duck).swim(name);
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfPlusBlockFooIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        List<String> names = new ArrayList<>();
        String name = "Foo";
        String apple = "Bar";
        names.add(name);

        when(duck.toString()).thenReturn(apple);

        String actual =
                ifPlusBlock.ifNestIfPlusBlockFoo(duck, canSwim, done, names);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck).swim("if if");
        verify(duck, never()).swim("if else");
        verify(duck, never()).swim("else");
        verify(duck).swim(name);
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfPlusBlockFooElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = false;
        List<String> names = new ArrayList<>();
        String name = "Foo";
        String apple = "Bar";
        names.add(name);

        when(duck.toString()).thenReturn(apple);

        String actual =
                ifPlusBlock.ifNestIfPlusBlockFoo(duck, canSwim, done, names);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("if if");
        verify(duck).swim("if else");
        verify(duck, never()).swim("else");
        verify(duck).swim(name);
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfPlusBlockFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = false;
        List<String> names = new ArrayList<>();
        String name = "Foo";
        String apple = "Bar";
        names.add(name);

        when(duck.toString()).thenReturn(apple);

        String actual =
                ifPlusBlock.ifNestIfPlusBlockFoo(duck, canSwim, done, names);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).swim("if if");
        verify(duck, never()).swim("if else");
        verify(duck).swim("else");
        verify(duck).swim(name);
        verify(duck).swim("end");
    }

    @Test
    public void testIfIfPlusBlockFooIfCanDive() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        List<String> names = new ArrayList<>();
        String name = "Foo";
        String apple = "Bar";
        names.add(name);

        when(duck.toString()).thenReturn(apple);

        String actual =
                ifPlusBlock.ifIfPlusBlockFoo(duck, canSwim, canDive, names);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if swim");
        verify(duck).swim("if dive");
        verify(duck).swim(name);
        verify(duck).swim("end");
    }

    @Test
    public void testIfIfPlusBlockFooCanDive() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = false;
        List<String> names = new ArrayList<>();
        String name = "Foo";
        String apple = "Bar";
        names.add(name);

        when(duck.toString()).thenReturn(apple);

        String actual =
                ifPlusBlock.ifIfPlusBlockFoo(duck, canSwim, canDive, names);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if swim");
        verify(duck, never()).swim("if dive");
        verify(duck).swim(name);
        verify(duck).swim("end");
    }

    @Test
    public void testIfIfPlusBlockFooIfCanDive2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        List<String> names = new ArrayList<>();
        String name = "Foo";
        String apple = "Bar";
        names.add(name);

        when(duck.toString()).thenReturn(apple);

        String actual =
                ifPlusBlock.ifIfPlusBlockFoo(duck, canSwim, canDive, names);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if swim");
        verify(duck).swim("if dive");
        verify(duck).swim(name);
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseTwicePlusBlockFooIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        List<String> names = new ArrayList<>();
        String name = "Foo";
        String apple = "Bar";
        names.add(name);

        when(duck.toString()).thenReturn(apple);

        String actual =
                ifPlusBlock.ifElseTwicePlusBlockFoo(duck, canSwim, done, names);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck).swim("if if");
        verify(duck, never()).swim("if else");
        verify(duck).swim(name);
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseTwicePlusBlockFooElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = false;
        List<String> names = new ArrayList<>();
        String name = "Foo";
        String apple = "Bar";
        names.add(name);

        when(duck.toString()).thenReturn(apple);

        String actual =
                ifPlusBlock.ifElseTwicePlusBlockFoo(duck, canSwim, done, names);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("else");
        verify(duck, never()).swim("if if");
        verify(duck).swim("if else");
        verify(duck).swim(name);
        verify(duck).swim("end");
    }

    @Test
    public void testIfElseTwicePlusBlockFooIfDone2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = true;
        List<String> names = new ArrayList<>();
        String name = "Foo";
        String apple = "Bar";
        names.add(name);

        when(duck.toString()).thenReturn(apple);

        String actual =
                ifPlusBlock.ifElseTwicePlusBlockFoo(duck, canSwim, done, names);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck).swim("else");
        verify(duck).swim("if if");
        verify(duck, never()).swim("if else");
        verify(duck).swim(name);
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfIfPlusBlockFooIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = true;
        List<String> names = new ArrayList<>();
        String name = "Foo";
        String apple = "Bar";
        names.add(name);

        when(duck.toString()).thenReturn(apple);

        String actual = ifPlusBlock.ifNestIfIfPlusBlockFoo(duck, canSwim,
                canDive, done, names);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck).swim("nest if");
        verify(duck, never()).swim("nest else");
        verify(duck).swim("nest if if");
        verify(duck, never()).swim("nest if else");
        verify(duck, never()).swim("else");
        verify(duck).swim(name);
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfIfPlusBlockFooElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = false;
        List<String> names = new ArrayList<>();
        String name = "Foo";
        String apple = "Bar";
        names.add(name);

        when(duck.toString()).thenReturn(apple);

        String actual = ifPlusBlock.ifNestIfIfPlusBlockFoo(duck, canSwim,
                canDive, done, names);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck).swim("nest if");
        verify(duck, never()).swim("nest else");
        verify(duck, never()).swim("nest if if");
        verify(duck).swim("nest if else");
        verify(duck, never()).swim("else");
        verify(duck).swim(name);
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfIfPlusBlockFooIfDone2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = false;
        boolean done = true;
        List<String> names = new ArrayList<>();
        String name = "Foo";
        String apple = "Bar";
        names.add(name);

        when(duck.toString()).thenReturn(apple);

        String actual = ifPlusBlock.ifNestIfIfPlusBlockFoo(duck, canSwim,
                canDive, done, names);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("nest if");
        verify(duck).swim("nest else");
        verify(duck).swim("nest if if");
        verify(duck, never()).swim("nest if else");
        verify(duck, never()).swim("else");
        verify(duck).swim(name);
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfIfPlusBlockFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean done = true;
        List<String> names = new ArrayList<>();
        String name = "Foo";
        String apple = "Bar";
        names.add(name);

        when(duck.toString()).thenReturn(apple);

        String actual = ifPlusBlock.ifNestIfIfPlusBlockFoo(duck, canSwim,
                canDive, done, names);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).swim("nest if");
        verify(duck, never()).swim("nest else");
        verify(duck, never()).swim("nest if if");
        verify(duck, never()).swim("nest if else");
        verify(duck).swim("else");
        verify(duck).swim(name);
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfNestBlockPlusBlockFooIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = true;
        List<String> lanes = new ArrayList<>();
        List<String> names = new ArrayList<>();
        String lane = "Foo";
        String name = "Bar";
        String apple = "Baz";
        names.add(name);
        lanes.add(lane);

        when(duck.toString()).thenReturn(apple);

        String actual = ifPlusBlock.ifNestIfNestBlockPlusBlockFoo(duck, canSwim,
                done, lanes, names);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck).swim("if if");
        verify(duck, never()).swim("if else");
        verify(duck).swim(lane);
        verify(duck, never()).swim("else");
        verify(duck).swim(name);
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfNestBlockPlusBlockFooElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean done = false;
        List<String> lanes = new ArrayList<>();
        List<String> names = new ArrayList<>();
        String lane = "Foo";
        String name = "Bar";
        String apple = "Baz";
        names.add(name);
        lanes.add(lane);

        when(duck.toString()).thenReturn(apple);

        String actual = ifPlusBlock.ifNestIfNestBlockPlusBlockFoo(duck, canSwim,
                done, lanes, names);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("if if");
        verify(duck).swim("if else");
        verify(duck).swim(lane);
        verify(duck, never()).swim("else");
        verify(duck).swim(name);
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfNestBlockPlusBlockFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean done = true;
        List<String> lanes = new ArrayList<>();
        List<String> names = new ArrayList<>();
        String lane = "Foo";
        String name = "Bar";
        String apple = "Baz";
        names.add(name);
        lanes.add(lane);

        when(duck.toString()).thenReturn(apple);

        String actual = ifPlusBlock.ifNestIfNestBlockPlusBlockFoo(duck, canSwim,
                done, lanes, names);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).swim("if if");
        verify(duck, never()).swim("if else");
        verify(duck, never()).swim(lane);
        verify(duck).swim("else");
        verify(duck).swim(name);
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfIfNestBlockPlusBlockFooIfDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = true;
        List<String> lanes = new ArrayList<>();
        List<String> names = new ArrayList<>();
        String lane = "Foo";
        String name = "Bar";
        String apple = "Baz";
        names.add(name);
        lanes.add(lane);

        when(duck.toString()).thenReturn(apple);

        String actual = ifPlusBlock.ifNestIfIfNestBlockPlusBlockFoo(duck,
                canSwim, canDive, done, lanes, names);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck).swim("nest if");
        verify(duck, never()).swim("nest else");
        verify(duck).swim("nest if if");
        verify(duck, never()).swim("nest if else");
        verify(duck).swim(lane);
        verify(duck, never()).swim("else");
        verify(duck).swim(name);
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfIfNestBlockPlusBlockFooElseDone() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = true;
        boolean done = false;
        List<String> lanes = new ArrayList<>();
        List<String> names = new ArrayList<>();
        String lane = "Foo";
        String name = "Bar";
        String apple = "Baz";
        names.add(name);
        lanes.add(lane);

        when(duck.toString()).thenReturn(apple);

        String actual = ifPlusBlock.ifNestIfIfNestBlockPlusBlockFoo(duck,
                canSwim, canDive, done, lanes, names);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck).swim("nest if");
        verify(duck, never()).swim("nest else");
        verify(duck, never()).swim("nest if if");
        verify(duck).swim("nest if else");
        verify(duck).swim(lane);
        verify(duck, never()).swim("else");
        verify(duck).swim(name);
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfIfNestBlockPlusBlockFooIfDone2() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        boolean canDive = false;
        boolean done = true;
        List<String> lanes = new ArrayList<>();
        List<String> names = new ArrayList<>();
        String lane = "Foo";
        String name = "Bar";
        String apple = "Baz";
        names.add(name);
        lanes.add(lane);

        when(duck.toString()).thenReturn(apple);

        String actual = ifPlusBlock.ifNestIfIfNestBlockPlusBlockFoo(duck,
                canSwim, canDive, done, lanes, names);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck).swim("if");
        verify(duck, never()).swim("nest if");
        verify(duck).swim("nest else");
        verify(duck).swim("nest if if");
        verify(duck, never()).swim("nest if else");
        verify(duck).swim(lane);
        verify(duck, never()).swim("else");
        verify(duck).swim(name);
        verify(duck).swim("end");
    }

    @Test
    public void testIfNestIfIfNestBlockPlusBlockFooElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        boolean canDive = true;
        boolean done = true;
        List<String> lanes = new ArrayList<>();
        List<String> names = new ArrayList<>();
        String lane = "Foo";
        String name = "Bar";
        String apple = "Baz";
        names.add(name);
        lanes.add(lane);

        when(duck.toString()).thenReturn(apple);

        String actual = ifPlusBlock.ifNestIfIfNestBlockPlusBlockFoo(duck,
                canSwim, canDive, done, lanes, names);

        assertEquals(apple, actual);
        verify(duck).swim("start");
        verify(duck, never()).swim("if");
        verify(duck, never()).swim("nest if");
        verify(duck, never()).swim("nest else");
        verify(duck, never()).swim("nest if if");
        verify(duck, never()).swim("nest if else");
        verify(duck, never()).swim(lane);
        verify(duck).swim("else");
        verify(duck).swim(name);
        verify(duck).swim("end");
    }
}
