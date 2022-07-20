package org.codetab.uknit.itest.ret;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class MultiReturnPathNoSplitTest {
    @InjectMocks
    private MultiReturnPathNoSplit multiReturnPathNoSplit;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTwoReturnPaths() {
        String grape = "foo";

        String actual = multiReturnPathNoSplit.twoReturnPaths();

        assertEquals(grape, actual);
    }
}
