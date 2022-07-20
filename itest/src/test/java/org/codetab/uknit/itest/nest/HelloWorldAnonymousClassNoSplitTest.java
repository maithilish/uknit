package org.codetab.uknit.itest.nest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class HelloWorldAnonymousClassNoSplitTest {
    @InjectMocks
    private HelloWorldAnonymousClassNoSplit helloWorldAnonymousClassNoSplit;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSayHello() {
        helloWorldAnonymousClassNoSplit.sayHello();
        // fail("unable to assert, STEPIN");
    }
}
