package org.codetab.uknit.itest.superclass;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class SuperCallAsArgTest {
    @InjectMocks
    private SuperCallAsArg superCallAsArg;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCallInternalCreateArgInSuper() {

        boolean actual = superCallAsArg.callInternalCreateArgInSuper();

        assertFalse(actual);
    }

    @Test
    public void testCallInternalGetArgFromSuper() throws Exception {
        DateFormat dateFormat = Mockito.mock(DateFormat.class);
        String dateStr1 = "Foo";
        Date date2 = Mockito.mock(Date.class);
        Date date3 = Mockito.mock(Date.class);
        boolean grape = true;

        when(dateFormat.parse(dateStr1)).thenReturn(date3);
        when(date3.after(date2)).thenReturn(grape);

        boolean actual = superCallAsArg.callInternalGetArgFromSuper(dateFormat,
                dateStr1, date2);

        assertTrue(actual);
    }
}
