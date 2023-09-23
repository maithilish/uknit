package org.codetab.uknit.itest.ret;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class UnaryTest {
    @InjectMocks
    private Unary unary;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPositive() {
        int apple = +1;

        int actual = unary.positive();

        assertEquals(apple, actual);
    }

    @Test
    public void testIncrement() {
        int x = 1;
        int apple = ++x;

        int actual = unary.increment();

        assertEquals(apple, actual);
    }

    @Test
    public void testReturnIncrement() {
        int x = 1;
        int apple = x++;

        int actual = unary.returnIncrement();

        assertEquals(apple, actual);
    }

    @Test
    public void testDecrement() {
        int x = 1;
        int apple = --x;

        int actual = unary.decrement();

        assertEquals(apple, actual);
    }

    @Test
    public void testReturnDecrement() {
        int x = 1;
        int apple = x--;

        int actual = unary.returnDecrement();

        assertEquals(apple, actual);
    }

    @Test
    public void testNegate() {

        boolean actual = unary.negate();

        assertFalse(actual);
    }
}
