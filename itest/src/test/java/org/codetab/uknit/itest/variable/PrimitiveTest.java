package org.codetab.uknit.itest.variable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class PrimitiveTest {
    @InjectMocks
    private Primitive primitive;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCharLiteral() {
        char capitalC = 'C';

        char actual = primitive.charLiteral();

        assertEquals(capitalC, actual);
    }

    @Test
    public void testIntLiteral() {
        int i = 100000;

        int actual = primitive.intLiteral();

        assertEquals(i, actual);
    }

    @Test
    public void testHexLiteral() {
        int hexVal = 0x1a;

        int actual = primitive.hexLiteral();

        assertEquals(hexVal, actual);
    }

    @Test
    public void testBinLiteral() {
        int binVal = 0b11010;

        int actual = primitive.binLiteral();

        assertEquals(binVal, actual);
    }

    @Test
    public void testBoolLiteral() {

        boolean actual = primitive.boolLiteral();

        assertTrue(actual);
    }

    @Test
    public void testByteLiteral() {
        int b = 100;

        byte actual = primitive.byteLiteral();

        assertEquals(b, actual);
    }

    @Test
    public void testShortLiteral() {
        int s = 10000;

        short actual = primitive.shortLiteral();

        assertEquals(s, actual);
    }

    @Test
    public void testDoubleLiteral() {
        double d1 = 123.4;

        double actual = primitive.doubleLiteral();

        assertEquals(d1, actual, 0);
    }

    @Test
    public void testDoubleELiteral() {
        double d2 = 1.234e2;

        double actual = primitive.doubleELiteral();

        assertEquals(d2, actual, 0);
    }

    @Test
    public void testFloatLiteral() {
        float f1 = 123.4f;

        float actual = primitive.floatLiteral();

        assertEquals(f1, actual, 0);
    }

    @Test
    public void testLongUnderscore() {
        long creditCardNumber = 1234_5678_9012_3456L;

        long actual = primitive.longUnderscore();

        assertEquals(creditCardNumber, actual);
    }

    @Test
    public void testFloatUnderscore() {
        float pi = 3.14_15F;

        float actual = primitive.floatUnderscore();

        assertEquals(pi, actual, 0);
    }

    @Test
    public void testHexByteUnderscore() {
        int hexBytes = 0xFF_EC_DE_5E;

        long actual = primitive.hexByteUnderscore();

        assertEquals(hexBytes, actual);
    }

    @Test
    public void testHexWordUnderscore() {
        int hexWords = 0xCAFE_BABE;

        long actual = primitive.hexWordUnderscore();

        assertEquals(hexWords, actual);
    }

    @Test
    public void testBinUnderscore() {
        int nybbles = 0b0010_0101;

        byte actual = primitive.binUnderscore();

        assertEquals(nybbles, actual);
    }

    @Test
    public void testBinWordUnderscore() {
        int bytes = 0b11010010_01101001_10010100_10010010;

        long actual = primitive.binWordUnderscore();

        assertEquals(bytes, actual);
    }
}
