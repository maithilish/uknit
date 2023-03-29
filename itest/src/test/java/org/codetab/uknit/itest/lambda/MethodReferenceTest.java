package org.codetab.uknit.itest.lambda;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Properties;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class MethodReferenceTest {
    @InjectMocks
    private MethodReference methodReference;

    @Mock
    private External external;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testInExternalArg() {
        boolean apple = true;

        when(external.apply(eq("true"), any(Function.class))).thenReturn(apple);

        boolean actual = methodReference.inExternalArg();

        assertTrue(actual);

        ArgumentCaptor<Function<String, Boolean>> captorA =
                ArgumentCaptor.forClass(Function.class);

        verify(external).apply(eq("true"), captorA.capture());
    }

    @Test
    public void testInInternalArg() {
        Properties properties = Mockito.mock(Properties.class);
        String value = "Foo";
        methodReference.inInternalArg(properties, value);

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testInNestedInternal() {
        Properties properties = Mockito.mock(Properties.class);
        methodReference.inNestedInternal(properties);

        // fail("unable to assert, STEPIN");
    }
}
