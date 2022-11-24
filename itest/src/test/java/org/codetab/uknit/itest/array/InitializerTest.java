package org.codetab.uknit.itest.array;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class InitializerTest {
    @InjectMocks
    private Initializer initializer;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInitialize() {
        String[] array = {"hello", "world"};

        String[] actual = initializer.initialize();

        assertArrayEquals(array, actual);
    }

    @Test
    public void testInitializeAndAssign() {
        String[] array = {"hello", "world"};
        String[] foo = array;

        String[] actual = initializer.initializeAndAssign();

        assertArrayEquals(foo, actual);
    }

    @Test
    public void testInitializeNew() {
        String[] array = new String[] {"hello", "world"};

        String[] actual = initializer.initializeNew();

        assertArrayEquals(array, actual);
    }

    @Test
    public void testInitializeInArg() {
        StringBuilder s = Mockito.mock(StringBuilder.class);
        StringBuilder stringBuilder = Mockito.mock(StringBuilder.class);

        when(s.append(new String[] {"hello", "world"}))
                .thenReturn(stringBuilder);

        StringBuilder actual = initializer.initializeInArg(s);

        assertSame(s, actual);
    }

    @Test
    public void testInitializeMultiDim() {
        int[][] array = {{1, 2}, {3, 4}};

        int[][] actual = initializer.initializeMultiDim();

        assertArrayEquals(array, actual);
    }
}
