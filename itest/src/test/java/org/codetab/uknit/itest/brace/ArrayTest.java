package org.codetab.uknit.itest.brace;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.brace.Model.Bar;
import org.codetab.uknit.itest.brace.Model.Foo;
import org.codetab.uknit.itest.brace.Model.Groups;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class ArrayTest {
    @InjectMocks
    private Array array;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAssignAccessPrimitive() {
        int foo = 10;

        int actual = array.assignAccessPrimitive();

        assertEquals(foo, actual);
    }

    @Test
    public void testReturnAccess() {
        int apple = 10;

        int actual = array.returnAccess();

        assertEquals(apple, actual);
    }

    @Test
    public void testCreateIntTwoDimArray() {
        int[][] anArray = new int[(2)][(3)];
        anArray[(0)][(0)] = (100);

        int[][] actual = array.createIntTwoDimArray();

        assertArrayEquals(anArray, actual);
    }

    @Test
    public void testCreateMultiInferTwoDim() {
        @SuppressWarnings("unchecked")
        Groups<String> groups = Mockito.mock(Groups.class);
        int apple = 1;
        int grape = 1;
        String[][] expected = new String[apple][grape];

        when(groups.size()).thenReturn(apple).thenReturn(grape);

        String[][] actual = array.createMultiInferTwoDim(groups);

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testReturnMultiInferTwoDim() {
        @SuppressWarnings("unchecked")
        Groups<String> groups = Mockito.mock(Groups.class);
        int apple = 1;
        int grape = 1;
        String[][] orange = new String[apple][grape];

        when(groups.size()).thenReturn(apple).thenReturn(grape);

        String[][] actual = array.returnMultiInferTwoDim(groups);

        assertArrayEquals(orange, actual);
    }

    @Test
    public void testInferInInitialize() {
        Foo foo = Mockito.mock(Foo.class);
        Bar bar = Mockito.mock(Bar.class);
        String apple = "Foo";
        String grape = "Bar";
        String[] expected = new String[] {apple, grape};

        when(foo.name()).thenReturn(apple);
        when(bar.name()).thenReturn(grape);

        String[] actual = array.inferInInitialize(foo, bar);

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testReturnInferInInitialize() {
        Foo foo = Mockito.mock(Foo.class);
        Bar bar = Mockito.mock(Bar.class);
        String apple = "Foo";
        String grape = "Bar";
        String[] orange = new String[] {apple, grape};

        when(foo.name()).thenReturn(apple);
        when(bar.name()).thenReturn(grape);

        String[] actual = array.returnInferInInitialize(foo, bar);

        assertArrayEquals(orange, actual);
    }

    @Test
    public void testReturnInferInAccess() {
        @SuppressWarnings("unchecked")
        Groups<Integer> groups = Mockito.mock(Groups.class);
        int apple = 1;
        int grape = 0;
        int orange = 0;
        int kiwi = 10;

        when(groups.size()).thenReturn(apple).thenReturn(grape)
                .thenReturn(orange);

        int actual = array.returnInferInAccess(groups);

        assertEquals(kiwi, actual);
    }

    @Test
    public void testInitializeInArg() {
        StringBuilder s = Mockito.mock(StringBuilder.class);
        StringBuilder stringBuilder = Mockito.mock(StringBuilder.class);

        when(s.append((new String[] {("hello"), ("world")})))
                .thenReturn(stringBuilder);

        StringBuilder actual = array.initializeInArg(s);

        assertSame(s, actual);
    }

    @Test
    public void testInitializeMultiDim() {
        int[][] expected = {{(1), (2)}, {(3), (4)}};

        int[][] actual = array.initializeMultiDim();

        assertArrayEquals(expected, actual);
    }
}
