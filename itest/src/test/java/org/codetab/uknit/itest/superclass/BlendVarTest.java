package org.codetab.uknit.itest.superclass;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class BlendVarTest {
    @InjectMocks
    private BlendVar blendVar;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInvokeInternalMock() {
        AFactory aFactory = Mockito.mock(AFactory.class);
        Date date = Mockito.mock(Date.class);
        Date date2 = date;

        when(aFactory.getDate()).thenReturn(date);

        Date actual = blendVar.invokeInternalMock(aFactory);

        assertSame(date2, actual);
    }

    @Test
    public void testInvokeFromSuperMock() {
        AFactory aFactory = Mockito.mock(AFactory.class);
        Date date = Mockito.mock(Date.class);
        Date date2 = date;

        when(aFactory.getDate()).thenReturn(date);

        Date actual = blendVar.invokeFromSuperMock(aFactory);

        assertSame(date2, actual);
    }

    @Test
    public void testInvokeWithSuperMock() {
        AFactory aFactory = Mockito.mock(AFactory.class);
        Date date = Mockito.mock(Date.class);

        when(aFactory.getDate()).thenReturn(date);

        Date actual = blendVar.invokeWithSuperMock(aFactory);

        assertSame(date, actual);
    }

    @Test
    public void testInvokeInternalReal() {
        AFactory aFactory = Mockito.mock(AFactory.class);
        String apple = "Foo";
        String grape = apple;

        when(aFactory.getString()).thenReturn(apple);

        String actual = blendVar.invokeInternalReal(aFactory);

        assertEquals(grape, actual);
    }

    @Test
    public void testInvokeFromSuperReal() {
        AFactory aFactory = Mockito.mock(AFactory.class);
        String apple = "Foo";
        String grape = apple;

        when(aFactory.getString()).thenReturn(apple);

        String actual = blendVar.invokeFromSuperReal(aFactory);

        assertEquals(grape, actual);
    }

    @Test
    public void testInvokeWithSuperReal() {
        AFactory aFactory = Mockito.mock(AFactory.class);
        String apple = "Foo";

        when(aFactory.getString()).thenReturn(apple);

        String actual = blendVar.invokeWithSuperReal(aFactory);

        assertEquals(apple, actual);
    }
}
