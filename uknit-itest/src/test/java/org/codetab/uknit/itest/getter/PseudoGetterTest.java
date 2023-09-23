package org.codetab.uknit.itest.getter;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.codetab.uknit.itest.getter.PseudoGetter.Payload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class PseudoGetterTest {
    @InjectMocks
    private PseudoGetter pseudoGetter;

    @Mock
    private Payload payload;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetDate() {
        Date date = Mockito.mock(Date.class);

        when(payload.getDate()).thenReturn(date);

        Date actual = pseudoGetter.getDate();

        assertSame(date, actual);
    }
}
