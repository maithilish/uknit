package org.codetab.uknit.itest.generic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class CaptureTypeBindTest {
    @InjectMocks
    private CaptureTypeBind captureTypeBind;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFoo() throws Exception {
        String apple = "Foo";

        String actual = captureTypeBind.foo();

        assertEquals(apple, actual);
    }
}
