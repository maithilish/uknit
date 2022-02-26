package org.codetab.uknit.itest.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.time.Instant;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class CallInternalWithInternalArgTest {
    @InjectMocks
    private CallInternalWithInternalArg callInternalWithInternalArg;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCallA() {
        Date date = Mockito.mock(Date.class);
        Instant apple = Mockito.mock(Instant.class);
        Instant instant1 = apple;
        int grape = 1;

        when(date.toInstant()).thenReturn(apple);
        when(instant1.getNano()).thenReturn(grape);

        int actual = callInternalWithInternalArg.callA(date);

        assertEquals(grape, actual);
    }

    @Test
    public void testCallX() throws Exception {
        DateFormat dateFormat = Mockito.mock(DateFormat.class);
        String dateStr1 = "Foo";
        Date date2 = Mockito.mock(Date.class);
        Date apple = Mockito.mock(Date.class);
        Date date1 = apple;
        boolean grape = true;

        when(dateFormat.parse(dateStr1)).thenReturn(apple);
        when(date1.after(date2)).thenReturn(grape);

        boolean actual =
                callInternalWithInternalArg.callX(dateFormat, dateStr1, date2);

        assertTrue(actual);
    }
}
