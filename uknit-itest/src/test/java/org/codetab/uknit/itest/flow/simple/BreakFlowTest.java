package org.codetab.uknit.itest.flow.simple;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class BreakFlowTest {
    @InjectMocks
    private BreakFlow breakFlow;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPlainBreakIf() {

        boolean actual = breakFlow.plainBreak();

        assertTrue(actual);
    }

    @Test
    public void testPlainBreakElse() {

        boolean actual = breakFlow.plainBreak();

        assertTrue(actual);
    }

    @Test
    public void testLabeledBreakIf() {

        boolean actual = breakFlow.labeledBreak();

        assertTrue(actual);
    }

    @Test
    public void testLabeledBreakElse() {

        boolean actual = breakFlow.labeledBreak();

        assertTrue(actual);
    }
}
