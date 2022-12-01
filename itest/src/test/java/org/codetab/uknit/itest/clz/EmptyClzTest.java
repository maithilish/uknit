package org.codetab.uknit.itest.clz;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class EmptyClzTest {
    @InjectMocks
    private EmptyClz emptyClz;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }
}
