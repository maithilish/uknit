package org.codetab.uknit.itest.generic;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class WildcardCaptureTest {
    @InjectMocks
    private WildcardCapture wildcardCapture;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testWrite() {
        PrintWriter out = Mockito.mock(PrintWriter.class);
        List<?> value = new ArrayList<>();
        wildcardCapture.write(out, value);

        // fail("unable to assert, STEPIN");
    }
}
