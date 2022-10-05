package org.codetab.uknit.jtest.array;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class ArrayAccessTest {
    @InjectMocks
    private ArrayAccess arrayAccess;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPrimitiveType() {
        int apple = 5;

        int actual = arrayAccess.primitiveType();

        assertEquals(apple, actual);
    }

    @Test
    public void testTypeName() {
        String apple = "bar";

        String actual = arrayAccess.typeName();

        assertEquals(apple, actual);
    }
}
