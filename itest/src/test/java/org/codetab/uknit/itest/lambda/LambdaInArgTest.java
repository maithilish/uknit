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

public class LambdaInArgTest {
    @InjectMocks
    private LambdaInArg lambdaInArg;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculate() {
        CalcA calcA = Mockito.mock(CalcA.class);
        int divValue = 1;
        int kiwi = 1;

        when(calcA.op(eq(8), eq(4), any(OperationA.class)))
                .thenReturn(divValue);
        when(calcA.op(eq(6), eq(3), any(OperationA.class))).thenReturn(kiwi);

        int actual = lambdaInArg.calculate(calcA);

        assertEquals(kiwi, actual);

        ArgumentCaptor<OperationA> argcA =
                ArgumentCaptor.forClass(OperationA.class);
        ArgumentCaptor<OperationA> argcB =
                ArgumentCaptor.forClass(OperationA.class);
        ArgumentCaptor<OperationA> argcC =
                ArgumentCaptor.forClass(OperationA.class);
        verify(calcA).op(eq(1), eq(2), argcA.capture());
        verify(calcA).op(eq(8), eq(4), argcB.capture());
        verify(calcA).op(eq(6), eq(3), argcC.capture());
    }
}
