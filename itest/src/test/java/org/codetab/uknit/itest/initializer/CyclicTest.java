package org.codetab.uknit.itest.initializer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.initializer.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class CyclicTest {
    @InjectMocks
    private Cyclic cyclic;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCyclicInInitializersReal() {
        cyclic.cyclicInInitializersReal();

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testCyclicInInitializersMock() {
        Foo foo = Mockito.mock(Foo.class);
        Foo value = foo;
        Object result = value;
        String orange = "Foo";
        // String apple = "Baz";
        String apple = "Foo";

        when(result.toString()).thenReturn(orange);

        String actual = cyclic.cyclicInInitializersMock(foo);

        assertEquals(apple, actual);
    }
}
