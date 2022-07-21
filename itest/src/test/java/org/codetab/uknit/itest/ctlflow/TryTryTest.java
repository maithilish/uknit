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

public class TryTryTest {
    @InjectMocks
    private TryTry tryTry;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTryTryFooTry2() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = tryTry.tryTryFoo(sb);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("try try");
        verify(sb).append("try try finally");
        verify(sb).append("finally");
        verify(sb).append("end");
    }

    @Test
    public void testTryTryFooCatchIllegalAccessError() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);
        doThrow(IllegalAccessError.class).when(sb).append("try try");

        String actual = tryTry.tryTryFoo(sb);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("try try");
        verify(sb).append("try try catch");
        verify(sb).append("try try finally");
        verify(sb).append("finally");
        verify(sb).append("end");
    }

    @Test
    public void testTryTryFooCatchIllegalArgumentException() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);
        doThrow(IllegalArgumentException.class).when(sb).append("try");

        String actual = tryTry.tryTryFoo(sb);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("catch");
        verify(sb).append("finally");
        verify(sb).append("end");
    }

    @Test
    public void testTryCatchTryFooTry() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = tryTry.tryCatchTryFoo(sb);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("finally");
        verify(sb).append("end");
    }

    @Test
    public void testTryCatchTryFooTry2() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);
        doThrow(IllegalArgumentException.class).when(sb).append("try");

        String actual = tryTry.tryCatchTryFoo(sb);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("catch");
        verify(sb).append("catch try");
        verify(sb).append("catch finally");
        verify(sb).append("finally");
        verify(sb).append("end");
    }

    @Test
    public void testTryCatchTryFooCatchIllegalAccessError() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);
        doThrow(IllegalArgumentException.class).when(sb).append("try");
        doThrow(IllegalAccessError.class).when(sb).append("catch try");

        String actual = tryTry.tryCatchTryFoo(sb);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("catch");
        verify(sb).append("catch try");
        verify(sb).append("catch catch");
        verify(sb).append("catch finally");
        verify(sb).append("finally");
        verify(sb).append("end");
    }

    @Test
    public void testTryFinallyTryFooTry2() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = tryTry.tryFinallyTryFoo(sb);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("finally");
        verify(sb).append("finally try");
        verify(sb).append("finally finally");
        verify(sb).append("end");
    }

    @Test
    public void testTryFinallyTryFooCatchIllegalAccessError() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);
        doThrow(IllegalAccessError.class).when(sb).append("finally try");

        String actual = tryTry.tryFinallyTryFoo(sb);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("finally");
        verify(sb).append("finally try");
        verify(sb).append("finally catch");
        verify(sb).append("finally finally");
        verify(sb).append("end");
    }

    @Test
    public void testTryFinallyTryFooTry3() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);
        doThrow(IllegalArgumentException.class).when(sb).append("try");

        String actual = tryTry.tryFinallyTryFoo(sb);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("catch");
        verify(sb).append("finally");
        verify(sb).append("finally try");
        verify(sb).append("finally finally");
        verify(sb).append("end");
    }
}
