package org.codetab.uknit.itest.operator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class ArithmeticTest {
    @InjectMocks
    private Arithmetic arithmetic;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAdd() {
        int result = 1 + 2;

        int actual = arithmetic.add();

        assertEquals(result, actual);
    }

    @Test
    public void testSubtract() {
        int result = 1 - 2;

        int actual = arithmetic.subtract();

        assertEquals(result, actual);
    }

    @Test
    public void testMultiply() {
        int result = 2 * 3;

        int actual = arithmetic.multiply();

        assertEquals(result, actual);
    }

    @Test
    public void testDivide() {
        int result = 8 / 2;

        int actual = arithmetic.divide();

        assertEquals(result, actual);
    }

    @Test
    public void testReminder() {
        int result = 9 % 2;

        int actual = arithmetic.reminder();

        assertEquals(result, actual);
    }

    @Test
    public void testConcat() {
        String ab = "a" + "b";

        String actual = arithmetic.concat();

        assertEquals(ab, actual);
    }
}
