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

class AnonParameterizedTest {
    @InjectMocks
    private AnonParameterized anonParameterized;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testAdd() {
        Calc2 calc = Mockito.mock(Calc2.class);
        Integer apple = Integer.valueOf(1);

        when(calc.op(eq(1), eq(2), any(IAddable.class))).thenReturn(apple);
        anonParameterized.add(calc);

        ArgumentCaptor<IAddable<Integer>> captorA =
                ArgumentCaptor.forClass(IAddable.class);

        verify(calc).op(eq(1), eq(2), captorA.capture());

        assertEquals(13, captorA.getValue().add(8, 5));
    }
}
