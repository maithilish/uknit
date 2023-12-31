package org.codetab.uknit.itest.ret;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class BitwiseTest {
    @InjectMocks
    private Bitwise bitwise;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBitwiseAnd() {
        int bitmask = 0x000F;
        int val = 0x2222;
        int apple = val & bitmask;

        int actual = bitwise.bitwiseAnd();

        assertEquals(apple, actual);
    }

    @Test
    public void testShift() {
        int val = 0x2222;
        int apple = val << 2;

        int actual = bitwise.shift();

        assertEquals(apple, actual);
    }

    @Test
    public void testUnsignedShift() {
        int val = 0x2222;
        int apple = val >>> 2;

        int actual = bitwise.unsignedShift();

        assertEquals(apple, actual);
    }

    @Test
    public void testToggle() {
        int val = -1;
        int apple = ~val;

        int actual = bitwise.toggle();

        assertEquals(apple, actual);
    }
}
