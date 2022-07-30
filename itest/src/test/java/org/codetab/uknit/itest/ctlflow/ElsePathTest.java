package org.codetab.uknit.itest.ctlflow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ElsePathTest {
    @InjectMocks
    private ElsePath elsePath;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIfFooIfFlag() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = elsePath.ifFoo(sb, flag);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("if");
        verify(sb).append("end");
    }

    @Test
    public void testIfFooFlag() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = false;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = elsePath.ifFoo(sb, flag);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("end");
    }

    @Test
    public void testIfIfFooIfFlag() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        boolean done = false;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = elsePath.ifIfFoo(sb, flag, done);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("if flag");
        verify(sb).append("end");
    }

    @Test
    public void testIfIfFooFlag() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = false;
        boolean done = false;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = elsePath.ifIfFoo(sb, flag, done);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("end");
    }

    @Test
    public void testIfIfFooIfDone() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = false;
        boolean done = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = elsePath.ifIfFoo(sb, flag, done);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("if done");
        verify(sb).append("end");
    }

    @Test
    public void testTryIfFooTry() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = false;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = elsePath.tryIfFoo(sb, flag);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("try");
        verify(sb).append("finally");
        verify(sb).append("end");
    }

    @Test
    public void testTryIfFooIfFlag() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = elsePath.tryIfFoo(sb, flag);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("if");
        verify(sb).append("end");
    }

    @Test
    public void testIfThenIfFooIfDone() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        boolean done = true;
        boolean eof = false;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = elsePath.ifThenIfFoo(sb, flag, done, eof);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("if flag");
        verify(sb).append("if done");
        verify(sb).append("end");
    }

    @Test
    public void testIfThenIfFooFlag() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = false;
        boolean done = false;
        boolean eof = false;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = elsePath.ifThenIfFoo(sb, flag, done, eof);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("end");
    }

    @Test
    public void testIfThenIfFooIfEof() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        boolean flag = true;
        boolean done = false;
        boolean eof = true;
        String apple = "Foo";

        when(sb.toString()).thenReturn(apple);

        String actual = elsePath.ifThenIfFoo(sb, flag, done, eof);

        assertEquals(apple, actual);
        verify(sb).append("start");
        verify(sb).append("if eof");
        verify(sb).append("end");
    }
}
