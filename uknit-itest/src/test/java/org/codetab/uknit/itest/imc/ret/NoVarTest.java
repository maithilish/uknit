package org.codetab.uknit.itest.imc.ret;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class NoVarTest {
    @InjectMocks
    private NoVar noVar;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFoo() {
        String grape = null;
        String apple = grape;

        String actual = noVar.foo();

        assertEquals(apple, actual);
    }
}
