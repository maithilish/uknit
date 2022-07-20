package org.codetab.uknit.itest.flow.nosplit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class IfThenTest {
    @InjectMocks
    private IfThen ifThen;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIfThen() {
        Date date1 = Mockito.mock(Date.class);
        Date date2 = Mockito.mock(Date.class);
        int result = 1;
        int apple = 1;

        when(date1.compareTo(date2)).thenReturn(apple);

        int actual = ifThen.ifThen(date1, date2);

        assertEquals(result, actual);
    }

    @Test
    public void testIfThenElse() {
        Date date1 = Mockito.mock(Date.class);
        Date date2 = Mockito.mock(Date.class);
        Date date3 = Mockito.mock(Date.class);
        int result = 3;
        int apple = 0;
        int orange = 0;

        when(date1.compareTo(date2)).thenReturn(apple);
        when(date2.compareTo(date3)).thenReturn(orange);

        int actual = ifThen.ifThenElse(date1, date2, date3);

        assertEquals(result, actual);
    }
}
