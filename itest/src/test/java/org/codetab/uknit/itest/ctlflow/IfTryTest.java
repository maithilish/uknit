package org.codetab.uknit.itest.ctlflow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class IfTryTest {
    @InjectMocks
    private IfTry ifTry;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIfTryFooTry() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = ifTry.ifTryFoo(sb, flag);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("if");
        verify(sb).append("if try");
        verify(sb).append("if finally");
        verify(sb).append("end");
    }

    @Test
    public void testIfTryFooCatchIllegalStateException() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(sb).append("if try");

        String actual = ifTry.ifTryFoo(sb, flag);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("if");
        verify(sb).append("if try");
        verify(sb).append("if catch");
        verify(sb).append("if finally");
        verify(sb).append("end");
    }

    @Test
    public void testIfTryFooElseFlag() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = false;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = ifTry.ifTryFoo(sb, flag);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("else");
        verify(sb).append("end");
    }

    @Test
    public void testTryIfFooIfDone() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        boolean done = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = ifTry.tryIfFoo(sb, flag, done);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("try if");
        verify(sb).append("try if if");
        verify(sb).append("finally");
        verify(sb).append("end");
    }

    @Test
    public void testTryIfFooElseDone() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        boolean done = false;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = ifTry.tryIfFoo(sb, flag, done);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("try if");
        verify(sb).append("try if else");
        verify(sb).append("finally");
        verify(sb).append("end");
    }

    @Test
    public void testTryIfFooElseFlag() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = false;
        boolean done = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = ifTry.tryIfFoo(sb, flag, done);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("try else");
        verify(sb).append("finally");
        verify(sb).append("end");
    }

    @Test
    public void testTryIfFooCatchIllegalStateException() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        boolean done = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(sb).append("try");

        String actual = ifTry.tryIfFoo(sb, flag, done);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("catch");
        verify(sb).append("finally");
        verify(sb).append("end");
    }

    @Test
    public void testTryCatchIfFooTry() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        boolean done = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = ifTry.tryCatchIfFoo(sb, flag, done);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("finally");
        verify(sb).append("end");
    }

    @Test
    public void testTryCatchIfFooIfDone() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        boolean done = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(sb).append("try");

        String actual = ifTry.tryCatchIfFoo(sb, flag, done);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("catch");
        verify(sb).append("catch if");
        verify(sb).append("catch if if");
        verify(sb).append("finally");
        verify(sb).append("end");
    }

    @Test
    public void testTryCatchIfFooElseDone() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        boolean done = false;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(sb).append("try");

        String actual = ifTry.tryCatchIfFoo(sb, flag, done);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("catch");
        verify(sb).append("catch if");
        verify(sb).append("catch if else");
        verify(sb).append("finally");
        verify(sb).append("end");
    }

    @Test
    public void testTryCatchIfFooElseFlag() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = false;
        boolean done = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(sb).append("try");

        String actual = ifTry.tryCatchIfFoo(sb, flag, done);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("catch");
        verify(sb).append("catch else");
        verify(sb).append("finally");
        verify(sb).append("end");
    }

    @Test
    public void testTryFinallyIfFooIfDone() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        boolean done = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = ifTry.tryFinallyIfFoo(sb, flag, done);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("finally");
        verify(sb).append("finally if");
        verify(sb).append("finally if if");
        verify(sb).append("end");
    }

    @Test
    public void testTryFinallyIfFooElseDone() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        boolean done = false;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = ifTry.tryFinallyIfFoo(sb, flag, done);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("finally");
        verify(sb).append("finally if");
        verify(sb).append("finally if else");
        verify(sb).append("end");
    }

    @Test
    public void testTryFinallyIfFooElseFlag() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = false;
        boolean done = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = ifTry.tryFinallyIfFoo(sb, flag, done);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("finally");
        verify(sb).append("finally else");
        verify(sb).append("end");
    }

    @Test
    public void testTryFinallyIfFooIfDone2() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        boolean done = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(sb).append("try");

        String actual = ifTry.tryFinallyIfFoo(sb, flag, done);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("catch");
        verify(sb).append("finally");
        verify(sb).append("finally if");
        verify(sb).append("finally if if");
        verify(sb).append("end");
    }

    @Test
    public void testTryIfNoFinallyFooIfFlag() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = ifTry.tryIfNoFinallyFoo(sb, flag);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("try if");
        verify(sb).append("end");
    }

    @Test
    public void testTryIfNoFinallyFooElseFlag() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = false;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = ifTry.tryIfNoFinallyFoo(sb, flag);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("try else");
        verify(sb).append("end");
    }

    @Test
    public void testTryIfNoFinallyFooCatchIllegalStateException() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(sb).append("try");

        String actual = ifTry.tryIfNoFinallyFoo(sb, flag);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("catch");
        verify(sb).append("end");
    }

    @Test
    public void testTryCatchIfNoFinallyFooTry() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = ifTry.tryCatchIfNoFinallyFoo(sb, flag);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("end");
    }

    @Test
    public void testTryCatchIfNoFinallyFooIfFlag() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(sb).append("try");

        String actual = ifTry.tryCatchIfNoFinallyFoo(sb, flag);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("catch");
        verify(sb).append("catch if");
        verify(sb).append("end");
    }

    @Test
    public void testTryCatchIfNoFinallyFooElseFlag() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = false;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(sb).append("try");

        String actual = ifTry.tryCatchIfNoFinallyFoo(sb, flag);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("catch");
        verify(sb).append("catch else");
        verify(sb).append("end");
    }

    @Test
    public void testTryIfNoCatchFooIfFlag() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = ifTry.tryIfNoCatchFoo(sb, flag);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("try if");
        verify(sb).append("finally");
        verify(sb).append("end");
    }

    @Test
    public void testTryIfNoCatchFooElseFlag() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = false;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = ifTry.tryIfNoCatchFoo(sb, flag);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("try else");
        verify(sb).append("finally");
        verify(sb).append("end");
    }

    @Test
    public void testTryFinallyIfNoCatchFooIfFlag() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = ifTry.tryFinallyIfNoCatchFoo(sb, flag);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("finally");
        verify(sb).append("finally if");
        verify(sb).append("end");
    }

    @Test
    public void testTryFinallyIfNoCatchFooElseFlag() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = false;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = ifTry.tryFinallyIfNoCatchFoo(sb, flag);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("finally");
        verify(sb).append("finally else");
        verify(sb).append("end");
    }
}
