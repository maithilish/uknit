package org.codetab.uknit.itest.operator;

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
    public void testCheckRelationalIfAIfAIfA() {

        boolean actual = relational.checkRelational();

        assertTrue(actual);
    }

    @Test
    public void testCheckRelationalIfAIfAElseA() {

        boolean actual = relational.checkRelational();

        assertTrue(actual);
    }

    @Test
    public void testCheckRelationalIfAIfAElseA2() {

        boolean actual = relational.checkRelational();

        assertTrue(actual);
    }

    @Test
    public void testCheckRelationalIfAIfAElseA3() {

        boolean actual = relational.checkRelational();

        assertTrue(actual);
    }

    @Test
    public void testCheckRelationalIfAIfAElseA4() {

        boolean actual = relational.checkRelational();

        assertTrue(actual);
    }

    @Test
    public void testCheckRelationalIfAElseA() {

        boolean actual = relational.checkRelational();

        assertTrue(actual);
    }

    @Test
    public void testCheckRelationalElseA() {

        boolean actual = relational.checkRelational();

        assertTrue(actual);
    }

    @Test
    public void testCheckConditionalIfIf() {

        boolean actual = relational.checkConditional();

        assertTrue(actual);
    }

    @Test
    public void testCheckConditionalIfElse() {

        boolean actual = relational.checkConditional();

        assertTrue(actual);
    }

    @Test
    public void testCheckConditionalElse() {

        boolean actual = relational.checkConditional();

        assertTrue(actual);
    }
}
