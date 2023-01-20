package org.codetab.uknit.itest.array;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class AccessTest {
    @InjectMocks
    private Access access;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAssignAccessPrimitive() {
        int foo = 10;

        int actual = access.assignAccessPrimitive();

        assertEquals(foo, actual);
    }

    @Test
    public void testReturnAccess() {
        int apple = 10;

        int actual = access.returnAccess();

        assertEquals(apple, actual);
    }
}
