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
        Date apple = Mockito.mock(Date.class);

        when(aFactory.getDate()).thenReturn(apple);

        Date actual = blendVar.invokeInternalMock(aFactory);

        assertSame(apple, actual);
    }

    @Test
    public void testInvokeFromSuperMock() {
        AFactory aFactory = Mockito.mock(AFactory.class);
        Date apple = Mockito.mock(Date.class);

        when(aFactory.getDate()).thenReturn(apple);

        Date actual = blendVar.invokeFromSuperMock(aFactory);

        assertSame(apple, actual);
    }

    @Test
    public void testInvokeWithSuperMock() {
        AFactory aFactory = Mockito.mock(AFactory.class);
        Date apple = Mockito.mock(Date.class);

        when(aFactory.getDate()).thenReturn(apple);

        Date actual = blendVar.invokeWithSuperMock(aFactory);

        assertSame(apple, actual);
    }

    @Test
    public void testInvokeInternalReal() {
        AFactory aFactory = Mockito.mock(AFactory.class);
        String apple = "Foo";

        when(aFactory.getString()).thenReturn(apple);

        String actual = blendVar.invokeInternalReal(aFactory);

        assertEquals(apple, actual);
    }

    @Test
    public void testInvokeFromSuperReal() {
        AFactory aFactory = Mockito.mock(AFactory.class);
        String apple = "Foo";

        when(aFactory.getString()).thenReturn(apple);

        String actual = blendVar.invokeFromSuperReal(aFactory);

        assertEquals(apple, actual);
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