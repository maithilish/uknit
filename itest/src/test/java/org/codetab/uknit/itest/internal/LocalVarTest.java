package org.codetab.uknit.itest.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class LocalVarTest {
    @InjectMocks
    private LocalVar localVar;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCallReturnsVar() {
        String bar = "bar";
        String foo = bar;

        String actual = localVar.callReturnsVar();

        assertEquals(foo, actual);
    }

    @Test
    public void testCallReturnsVarNameConflict() {
        String bar2 = "bar";
        String bar = bar2;

        String actual = localVar.callReturnsVarNameConflict();

        assertEquals(bar, actual);
    }

    @Test
    public void testCallReturnsLiteral() {
        String apple = "bar";
        String foo = apple;

        String actual = localVar.callReturnsLiteral();

        assertEquals(foo, actual);
    }

    @Test
    public void testCallReturnsLiteralNameConflict() {
        String apple = "bar";
        String bar = apple;

        String actual = localVar.callReturnsLiteralNameConflict();

        assertEquals(bar, actual);
    }

    @Test
    public void testReturnCall() {
        String bar = "bar";
        String apple = bar;

        String actual = localVar.returnCall();

        assertEquals(apple, actual);
    }
}
