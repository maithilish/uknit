package org.codetab.uknit.itest.flow.nosplit;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class BreakFlowTest {
    @InjectMocks
    private BreakFlow breakFlow;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPlainBreak() {

        boolean actual = breakFlow.plainBreak();

        assertTrue(actual);
    }

    @Test
    public void testLabeledBreak() {

        boolean actual = breakFlow.labeledBreak();

        assertTrue(actual);
    }
}
