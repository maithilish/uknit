package org.codetab.uknit.itest.nest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class AnonymousClassAbstractTest {
    @InjectMocks
    private AnonymousClassAbstract anonymousClassAbstract;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAdd() {
        Calc calc = Mockito.mock(Calc.class);
        anonymousClassAbstract.add(calc);

        @SuppressWarnings("unchecked")
        ArgumentCaptor<Addable<Integer>> argcA =
                ArgumentCaptor.forClass(Addable.class);

        verify(calc).op(eq(1), eq(2), argcA.capture());

        Addable<Integer> v = argcA.getValue();
        assertThat(5).isEqualTo(v.add(2, 3));
    }
}
