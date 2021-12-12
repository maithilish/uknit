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

public class AnonymousClassParameterizedTest {
    @InjectMocks
    private AnonymousClassParameterized anonymousClassParameterized;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testAdd() {
        Calc2 calc = Mockito.mock(Calc2.class);
        anonymousClassParameterized.add(calc);

        ArgumentCaptor<IAddable<Integer>> argcA =
                ArgumentCaptor.forClass(IAddable.class);

        verify(calc).op(eq(1), eq(2), argcA.capture());

        IAddable<Integer> v = argcA.getValue();
        assertThat(5).isEqualTo(v.add(2, 3));
    }
}
