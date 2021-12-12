package org.codetab.uknit.itest.lambda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class LambdaInArgGenericTest {
    @InjectMocks
    private LambdaInArgGeneric lambdaInArgGeneric;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCalculate() {
        CalcB calcB = Mockito.mock(CalcB.class);
        int divValue = 1;
        Integer kiwi = Integer.valueOf(1);

        when(calcB.op(eq(8), eq(4), any(OperationB.class)))
                .thenReturn(divValue);
        when(calcB.op(eq(6), eq(3), any(OperationB.class))).thenReturn(kiwi);

        Integer actual = lambdaInArgGeneric.calculate(calcB);

        assertEquals(kiwi, actual);

        ArgumentCaptor<OperationB<Integer>> argcA =
                ArgumentCaptor.forClass(OperationB.class);
        ArgumentCaptor<OperationB<Integer>> argcB =
                ArgumentCaptor.forClass(OperationB.class);
        ArgumentCaptor<OperationB<Integer>> argcC =
                ArgumentCaptor.forClass(OperationB.class);
        verify(calcB).op(eq(1), eq(2), argcA.capture());
        verify(calcB).op(eq(8), eq(4), argcB.capture());
        verify(calcB).op(eq(6), eq(3), argcC.capture());
    }
}
