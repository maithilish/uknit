package org.codetab.uknit.itest.lambda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Properties;
import java.util.function.BiConsumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class LambdaInArgGenericTest {
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
        Integer apple = Integer.valueOf(1);
        Integer divValue = Integer.valueOf(1);
        Integer grape = Integer.valueOf(1);

        when(calcB.op(eq(1), eq(2), any(OperationB.class))).thenReturn(apple);
        when(calcB.op(eq(8), eq(4), any(OperationB.class)))
                .thenReturn(divValue);
        when(calcB.op(eq(6), eq(3), any(OperationB.class))).thenReturn(grape);

        Integer actual = lambdaInArgGeneric.calculate(calcB);

        assertEquals(grape, actual);

        ArgumentCaptor<OperationB<Integer>> captorA =
                ArgumentCaptor.forClass(OperationB.class);
        ArgumentCaptor<OperationB<Integer>> captorB =
                ArgumentCaptor.forClass(OperationB.class);
        ArgumentCaptor<OperationB<Integer>> captorC =
                ArgumentCaptor.forClass(OperationB.class);
        verify(calcB).op(eq(1), eq(2), captorA.capture());
        verify(calcB).op(eq(8), eq(4), captorB.capture());
        verify(calcB).op(eq(6), eq(3), captorC.capture());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCastInlambda() {
        Properties properties = Mockito.mock(Properties.class);
        Config<String, String> config = Mockito.mock(Config.class);
        lambdaInArgGeneric.castInlambda(properties, config);
        ArgumentCaptor<BiConsumer<? super Object, ? super Object>> captorA =
                ArgumentCaptor.forClass(BiConsumer.class);

        verify(properties).forEach(captorA.capture());
    }
}
