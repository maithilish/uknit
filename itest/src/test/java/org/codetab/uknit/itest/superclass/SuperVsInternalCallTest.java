package org.codetab.uknit.itest.superclass;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class SuperVsInternalCallTest {
    @InjectMocks
    private SuperVsInternalCall superVsInternalCall;

    @Mock
    private StringBuilder bar;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetInternalFooBar() {

        StringBuilder actual = superVsInternalCall.getInternalFooBar();

        assertSame(bar, actual);
    }

    @Test
    public void testGetSuperFooBar() {
        StringBuilder xbar = Mockito.mock(StringBuilder.class);
        superVsInternalCall.setBar(xbar); // STEPIN

        StringBuilder actual = superVsInternalCall.getSuperFooBar();

        assertSame(xbar, actual);
    }

    @Test
    public void testInternalFooBar() {
        StringBuilder xbar = Mockito.mock(StringBuilder.class);

        StringBuilder actual = superVsInternalCall.internalFooBar(xbar);

        assertSame(xbar, actual);
    }

    @Test
    public void testInternalBar() {

        StringBuilder actual = superVsInternalCall.internalBar();

        assertSame(bar, actual);
    }
}
