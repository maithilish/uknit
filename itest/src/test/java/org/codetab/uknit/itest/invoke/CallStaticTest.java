package org.codetab.uknit.itest.invoke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class CallStaticTest {
    @InjectMocks
    private CallStatic callStatic;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCallStatic() {
        LocalDate date = Mockito.mock(LocalDate.class);
        int apple = 1;

        when(date.compareTo(LocalDate.now())).thenReturn(apple);

        int actual = callStatic.callStatic(date);

        assertEquals(apple, actual);
    }

    @Test
    public void testStaticCallReturn() {

        Date actual = callStatic.staticCallReturn();
    }

    @Test
    public void testRealCallReturn() {

        Date actual = callStatic.realCallReturn();
    }
}
