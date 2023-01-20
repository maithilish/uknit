package org.codetab.uknit.itest.initializer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class StepinTest {
    @InjectMocks
    private Stepin stepin;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAccessArrayCreation() {
        int foo = 0;

        int actual = stepin.accessArrayCreation();

        assertEquals(foo, actual);
    }

    @Test
    public void testAccessArrayInitializer() {
        int foo = 1;

        int actual = stepin.accessArrayInitializer();

        assertEquals(foo, actual);
    }

    @Test
    public void testAccessArrayNewInitializer() {
        int foo = 1;

        int actual = stepin.accessArrayNewInitializer();

        assertEquals(foo, actual);
    }

    @Test
    public void testInvokeReal() {
        String foo = "foo";

        String actual = stepin.invokeReal();

        assertEquals(foo, actual);
    }

    @Test
    public void testInvokeRealOfMockType() {
        Set<String> attr = new HashSet<>();

        Set<String> actual = stepin.invokeRealOfMockType();

        assertEquals(attr, actual);
    }
}
