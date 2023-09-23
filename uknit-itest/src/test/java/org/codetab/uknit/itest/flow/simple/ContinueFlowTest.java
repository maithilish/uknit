package org.codetab.uknit.itest.flow.simple;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class ContinueFlowTest {
    @InjectMocks
    private ContinueFlow continueFlow;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPlainContinueIfSearchMeCharAt() {
        int numPs = 9;

        int actual = continueFlow.plainContinue();

        assertEquals(numPs, actual);
    }

    @Test
    public void testPlainContinueElseSearchMeCharAt() {
        int numPs = 9;

        int actual = continueFlow.plainContinue();

        assertEquals(numPs, actual);
    }

    @Test
    public void testLabeledContinueIfSearchMeCharAt() {

        boolean actual = continueFlow.labeledContinue();

        assertTrue(actual);
    }

    @Test
    public void testLabeledContinueElseSearchMeCharAt() {

        boolean actual = continueFlow.labeledContinue();

        assertTrue(actual);
    }
}
