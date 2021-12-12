package org.codetab.uknit.itest.nest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class HelloWorldAnonymousClassTest {
    @InjectMocks
    private HelloWorldAnonymousClass helloWorldAnonymousClass;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSayHello() {
        helloWorldAnonymousClass.sayHello();
    }
}
