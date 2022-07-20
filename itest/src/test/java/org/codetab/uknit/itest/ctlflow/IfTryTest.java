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
    public void testFooIfTryTry() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        boolean done = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = ifTry.fooIfTry(sb, flag, done);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("if");
        verify(sb).append("try");
        verify(sb).append("finally");
        verify(sb).append("end");
    }

    @Test
    public void testFooIfTryCatchIllegalStateException() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        boolean done = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(sb).append("try");

        String actual = ifTry.fooIfTry(sb, flag, done);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("if");
        verify(sb).append("try");
        verify(sb).append("catch");
        verify(sb).append("finally");
        verify(sb).append("end");
    }

    @Test
    public void testFooIfTryElseFlag() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = false;
        boolean done = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = ifTry.fooIfTry(sb, flag, done);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("else");
        verify(sb).append("end");
    }

    @Test
    public void testFooTryIfIfDone() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        boolean done = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = ifTry.fooTryIf(sb, flag, done);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("if");
        verify(sb).append("if if");
        verify(sb).append("finally");
        verify(sb).append("end");
    }

    @Test
    public void testFooTryIfElseDone() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        boolean done = false;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = ifTry.fooTryIf(sb, flag, done);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("if");
        verify(sb).append("if else");
        verify(sb).append("finally");
        verify(sb).append("end");
    }

    @Test
    public void testFooTryIfElseFlag() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = false;
        boolean done = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = ifTry.fooTryIf(sb, flag, done);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("else");
        verify(sb).append("finally");
        verify(sb).append("end");
    }

    @Test
    public void testFooTryIfCatchIllegalStateException() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        boolean done = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(sb).append("try");

        String actual = ifTry.fooTryIf(sb, flag, done);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("catch");
        verify(sb).append("finally");
        verify(sb).append("end");
    }
}
