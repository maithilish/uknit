package org.codetab.uknit.itest.ctlflow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class IfElseTest {
    @InjectMocks
    private IfElse ifElse;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFooIfIfFlag() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = ifElse.fooIf(sb, flag);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("if");
        verify(sb).append("end");
    }

    @Test
    public void testFooIfFlag() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = false;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = ifElse.fooIf(sb, flag);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("end");
    }

    @Test
    public void testFooIfElseIfFlag() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = ifElse.fooIfElse(sb, flag);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("if");
        verify(sb).append("end");
    }

    @Test
    public void testFooIfElseElseFlag() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = false;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = ifElse.fooIfElse(sb, flag);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("else");
        verify(sb).append("end");
    }

    @Test
    public void testFooIfIfIfDone() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        boolean done = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = ifElse.fooIfIf(sb, flag, done);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("if");
        verify(sb).append("if if");
        verify(sb).append("end");
    }

    @Test
    public void testFooIfIfElseDone() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        boolean done = false;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = ifElse.fooIfIf(sb, flag, done);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("if");
        verify(sb).append("if else");
        verify(sb).append("end");
    }

    @Test
    public void testFooIfIfElseFlag() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = false;
        boolean done = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = ifElse.fooIfIf(sb, flag, done);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("else");
        verify(sb).append("end");
    }
}
