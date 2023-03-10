package org.codetab.uknit.itest.lambda;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class MethodReferenceTest {
    @InjectMocks
    private MethodReference methodReference;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    // Not proper

    // @Test
    // public void testFoo() {
    // Properties properties = Mockito.mock(Properties.class);
    // V v = STEPIN;
    // Function<String, V> func = STEPIN;
    // String value = "true";
    //
    // when(func.apply(value)).thenReturn(v);
    // methodReference.foo(properties);
    //
    // fail("unable to assert, STEPIN");
    // }
}
