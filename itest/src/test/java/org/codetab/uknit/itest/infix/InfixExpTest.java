package org.codetab.uknit.itest.infix;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class InfixExpTest {
    @InjectMocks
    private InfixExp infixExp;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReturnInfixLeft() {
        LocalDate date = Mockito.mock(LocalDate.class);
        int apple = 1;

        when(date.compareTo(LocalDate.now())).thenReturn(apple);

        boolean actual = infixExp.returnInfixLeft(date);

        assertTrue(actual);
    }

    @Test
    public void testReturnInfixRight() {
        LocalDate date = Mockito.mock(LocalDate.class);
        int apple = 1;

        when(date.compareTo(LocalDate.now())).thenReturn(apple);

        boolean actual = infixExp.returnInfixRight(date);

        assertTrue(actual);
    }

    @Test
    public void testInfixAdd() {
        int a = 1;
        int b = 1;
        int c = a + b;

        int actual = infixExp.infixAdd(a, b);

        assertEquals(c, actual);
    }
}
