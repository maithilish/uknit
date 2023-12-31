package org.codetab.uknit.itest.ret;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class ArithmeticTest {
    @InjectMocks
    private Arithmetic arithmetic;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAdd() {
        int apple = 1 + 2;

        int actual = arithmetic.add();

        assertEquals(apple, actual);
    }

    @Test
    public void testSubstract() {
        int apple = 1 - 2;

        int actual = arithmetic.substract();

        assertEquals(apple, actual);
    }

    @Test
    public void testMultiply() {
        int apple = 2 * 3;

        int actual = arithmetic.multiply();

        assertEquals(apple, actual);
    }

    @Test
    public void testDivide() {
        int apple = 6 / 2;

        int actual = arithmetic.divide();

        assertEquals(apple, actual);
    }

    @Test
    public void testReminder() {
        int apple = 9 % 2;

        int actual = arithmetic.reminder();

        assertEquals(apple, actual);
    }

    @Test
    public void testConcat() {
        String apple = "a" + "b";

        String actual = arithmetic.concat();

        assertEquals(apple, actual);
    }
}
