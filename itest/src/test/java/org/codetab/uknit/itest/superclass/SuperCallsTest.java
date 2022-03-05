package org.codetab.uknit.itest.superclass;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class SuperCallsTest {
    @InjectMocks
    private SuperCalls superCalls;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void testDefineVarFromCreate() {
        superCalls.defineVarFromCreate();
    }

    @Test
    public void testDefineVarFromCreateAndMock() throws Exception {
        String dateStr = "05/03/22, 7:37 AM";

        DateFormat dateFormat = new SimpleDateFormat();

        Date date = dateFormat.parse(dateStr);

        Date actual = superCalls.defineVarFromCreateAndMock(dateStr);

        assertEquals(date, actual);
    }

    @Test
    public void testDefineVarFromMocks() throws Exception {
        DateFormat dateFormat = Mockito.mock(DateFormat.class);
        String dateStr = "Foo";
        Date date = Mockito.mock(Date.class);

        when(dateFormat.parse(dateStr)).thenReturn(date);

        Date actual = superCalls.defineVarFromMocks(dateFormat, dateStr);

        assertSame(date, actual);
    }
}
