package org.codetab.uknit.itest.superclass;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        Date actual = superCalls.defineVarFromCreate();

        assertNotNull(actual);
    }

    @Test
    public void testDefineVarFromCreateAndMock() throws Exception {
        String dateStr = "24/06/22, 2:04 PM";

        DateFormat dateFormat = new SimpleDateFormat();

        Date apple = dateFormat.parse(dateStr);
        Date date = apple;

        Date actual = superCalls.defineVarFromCreateAndMock(dateStr);

        assertEquals(date, actual);
    }

    @Test
    public void testDefineVarFromMocks() throws Exception {
        DateFormat dateFormat = Mockito.mock(DateFormat.class);
        String dateStr = "24/06/22, 2:04 PM";
        Date apple = Mockito.mock(Date.class);
        Date date = apple;

        when(dateFormat.parse(dateStr)).thenReturn(apple);

        Date actual = superCalls.defineVarFromMocks(dateFormat, dateStr);

        assertSame(date, actual);
    }

    @Test
    public void testReturnFromCreate() {
        Date actual = superCalls.returnFromCreate();

        assertNotNull(actual);
    }

    @Test
    public void testReturnFromCreateAndMock() throws Exception {
        String dateStr = "24/06/22, 2:04 PM";

        DateFormat dateFormat = new SimpleDateFormat();

        Date apple = dateFormat.parse(dateStr);

        Date actual = superCalls.returnFromCreateAndMock(dateStr);

        assertEquals(apple, actual);
    }

    @Test
    public void testReturnFromMocks() throws Exception {
        DateFormat dateFormat = Mockito.mock(DateFormat.class);
        String dateStr = "Foo";
        Date apple = Mockito.mock(Date.class);

        when(dateFormat.parse(dateStr)).thenReturn(apple);

        Date actual = superCalls.returnFromMocks(dateFormat, dateStr);

        assertSame(apple, actual);
    }

    @Test
    public void testInForEach() {
        superCalls.inForEach();
    }
}
