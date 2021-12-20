package org.codetab.uknit.itest.array;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.codetab.uknit.itest.model.Groups;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class CreateInferTest {
    @InjectMocks
    private CreateInfer createInfer;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCreate() {
        Groups<String> groups = Mockito.mock(Groups.class);
        String[] array = new String[3];

        String[] actual = createInfer.create(groups);

        assertArrayEquals(array, actual);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCreateInfer() {
        Groups<String> groups = Mockito.mock(Groups.class);
        int apple = 1;
        String[] array = new String[apple];

        when(groups.size()).thenReturn(apple);

        String[] actual = createInfer.createInfer(groups);

        assertArrayEquals(array, actual);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCreateTwoDim() {
        Groups<String> groups = Mockito.mock(Groups.class);
        String[][] array = new String[3][2];

        String[][] actual = createInfer.createTwoDim(groups);

        assertArrayEquals(array, actual);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCreateInferTwoDim() {
        Groups<String> groups = Mockito.mock(Groups.class);
        int apple = 1;
        String[][] array = new String[apple][1];

        when(groups.size()).thenReturn(apple);

        String[][] actual = createInfer.createInferTwoDim(groups);

        assertArrayEquals(array, actual);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCreateMultiInferTwoDim() {
        Groups<String> groups = Mockito.mock(Groups.class);
        int apple = 1;
        int grape = 1;
        String[][] array = new String[apple][grape];

        when(groups.size()).thenReturn(apple).thenReturn(grape);

        String[][] actual = createInfer.createMultiInferTwoDim(groups);

        assertArrayEquals(array, actual);
    }

    @Test
    public void testCreateArrayInMethodCall() {
        List<String> list = new ArrayList<>();
        String[] array = {};

        String[] actual = createInfer.createArrayInMethodCall(list);

        assertArrayEquals(array, actual);
    }
}
