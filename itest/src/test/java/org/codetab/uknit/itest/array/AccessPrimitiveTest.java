package org.codetab.uknit.itest.array;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class AccessPrimitiveTest {
    @InjectMocks
    private AccessPrimitive accessPrimitive;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAccess() {
        int foo = 10;

        int actual = accessPrimitive.access();

        assertEquals(foo, actual);
    }

    @Test
    public void testAccessAndReturn() {
        int grape = 10;

        int actual = accessPrimitive.accessAndReturn();

        assertEquals(grape, actual);
    }
}
