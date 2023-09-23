package org.codetab.uknit.itest.ret;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class VoidsTest {
    @InjectMocks
    private Voids voids;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSingleReturn() {
        voids.singleReturn();

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testTwoReturnInIfElseIfFlag() {
        boolean flag = true;
        voids.twoReturnInIfElse(flag);

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testTwoReturnInIfElseElseFlag() {
        boolean flag = true;
        voids.twoReturnInIfElse(flag);

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testTwoReturnNoElseIfFlag() {
        boolean flag = true;
        voids.twoReturnNoElse(flag);

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testTwoReturnNoElseElseFlag() {
        boolean flag = true;
        voids.twoReturnNoElse(flag);

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testMultiReturnIfElseIfFlag1() {
        boolean flag1 = true;
        boolean flag2 = true;
        voids.multiReturnIfElse(flag1, flag2);

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testMultiReturnIfElseElseFlag1Ifelse() {
        boolean flag1 = true;
        boolean flag2 = true;
        voids.multiReturnIfElse(flag1, flag2);

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testMultiReturnIfElseElseFlag1Elseelse() {
        boolean flag1 = true;
        boolean flag2 = true;
        voids.multiReturnIfElse(flag1, flag2);

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testMultiReturnNoElseIfFlag1() {
        boolean flag1 = true;
        boolean flag2 = true;
        voids.multiReturnNoElse(flag1, flag2);

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testMultiReturnNoElseElseFlag1Ifelse() {
        boolean flag1 = true;
        boolean flag2 = true;
        voids.multiReturnNoElse(flag1, flag2);

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testMultiReturnNoElseElseFlag1Elseelse() {
        boolean flag1 = true;
        boolean flag2 = true;
        voids.multiReturnNoElse(flag1, flag2);

        // fail("unable to assert, STEPIN");
    }
}
