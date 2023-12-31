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

class InternalNestedArgTest {
    @InjectMocks
    private InternalNestedArg internalNestedArg;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCallA() {
        Date date = Mockito.mock(Date.class);
        Instant instant2 = Mockito.mock(Instant.class);
        int grape = 1;
        int apple = grape;

        when(date.toInstant()).thenReturn(instant2);
        when(instant2.getNano()).thenReturn(grape);

        int actual = internalNestedArg.callA(date);

        assertEquals(apple, actual);
    }

    @Test
    public void testCallB() throws Exception {
        DateFormat dateFormat = Mockito.mock(DateFormat.class);
        String dateStr1 = "Foo";
        Date date2 = Mockito.mock(Date.class);
        Date date3 = Mockito.mock(Date.class);
        boolean grape = true;

        when(dateFormat.parse(dateStr1)).thenReturn(date3);
        when(date3.after(date2)).thenReturn(grape);

        boolean actual = internalNestedArg.callB(dateFormat, dateStr1, date2);

        assertTrue(actual);
    }

    @Test
    public void testMockDiffNameC() {
        Instant instant = Mockito.mock(Instant.class);
        Instant instant5 = Mockito.mock(Instant.class);
        Instant instant6 = Mockito.mock(Instant.class);
        Instant instant7 = Mockito.mock(Instant.class);
        Instant instant4 = instant7;

        when(instant.minusMillis(1)).thenReturn(instant5);
        when(instant5.minusMillis(2)).thenReturn(instant6);
        when(instant6.minusMillis(3)).thenReturn(instant7);

        Instant actual = internalNestedArg.mockDiffNameC(instant);

        assertSame(instant4, actual);
    }

    @Test
    public void testMockSameNameD() {
        Instant instant = Mockito.mock(Instant.class);
        Instant instant5 = Mockito.mock(Instant.class);
        Instant instant6 = Mockito.mock(Instant.class);
        Instant instant7 = Mockito.mock(Instant.class);
        Instant instant4 = instant7;

        when(instant.minusMillis(4)).thenReturn(instant5);
        when(instant5.minusMillis(5)).thenReturn(instant6);
        when(instant6.minusMillis(6)).thenReturn(instant7);

        Instant actual = internalNestedArg.mockSameNameD(instant);

        assertSame(instant4, actual);
    }

    @Test
    public void testRealDiffNameE() {
        String str = "Foo";
        String kiwi = str.concat("E3");
        String mango = kiwi.concat("E2");
        String banana = mango.concat("E1");
        String orange = banana;

        String actual = internalNestedArg.realDiffNameE(str);

        assertEquals(orange, actual);
    }

    @Test
    public void testRealSameNameF() {
        String str = "Foo";
        String kiwi = str.concat("F3");
        String mango = kiwi.concat("F2");
        String banana = mango.concat("F1");
        String orange = banana;

        String actual = internalNestedArg.realSameNameF(str);

        assertEquals(orange, actual);
    }
}
