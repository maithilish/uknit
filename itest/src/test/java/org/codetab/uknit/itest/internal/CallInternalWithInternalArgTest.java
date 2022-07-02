package org.codetab.uknit.itest.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
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
        int grape = 1;

        when(date.toInstant()).thenReturn(apple);
        when(apple.getNano()).thenReturn(grape);

        int actual = callInternalWithInternalArg.callA(date);

        assertEquals(grape, actual);
    }

    @Test
    public void testCallB() throws Exception {
        DateFormat dateFormat = Mockito.mock(DateFormat.class);
        String dateStr1 = "Foo";
        Date date2 = Mockito.mock(Date.class);
        Date apple = Mockito.mock(Date.class);
        boolean grape = true;

        when(dateFormat.parse(dateStr1)).thenReturn(apple);
        when(apple.after(date2)).thenReturn(grape);

        boolean actual =
                callInternalWithInternalArg.callB(dateFormat, dateStr1, date2);

        assertTrue(actual);
    }

    @Test
    public void testMockDiffNameC() {
        Instant instant = Mockito.mock(Instant.class);
        Instant instant2 = Mockito.mock(Instant.class);
        Instant instant3 = Mockito.mock(Instant.class);
        Instant instant4 = Mockito.mock(Instant.class);

        when(instant.minusMillis(1)).thenReturn(instant2);
        when(instant2.minusMillis(2)).thenReturn(instant3);
        when(instant3.minusMillis(3)).thenReturn(instant4);

        Instant actual = callInternalWithInternalArg.mockDiffNameC(instant);

        assertSame(instant4, actual);
    }

    @Test
    public void testMockSameNameD() {
        Instant instant = Mockito.mock(Instant.class);
        Instant instant2 = Mockito.mock(Instant.class);
        Instant instant3 = Mockito.mock(Instant.class);
        Instant instant4 = Mockito.mock(Instant.class);

        when(instant.minusMillis(4)).thenReturn(instant2);
        when(instant2.minusMillis(5)).thenReturn(instant3);
        when(instant3.minusMillis(6)).thenReturn(instant4);

        Instant actual = callInternalWithInternalArg.mockSameNameD(instant);

        assertSame(instant4, actual);
    }

    @Test
    public void testRealDiffNameE() {
        String str = "Foo";
        String orange = "FooE3E2E1";

        String actual = callInternalWithInternalArg.realDiffNameE(str);

        assertEquals(orange, actual);
    }

    @Test
    public void testRealSameNameF() {
        String str = "Foo";
        String orange = "FooF3F2F1";

        String actual = callInternalWithInternalArg.realSameNameF(str);

        assertEquals(orange, actual);
    }

}
