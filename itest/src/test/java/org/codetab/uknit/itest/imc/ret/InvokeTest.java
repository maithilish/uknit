package org.codetab.uknit.itest.imc.ret;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class InvokeTest {
    @InjectMocks
    private Invoke invoke;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInvokeOnFieldAccess() {
        Zoo zoo = Mockito.mock(Zoo.class);

        boolean actual = invoke.invokeOnFieldAccess(zoo);

        assertTrue(actual);
    }
}
