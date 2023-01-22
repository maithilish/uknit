package org.codetab.uknit.itest.ret;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class MultiReturnPathTest {
    @InjectMocks
    private MultiReturnPath multiReturnPath;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTwoReturnPathsIfFlag() {
        String apple = "foo";

        String actual = multiReturnPath.twoReturnPaths();

        assertEquals(apple, actual);
    }

    @Test
    public void testTwoReturnPathsElseFlag() {
        String apple = "foo";

        String actual = multiReturnPath.twoReturnPaths();

        assertEquals(apple, actual);
    }
}
