package org.codetab.uknit.itest.initializer;

import static org.mockito.Mockito.when;

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
    public void testCyclicInInitializers() {

        String grape = "Bar";
        Object base = "foo";
        Object value2 = base;

        when(value2.toString()).thenReturn(grape);
        cyclic.cyclicInInitializers();

        // fail("unable to assert, STEPIN");
    }
}
