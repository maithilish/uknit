package org.codetab.uknit.itest.internal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class RecursiveTest {
    @InjectMocks
    private Recursive recursive;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCallRecursiveSelfIfC() {
        int c = 1;
        recursive.callRecursiveSelf(c);

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testCallRecursiveSelfElseC() {
        int c = 1;
        recursive.callRecursiveSelf(c);

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testCallRecursiveInternal() {
        recursive.callRecursiveInternal();

        // fail("unable to assert, STEPIN");
    }
}
