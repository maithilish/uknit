package org.codetab.uknit.itest.initializer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class StepinInternalTest {
    @InjectMocks
    private StepinInternal stepinInternal;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAccessArrayCreation() {
        int foo2 = 0;
        int foo = foo2;

        int actual = stepinInternal.accessArrayCreation();

        assertEquals(foo, actual);
    }

    @Test
    public void testAccessArrayInitializer() {
        int foo2 = 1;
        int foo = foo2;

        int actual = stepinInternal.accessArrayInitializer();

        assertEquals(foo, actual);
    }

    @Test
    public void testAccessArrayNewInitializer() {
        int foo2 = 1;
        int foo = foo2;

        int actual = stepinInternal.accessArrayNewInitializer();

        assertEquals(foo, actual);
    }

    @Test
    public void testInvokeReal() {
        String foo2 = "foo";
        String foo = foo2;

        String actual = stepinInternal.invokeReal();

        assertEquals(foo, actual);
    }

    @Test
    public void testInvokeRealOfMockType() {
        Set<String> attr2 = new HashSet<>();
        Set<String> attr = attr2;

        Set<String> actual = stepinInternal.invokeRealOfMockType();

        assertEquals(attr, actual);
    }
}
