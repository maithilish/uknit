package org.codetab.uknit.itest.operator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class BitwiseTest {
    @InjectMocks
    private Bitwise bitwise;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBitwiseAnd() {
        int result = 2;

        int actual = bitwise.bitwiseAnd();

        assertEquals(result, actual);
    }

    @Test
    public void testShift() {
        int val = 0x2222;
        int result = val << 2;

        int actual = bitwise.shift();

        assertEquals(result, actual);
    }

    @Test
    public void testUnsignedShift() {
        int val = 0x2222;
        int result = val >>> 2;

        int actual = bitwise.unsignedShift();

        assertEquals(result, actual);
    }

    @Test
    public void testToggle() {
        int val = -1;
        int result = ~val;

        int actual = bitwise.toggle();

        assertEquals(result, actual);
    }
}
