package org.codetab.uknit.itest.nested.statics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class AccessEnclosingTest {
    @InjectMocks
    private AccessEnclosing accessEnclosing;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAccessNestedStaticMethod() {
        String apple = "Speed 10";

        String actual = AccessEnclosing.accessNestedStaticMethod();

        assertEquals(apple, actual);
    }

    @Test
    public void testFly() {
        String grape = "Speed 10";

        String actual = accessEnclosing.fly();

        assertEquals(grape, actual);
    }
}
