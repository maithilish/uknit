package org.codetab.uknit.jtest.array;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class ArrayInitializerTest {
    @InjectMocks
    private ArrayInitializer arrayInitializer;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPrimitiveTypeArrayInitilizer() {
        int[] foo = new int[] {1, 2, 3};

        int[] actual = arrayInitializer.primitiveTypeArrayInitilizer();

        assertArrayEquals(foo, actual);
    }

    @Test
    public void testTypeNameArrayInitilizer() {
        String[] foo = new String[] {"foo", "bar"};

        String[] actual = arrayInitializer.typeNameArrayInitilizer();

        assertArrayEquals(foo, actual);
    }

    @Test
    public void testPrimitiveTypeNoNew() {
        char ac[] = {'n', 'o'};

        char[] actual = arrayInitializer.primitiveTypeNoNew();

        assertArrayEquals(ac, actual);
    }

    @Test
    public void testTypeNameNoNew() {
        String foo[] = {"foo", "bar"};

        String[] actual = arrayInitializer.typeNameNoNew();

        assertArrayEquals(foo, actual);
    }
}
