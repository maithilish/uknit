package org.codetab.uknit.itest.array;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.codetab.uknit.itest.array.Model.Bar;
import org.codetab.uknit.itest.array.Model.Foo;
import org.codetab.uknit.itest.array.Model.Groups;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class InferTest {
    @InjectMocks
    private Infer infer;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        @SuppressWarnings("unchecked")
        Groups<String> groups = Mockito.mock(Groups.class);
        String[] array = new String[3];

        String[] actual = infer.create(groups);

        assertArrayEquals(array, actual);
    }

    @Test
    public void testCreateInfer() {
        @SuppressWarnings("unchecked")
        Groups<String> groups = Mockito.mock(Groups.class);
        int apple = 1;
        String[] array = new String[apple];

        when(groups.size()).thenReturn(apple);

        String[] actual = infer.createInfer(groups);

        assertArrayEquals(array, actual);
    }

    @Test
    public void testReturnCreateInfer() {
        @SuppressWarnings("unchecked")
        Groups<String> groups = Mockito.mock(Groups.class);
        int apple = 1;
        String[] grape = new String[apple];

        when(groups.size()).thenReturn(apple);

        String[] actual = infer.returnCreateInfer(groups);

        assertArrayEquals(grape, actual);
    }

    @Test
    public void testCreateTwoDim() {
        @SuppressWarnings("unchecked")
        Groups<String> groups = Mockito.mock(Groups.class);
        String[][] array = new String[3][2];

        String[][] actual = infer.createTwoDim(groups);

        assertArrayEquals(array, actual);
    }

    @Test
    public void testCreateInferTwoDim() {
        @SuppressWarnings("unchecked")
        Groups<String> groups = Mockito.mock(Groups.class);
        int apple = 1;
        String[][] array = new String[apple][1];

        when(groups.size()).thenReturn(apple);

        String[][] actual = infer.createInferTwoDim(groups);

        assertArrayEquals(array, actual);
    }

    @Test
    public void testCreateMultiInferTwoDim() {
        @SuppressWarnings("unchecked")
        Groups<String> groups = Mockito.mock(Groups.class);
        int apple = 1;
        int grape = 1;
        String[][] array = new String[apple][grape];

        when(groups.size()).thenReturn(apple).thenReturn(grape);

        String[][] actual = infer.createMultiInferTwoDim(groups);

        assertArrayEquals(array, actual);
    }

    @Test
    public void testReturnCreateMultiInferTwoDim() {
        @SuppressWarnings("unchecked")
        Groups<String> groups = Mockito.mock(Groups.class);
        int apple = 1;
        int grape = 1;
        String[][] orange = new String[apple][grape];

        when(groups.size()).thenReturn(apple).thenReturn(grape);

        String[][] actual = infer.returnCreateMultiInferTwoDim(groups);

        assertArrayEquals(orange, actual);
    }

    @Test
    public void testCreateArrayInMethodCall() {
        List<String> list = new ArrayList<>();
        String[] array = {};

        String[] actual = infer.createArrayInMethodCall(list);

        assertArrayEquals(array, actual);
    }

    @Test
    public void testReturnArrayInMethodCall() {
        List<String> list = new ArrayList<>();
        String[] apple = {};

        String[] actual = infer.returnArrayInMethodCall(list);

        assertArrayEquals(apple, actual);
    }

    @Test
    public void testInferInInitialize() {
        Foo foo = Mockito.mock(Foo.class);
        Bar bar = Mockito.mock(Bar.class);
        String apple = "Foo";
        String grape = "Bar";
        String[] array = new String[] {apple, grape};

        when(foo.name()).thenReturn(apple);
        when(bar.name()).thenReturn(grape);

        String[] actual = infer.inferInInitialize(foo, bar);

        assertArrayEquals(array, actual);
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

        String[] actual = infer.returnInferInInitialize(foo, bar);

        assertArrayEquals(orange, actual);
    }

    @Test
    public void testInferInAccess() {
        @SuppressWarnings("unchecked")
        Groups<Integer> groups = Mockito.mock(Groups.class);
        int apple = 1;
        int grape = 0;
        int orange = 0;
        int foo = 10;

        when(groups.size()).thenReturn(apple).thenReturn(grape)
                .thenReturn(orange);

        int actual = infer.inferInAccess(groups);

        assertEquals(foo, actual);
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

        int actual = infer.returnInferInAccess(groups);

        assertEquals(kiwi, actual);
    }
}
