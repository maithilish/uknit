package org.codetab.uknit.itest.flow.nosplit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.flow.nosplit.Model.Duck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class IfTryTest {
    @InjectMocks
    private IfTry ifTry;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIfTryFoo() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifTry.ifTryFoo(duck, canSwim);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("else canSwim");
        verify(duck).swim("if try");
        verify(duck, never()).swim("if catch");
        verify(duck).swim("if finally");
        verify(duck).swim("end");
    }

    @Test
    public void testTryIfFoo() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String apple = "Foo";

        when(duck.toString()).thenReturn(apple);

        String actual = ifTry.tryIfFoo(duck, canSwim);

        assertEquals(apple, actual);

        verify(duck).swim("start");
        verify(duck).swim("if try");
        verify(duck, never()).swim("if catch");
        verify(duck).swim("if finally");
        verify(duck).swim("if canSwim");
        verify(duck, never()).swim("else canSwim");
        verify(duck).swim("end");
    }
}
