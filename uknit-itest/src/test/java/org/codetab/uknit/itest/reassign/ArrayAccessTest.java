package org.codetab.uknit.itest.reassign;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class ArrayAccessTest {
    @InjectMocks
    private ArrayAccess arrayAccess;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAssignTwice() {
        arrayAccess.assignTwice();

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testAssignTwiceInIfBlockIfCode() {
        int code = 1;
        arrayAccess.assignTwiceInIfBlock(code);

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testAssignTwiceInIfBlockElseCode() {
        int code = 1;
        arrayAccess.assignTwiceInIfBlock(code);

        // fail("unable to assert, STEPIN");
    }
}
