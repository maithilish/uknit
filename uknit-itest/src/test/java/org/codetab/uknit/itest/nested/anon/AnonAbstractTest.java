package org.codetab.uknit.itest.nested.anon;

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

class AnonAbstractTest {
    @InjectMocks
    private AnonAbstract anonAbstract;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testAdd() {
        Calc calc = Mockito.mock(Calc.class);
        Integer apple = Integer.valueOf(1);

        when(calc.op(eq(1), eq(2), any(Addable.class))).thenReturn(apple);
        anonAbstract.add(calc);

        ArgumentCaptor<Addable<Integer>> captorA =
                ArgumentCaptor.forClass(Addable.class);

        verify(calc).op(eq(1), eq(2), captorA.capture());

        assertEquals(5, captorA.getValue().add(3, 2));
    }
}
