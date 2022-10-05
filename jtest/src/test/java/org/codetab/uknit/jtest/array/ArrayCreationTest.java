package org.codetab.uknit.jtest.array;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class ArrayCreationTest {
    @InjectMocks
    private ArrayCreation arrayCreation;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPrimitiveType() {
        int[] foo = new int[5];

        int[] actual = arrayCreation.primitiveType();

        assertArrayEquals(foo, actual);
    }

    @Test
    public void testTypeName() {
        String[] foo = new String[4];

        String[] actual = arrayCreation.typeName();

        assertArrayEquals(foo, actual);
    }

    @Test
    public void testPrimitiveTypeDeclareThenCreate() {
        int[] foo = new int[5];

        int[] actual = arrayCreation.primitiveTypeDeclareThenCreate();

        assertArrayEquals(foo, actual);
    }

    @Test
    public void testTypeNameDeclareThenCreate() {
        String[] foo = new String[4];

        String[] actual = arrayCreation.typeNameDeclareThenCreate();

        assertArrayEquals(foo, actual);
    }

    @Test
    public void testPrimitiveTypeArrayInitilizer() {
        int[] foo = new int[] {1, 2, 3};

        int[] actual = arrayCreation.primitiveTypeArrayInitilizer();

        assertArrayEquals(foo, actual);
    }

    @Test
    public void testTypeNameArrayInitilizer() {
        String[] foo = new String[] {"foo", "bar"};

        String[] actual = arrayCreation.typeNameArrayInitilizer();

        assertArrayEquals(foo, actual);
    }
}
