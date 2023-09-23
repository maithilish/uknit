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

class LambdaInArgTest {
    @InjectMocks
    private LambdaInArg lambdaInArg;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculate() {
        CalcA calcA = Mockito.mock(CalcA.class);
        int apple = 1;
        int divValue = 1;
        int grape = 1;

        when(calcA.op(eq(1), eq(2), any(OperationA.class))).thenReturn(apple);
        when(calcA.op(eq(8), eq(4), any(OperationA.class)))
                .thenReturn(divValue);
        when(calcA.op(eq(6), eq(3), any(OperationA.class))).thenReturn(grape);

        int actual = lambdaInArg.calculate(calcA);

        assertEquals(grape, actual);

        ArgumentCaptor<OperationA> captorA =
                ArgumentCaptor.forClass(OperationA.class);
        ArgumentCaptor<OperationA> captorB =
                ArgumentCaptor.forClass(OperationA.class);
        ArgumentCaptor<OperationA> captorC =
                ArgumentCaptor.forClass(OperationA.class);
        verify(calcA).op(eq(1), eq(2), captorA.capture());
        verify(calcA).op(eq(8), eq(4), captorB.capture());
        verify(calcA).op(eq(6), eq(3), captorC.capture());
    }
}
