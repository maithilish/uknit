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
        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testSayHello2() {
        helloWorldAnonymousClass.sayHello();
        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testSayHello3() {
        helloWorldAnonymousClass.sayHello();
        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testSayHello4() {
        helloWorldAnonymousClass.sayHello();
        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testSayHello5() {
        helloWorldAnonymousClass.sayHello();
        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testSayHello6() {
        helloWorldAnonymousClass.sayHello();
        // fail("unable to assert, STEPIN");
    }
}
