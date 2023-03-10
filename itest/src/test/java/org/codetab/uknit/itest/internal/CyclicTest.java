package org.codetab.uknit.itest.internal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class CyclicTest {
    @InjectMocks
    private Cyclic cyclic;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testImCallsImCallerA() {
        cyclic.imCallsImCallerA();

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testImCallsImCallerB() {
        cyclic.imCallsImCallerB();

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testImCallsBaseC() {
        int c = 5;
        cyclic.imCallsBaseC(c);

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testImCallsBaseD() {
        int c = 5;
        cyclic.imCallsBaseD(c);

        // fail("unable to assert, STEPIN");
    }
}
