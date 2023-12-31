package org.codetab.uknit.itest.ret;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class UnmodifiablePrimitiveTest {
    @InjectMocks
    private UnmodifiablePrimitive unmodifiablePrimitive;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReturnbyte() {
        byte apple = 100;

        byte actual = unmodifiablePrimitive.returnbyte();

        assertEquals(apple, actual);
    }

    @Test
    public void testReturnbyteVar() {
        int value = 101;

        byte actual = unmodifiablePrimitive.returnbyteVar();

        assertEquals(value, actual);
    }

    @Test
    public void testReturnbyteVarConfigured() {
        byte value = 65;

        byte actual = unmodifiablePrimitive.returnbyteVarConfigured(value);

        assertEquals(value, actual);
    }

    @Test
    public void testReturnshort() {
        short apple = 7;

        short actual = unmodifiablePrimitive.returnshort();

        assertEquals(apple, actual);
    }

    @Test
    public void testReturnshortVar() {
        int value = 8;

        short actual = unmodifiablePrimitive.returnshortVar();

        assertEquals(value, actual);
    }

    @Test
    public void testReturnshortVarConfigured() {
        short value = 1;

        short actual = unmodifiablePrimitive.returnshortVarConfigured(value);

        assertEquals(value, actual);
    }

    @Test
    public void testReturnchar() {
        char apple = 'A';

        char actual = unmodifiablePrimitive.returnchar();

        assertEquals(apple, actual);
    }

    @Test
    public void testReturncharVar() {
        char value = 'B';

        char actual = unmodifiablePrimitive.returncharVar();

        assertEquals(value, actual);
    }

    @Test
    public void testReturncharVarConfigured() {
        char value = 'A';

        char actual = unmodifiablePrimitive.returncharVarConfigured(value);

        assertEquals(value, actual);
    }

    @Test
    public void testReturnint() {
        int apple = 7;

        int actual = unmodifiablePrimitive.returnint();

        assertEquals(apple, actual);
    }

    @Test
    public void testReturnintVar() {
        int value = 8;

        int actual = unmodifiablePrimitive.returnintVar();

        assertEquals(value, actual);
    }

    @Test
    public void testReturnintVarConfigured() {
        int value = 1;

        int actual = unmodifiablePrimitive.returnintVarConfigured(value);

        assertEquals(value, actual);
    }

    @Test
    public void testReturnlong() {
        long apple = 200L;

        long actual = unmodifiablePrimitive.returnlong();

        assertEquals(apple, actual);
    }

    @Test
    public void testReturnlongVar() {
        long value = 300L;

        long actual = unmodifiablePrimitive.returnlongVar();

        assertEquals(value, actual);
    }

    @Test
    public void testReturnlongVarConfigured() {
        long value = 1L;

        long actual = unmodifiablePrimitive.returnlongVarConfigured(value);

        assertEquals(value, actual);
    }

    @Test
    public void testReturnfloat() {
        float apple = 200.10f;

        float actual = unmodifiablePrimitive.returnfloat();

        assertEquals(apple, actual, 0);
    }

    @Test
    public void testReturnfloatVar() {
        float value = 300.20f;

        float actual = unmodifiablePrimitive.returnfloatVar();

        assertEquals(value, actual, 0);
    }

    @Test
    public void testReturnfloatVarConfigured() {
        float value = 1.0f;

        float actual = unmodifiablePrimitive.returnfloatVarConfigured(value);

        assertEquals(value, actual, 0);
    }

    @Test
    public void testReturndouble() {
        double apple = 400.10d;

        double actual = unmodifiablePrimitive.returndouble();

        assertEquals(apple, actual, 0);
    }

    @Test
    public void testReturndoubleVar() {
        double value = 500.20d;

        double actual = unmodifiablePrimitive.returndoubleVar();

        assertEquals(value, actual, 0);
    }

    @Test
    public void testReturndoubleVarConfigured() {
        double value = 1.0d;

        double actual = unmodifiablePrimitive.returndoubleVarConfigured(value);

        assertEquals(value, actual, 0);
    }

    @Test
    public void testReturnboolean() {

        boolean actual = unmodifiablePrimitive.returnboolean();

        assertTrue(actual);
    }

    @Test
    public void testReturnbooleanVar() {

        boolean actual = unmodifiablePrimitive.returnbooleanVar();

        assertFalse(actual);
    }

    @Test
    public void testReturnbooleanVarConfigured() {
        boolean value = true;

        boolean actual =
                unmodifiablePrimitive.returnbooleanVarConfigured(value);

        assertTrue(actual);
    }
}
