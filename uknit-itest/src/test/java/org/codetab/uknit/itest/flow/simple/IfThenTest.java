package org.codetab.uknit.itest.flow.simple;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class IfThenTest {
    @InjectMocks
    private IfThen ifThen;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIfThenIfDate1CompareTo() {
        Date date1 = Mockito.mock(Date.class);
        Date date2 = Mockito.mock(Date.class);
        int apple = 1;
        int result2 = 1;

        when(date1.compareTo(date2)).thenReturn(apple);

        int actual = ifThen.ifThen(date1, date2);

        assertEquals(result2, actual);
    }

    @Test
    public void testIfThenElseDate1CompareTo() {
        Date date1 = Mockito.mock(Date.class);
        Date date2 = Mockito.mock(Date.class);
        int result = 0;
        int apple = 0;

        when(date1.compareTo(date2)).thenReturn(apple);

        int actual = ifThen.ifThen(date1, date2);

        assertEquals(result, actual);
    }

    @Test
    public void testIfThenElseIfDate1CompareTo() {
        Date date1 = Mockito.mock(Date.class);
        Date date2 = Mockito.mock(Date.class);
        Date date3 = Mockito.mock(Date.class);
        int apple = 1;
        int result2 = 1;
        int grape = 1;

        when(date1.compareTo(date2)).thenReturn(apple);
        when(date2.compareTo(date3)).thenReturn(grape);

        int actual = ifThen.ifThenElse(date1, date2, date3);

        assertEquals(result2, actual);
    }

    @Test
    public void testIfThenElseElseDate1CompareToIfelse() {
        Date date1 = Mockito.mock(Date.class);
        Date date2 = Mockito.mock(Date.class);
        Date date3 = Mockito.mock(Date.class);
        int apple = 0;
        int grape = 1;
        int result3 = 2;

        when(date1.compareTo(date2)).thenReturn(apple);
        when(date2.compareTo(date3)).thenReturn(grape);

        int actual = ifThen.ifThenElse(date1, date2, date3);

        assertEquals(result3, actual);
    }

    @Test
    public void testIfThenElseElseDate1CompareToElseelse() {
        Date date1 = Mockito.mock(Date.class);
        Date date2 = Mockito.mock(Date.class);
        Date date3 = Mockito.mock(Date.class);
        int apple = 0;
        int grape = 0;
        int result4 = 3;

        when(date1.compareTo(date2)).thenReturn(apple);
        when(date2.compareTo(date3)).thenReturn(grape);

        int actual = ifThen.ifThenElse(date1, date2, date3);

        assertEquals(result4, actual);
    }
}
