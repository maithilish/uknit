package org.codetab.uknit.itest.superclass;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class SuperVsInternalCallTest {
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
        StringBuilder stringBuilder2 = bar;

        StringBuilder actual = superVsInternalCall.getInternalFooBar();

        assertSame(stringBuilder2, actual);
    }

    @Test
    public void testGetSuperFooBar() {

        StringBuilder actual = superVsInternalCall.getSuperFooBar();

        assertSame(bar, actual);
    }

    @Test
    public void testInternalFooBar() {
        StringBuilder bar = Mockito.mock(StringBuilder.class);

        StringBuilder actual = superVsInternalCall.internalFooBar(bar);

        assertSame(bar, actual);
    }

    @Test
    public void testInternalBar() {

        StringBuilder actual = superVsInternalCall.internalBar();

        assertSame(bar, actual);
    }
}
