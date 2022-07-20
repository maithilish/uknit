package org.codetab.uknit.itest.exception.nosplit;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class CatchMultipleTest {
    @InjectMocks
    private CatchMultiple catchMultiple;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRun() {
        Config config = Mockito.mock(Config.class);
        String key = "foo";
        IllegalStateException e = Mockito.mock(IllegalStateException.class);
        String apple = "Foo";
        String grape = "Bar";

        when(e.getLocalizedMessage()).thenReturn(apple).thenReturn(grape);
        catchMultiple.run(config);

        verify(config).getConfigA(key);
        verify(config).getConfigB(key);
        verify(config).getConfigC(key);
    }
}
