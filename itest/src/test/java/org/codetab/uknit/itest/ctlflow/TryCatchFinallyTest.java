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

public class TryCatchFinallyTest {
    @InjectMocks
    private TryCatchFinally tryCatchFinally;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFooTryTry() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = tryCatchFinally.fooTry(sb);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("finally");
        verify(sb).append("end");
    }

    @Test
    public void testFooTryCatchIllegalArgumentException() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);
        doThrow(IllegalArgumentException.class).when(sb).append("try");

        String actual = tryCatchFinally.fooTry(sb);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("catch illegal arg");
        verify(sb).append("finally");
        verify(sb).append("end");
    }

    @Test
    public void testFooTryFinallyTry() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = tryCatchFinally.fooTryFinally(sb);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("finally");
        verify(sb).append("end");
    }

    @Test
    public void testFooTryFinallyCatchIllegalStateException() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);
        doThrow(IllegalStateException.class).when(sb).append("try");

        String actual = tryCatchFinally.fooTryFinally(sb);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("catch illegal state");
        verify(sb).append("finally");
        verify(sb).append("end");
    }

    @Test
    public void testFooTryFinallyCatchIllegalArgumentException() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);
        doThrow(IllegalArgumentException.class).when(sb).append("try");

        String actual = tryCatchFinally.fooTryFinally(sb);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("catch illegal arg");
        verify(sb).append("finally");
        verify(sb).append("end");
    }

    @Test
    public void testFooTryFinallyTry2() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = tryCatchFinally.fooTryFinally(sb);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try 2");
        verify(sb).append("finally 2");
        verify(sb).append("end");
    }

    @Test
    public void testFooTryFinallyTry3() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = tryCatchFinally.fooTryFinally(sb);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try 3");
        verify(sb).append("finally 3");
        verify(sb).append("end");
    }
}
