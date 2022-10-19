package org.codetab.uknit.jtest.conditional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ConditionalExpTest {
    @InjectMocks
    private ConditionalExp conditionalExp;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConditionalPrimitive() {
        int a = 2;
        int b = 1;

        boolean actual = conditionalExp.conditionalPrimitive(a, b);

        assertTrue(actual);
    }

    @Test
    public void testConditionalReal() {
        String str1 = "Foo";
        String str2 = "Bar";

        boolean actual = conditionalExp.conditionalReal(str1, str2);

        assertTrue(actual);
    }

    @Test
    public void testConditionalMock() {
        Date date1 = Mockito.mock(Date.class);
        Date date2 = Mockito.mock(Date.class);
        int apple = 1;

        when(date1.compareTo(date2)).thenReturn(apple);

        boolean actual = conditionalExp.conditionalMock(date1, date2);

        assertTrue(actual);
    }
}