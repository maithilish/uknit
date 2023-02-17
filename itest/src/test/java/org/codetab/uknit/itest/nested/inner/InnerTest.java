package org.codetab.uknit.itest.nested.inner;

import static org.mockito.Mockito.verify;

import org.codetab.uknit.itest.nested.inner.Model.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class InnerTest {
    @InjectMocks
    private Inner inner;

    @Mock
    private Logger logger;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPrintEven() {
        inner.printEven();

        verify(logger).log("0 ");
        verify(logger).log("2 ");
        verify(logger).log("4 ");
    }
}
