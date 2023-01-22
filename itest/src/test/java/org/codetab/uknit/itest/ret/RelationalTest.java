package org.codetab.uknit.itest.ret;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class RelationalTest {
    @InjectMocks
    private Relational relational;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCheckRelational() {

        boolean actual = relational.checkRelational();

        assertTrue(actual);
    }

    @Test
    public void testCheckRelationalGt() {

        boolean actual = relational.checkRelationalGt();

        assertFalse(actual);
    }

    @Test
    public void testCheckConditional() {

        boolean actual = relational.checkConditional();

        assertTrue(actual);
    }
}
