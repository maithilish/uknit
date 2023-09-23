package org.codetab.uknit.itest.array;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class CreateTest {
    @InjectMocks
    private Create create;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateIntArray() {
        int[] anArray = new int[2];
        anArray[0] = 100;

        int[] actual = create.createIntArray();

        assertArrayEquals(anArray, actual);
    }

    @Test
    public void testCreateIntTwoDimArray() {
        int[][] anArray = new int[2][3];
        anArray[0][0] = 100;

        int[][] actual = create.createIntTwoDimArray();

        assertArrayEquals(anArray, actual);
    }

    @Test
    public void testDeclareAndCreateIntArray() {
        int[] anArray = new int[2];
        anArray[0] = 100;

        int[] actual = create.declareAndCreateIntArray();

        assertArrayEquals(anArray, actual);
    }

    @Test
    public void testDeclareAndCreateIntTwoDimArray() {
        int[][] anArray = new int[2][4];
        anArray[0][1] = 100;

        int[][] actual = create.declareAndCreateIntTwoDimArray();

        assertArrayEquals(anArray, actual);
    }

    @Test
    public void testCreateAndAcessIntArray() {
        int[] anArray = new int[2];
        anArray[0] = 100;
        anArray[1] = 200;

        int[] actual = create.createAndAcessIntArray();

        assertArrayEquals(anArray, actual);
    }

    @Test
    public void testCreateStringArray() {
        String[] anArray = new String[1];
        anArray[0] = "foo";

        String[] actual = create.createStringArray();

        assertArrayEquals(anArray, actual);
    }

    @Test
    public void testDeclareAndCreateStringArray() {
        String[] anArray = new String[1];
        anArray[0] = "foo";

        String[] actual = create.declareAndCreateStringArray();

        assertArrayEquals(anArray, actual);
    }

    @Test
    public void testCreateAndAcessStringArray() {
        String[] anArray = new String[1];
        anArray[0] = "foo";

        String[] actual = create.createAndAcessStringArray();

        assertArrayEquals(anArray, actual);
    }
}
