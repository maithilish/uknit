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
    public void testTwoReturnIfElseIfFlag() {
        boolean flag = true;
        String apple = "foo";

        String actual = multiReturnPath.twoReturnIfElse(flag);

        assertEquals(apple, actual);
    }

    @Test
    public void testTwoReturnIfElseElseFlag() {
        boolean flag = false;
        String apple = "bar";

        String actual = multiReturnPath.twoReturnIfElse(flag);

        assertEquals(apple, actual);
    }

    @Test
    public void testTwoReturnNoElseIfFlag() {
        boolean flag = true;
        String apple = "foo";

        String actual = multiReturnPath.twoReturnNoElse(flag);

        assertEquals(apple, actual);
    }

    @Test
    public void testTwoReturnNoElseElseFlag() {
        boolean flag = false;
        String apple = "bar";

        String actual = multiReturnPath.twoReturnNoElse(flag);

        assertEquals(apple, actual);
    }

    @Test
    public void testTwoNullNoElseIfFlag() {
        boolean flag = true;
        String apple = "foo";

        String actual = multiReturnPath.twoNullNoElse(flag);

        assertEquals(apple, actual);
    }

    @Test
    public void testTwoNullNoElseElseFlag() {
        boolean flag = false;
        String apple = null;

        String actual = multiReturnPath.twoNullNoElse(flag);

        assertEquals(apple, actual);
    }

    @Test
    public void testMultiReturnIfElseIfFlag1() {
        boolean flag1 = true;
        boolean flag2 = true;
        String apple = "foo";

        String actual = multiReturnPath.multiReturnIfElse(flag1, flag2);

        assertEquals(apple, actual);
    }

    @Test
    public void testMultiReturnIfElseElseFlag1Ifelse() {
        boolean flag1 = false;
        boolean flag2 = true;
        String apple = "bar";

        String actual = multiReturnPath.multiReturnIfElse(flag1, flag2);

        assertEquals(apple, actual);
    }

    @Test
    public void testMultiReturnIfElseElseFlag1Elseelse() {
        boolean flag1 = false;
        boolean flag2 = false;
        String apple = "baz";

        String actual = multiReturnPath.multiReturnIfElse(flag1, flag2);

        assertEquals(apple, actual);
    }

    @Test
    public void testMultiReturnNoElseIfFlag1() {
        boolean flag1 = true;
        boolean flag2 = true;
        String apple = "foo";

        String actual = multiReturnPath.multiReturnNoElse(flag1, flag2);

        assertEquals(apple, actual);
    }

    @Test
    public void testMultiReturnNoElseElseFlag1Ifelse() {
        boolean flag1 = false;
        boolean flag2 = true;
        String apple = "bar";

        String actual = multiReturnPath.multiReturnNoElse(flag1, flag2);

        assertEquals(apple, actual);
    }

    @Test
    public void testMultiReturnNoElseElseFlag1Elseelse() {
        boolean flag1 = false;
        boolean flag2 = false;
        String apple = "baz";

        String actual = multiReturnPath.multiReturnNoElse(flag1, flag2);

        assertEquals(apple, actual);
    }

    @Test
    public void testMultiNullNoElseIfFlag1() {
        boolean flag1 = true;
        boolean flag2 = true;
        String apple = "foo";

        String actual = multiReturnPath.multiNullNoElse(flag1, flag2);

        assertEquals(apple, actual);
    }

    @Test
    public void testMultiNullNoElseElseFlag1Ifelse() {
        boolean flag1 = false;
        boolean flag2 = true;
        String apple = "bar";

        String actual = multiReturnPath.multiNullNoElse(flag1, flag2);

        assertEquals(apple, actual);
    }

    @Test
    public void testMultiNullNoElseElseFlag1Elseelse() {
        boolean flag1 = false;
        boolean flag2 = false;
        String apple = null;

        String actual = multiReturnPath.multiNullNoElse(flag1, flag2);

        assertEquals(apple, actual);
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
