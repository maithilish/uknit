package org.codetab.uknit.itest.ret;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class MultiReturnPathNoSplitTest {
    @InjectMocks
    private MultiReturnPathNoSplit multiReturnPathNoSplit;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTwoReturnPathsIfFlag() {
        String apple = "foo";

        String actual = multiReturnPathNoSplit.twoReturnPaths();

        assertEquals(apple, actual);
    }

    @Test
    public void testTwoReturnPathsElseFlag() {
        String apple = "foo";

        String actual = multiReturnPathNoSplit.twoReturnPaths();

        assertEquals(apple, actual);
    }
}
