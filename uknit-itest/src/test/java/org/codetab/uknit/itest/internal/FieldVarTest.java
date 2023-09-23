package org.codetab.uknit.itest.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class FieldVarTest {
    @InjectMocks
    private FieldVar fieldVar;

    @Mock
    private Locale locale;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCallReturnsFieldInvoke() {
        String grape = "Foo";
        String apple = grape;

        when(locale.getCountry()).thenReturn(grape);

        String actual = fieldVar.callReturnsFieldInvoke();

        assertEquals(apple, actual);
    }
}
