package org.codetab.uknit.itest.operator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class UnaryTest {
    @InjectMocks
    private Unary unary;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPositive() {
        int x = +1;

        int actual = unary.positive();

        assertEquals(x, actual);
    }

    @Test
    public void testNegetive() {
        int x = +1;

        int actual = unary.negetive();

        assertEquals(x, actual);
    }

    @Test
    public void testIncrement() {
        int x = 3;

        int actual = unary.increment();

        assertEquals(x, actual);
    }

    @Test
    public void testAssignIncrement() {
        int x = 1;
        int y = x++;

        int actual = unary.assignIncrement();

        assertEquals(y, actual);
    }

    @Test
    public void testIncrementAssign() {
        int x = 1;
        int y = ++x;

        int actual = unary.incrementAssign();

        assertEquals(y, actual);
    }

    @Test
    public void testDecrement() {
        int x = -1;

        int actual = unary.decrement();

        assertEquals(x, actual);
    }

    @Test
    public void testAssignDecrement() {
        int x = 1;
        int y = x--;

        int actual = unary.assignDecrement();

        assertEquals(y, actual);
    }

    @Test
    public void testDecrementAssign() {
        int x = 1;
        int y = --x;

        int actual = unary.decrementAssign();

        assertEquals(y, actual);
    }

    @Test
    public void testNegate() {

        boolean actual = unary.negate();

        assertFalse(actual);
    }
}
