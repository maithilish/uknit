package org.codetab.uknit.itest.array;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class CreatePrimitiveTest {
    @InjectMocks
    private CreatePrimitive createPrimitive;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateIntArray() {
        int[] anArray = new int[2];
        anArray[0] = 100;

        int[] actual = createPrimitive.createIntArray();

        assertArrayEquals(anArray, actual);
    }

    @Test
    public void testCreateIntTwoDimArray() {
        int[][] anArray = new int[2][3];
        anArray[0][0] = 100;

        int[][] actual = createPrimitive.createIntTwoDimArray();

        assertArrayEquals(anArray, actual);
    }

    @Test
    public void testDeclareAndCreateIntArray() {
        int[] anArray = new int[2];
        anArray[0] = 100;

        int[] actual = createPrimitive.declareAndCreateIntArray();

        assertArrayEquals(anArray, actual);
    }

    @Test
    public void testDeclareAndCreateIntTwoDimArray() {
        int[][] anArray = new int[2][4];
        anArray[0][1] = 100;

        int[][] actual = createPrimitive.declareAndCreateIntTwoDimArray();

        assertArrayEquals(anArray, actual);
    }

    @Test
    public void testCreateAndAcessIntArray() {
        int[] anArray = new int[2];
        anArray[0] = 100;
        anArray[1] = 200;

        int[] actual = createPrimitive.createAndAcessIntArray();

        assertArrayEquals(anArray, actual);
    }
}
