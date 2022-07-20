package org.codetab.uknit.itest.operator;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class RelationalNoSplitTest {
    @InjectMocks
    private RelationalNoSplit relationalNoSplit;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCheckRelational() {

        boolean actual = relationalNoSplit.checkRelational();

        assertTrue(actual);
    }

    @Test
    public void testCheckConditional() {

        boolean actual = relationalNoSplit.checkConditional();

        assertTrue(actual);
    }
}
