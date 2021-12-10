package org.codetab.uknit.itest.flow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class ContinueFlowTest {
    @InjectMocks
    private ContinueFlow continueFlow;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPlainContinue() {
        int numPs = 9;

        int actual = continueFlow.plainContinue();

        assertEquals(numPs, actual);
    }

    @Test
    public void testLabeledContinue() {

        boolean actual = continueFlow.labeledContinue();

        assertTrue(actual);
    }
}
