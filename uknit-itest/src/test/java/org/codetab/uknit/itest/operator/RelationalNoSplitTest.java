package org.codetab.uknit.itest.operator;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class RelationalNoSplitTest {
    @InjectMocks
    private RelationalNoSplit relationalNoSplit;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCheckRelationalIfAIfAIfA() {

        boolean actual = relationalNoSplit.checkRelational();

        assertTrue(actual);
    }

    @Test
    public void testCheckRelationalIfAIfAElseA() {

        boolean actual = relationalNoSplit.checkRelational();

        assertTrue(actual);
    }

    @Test
    public void testCheckRelationalIfAIfAElseA2() {

        boolean actual = relationalNoSplit.checkRelational();

        assertTrue(actual);
    }

    @Test
    public void testCheckRelationalIfAIfAElseA3() {

        boolean actual = relationalNoSplit.checkRelational();

        assertTrue(actual);
    }

    @Test
    public void testCheckRelationalIfAIfAElseA4() {

        boolean actual = relationalNoSplit.checkRelational();

        assertTrue(actual);
    }

    @Test
    public void testCheckRelationalIfAElseA() {

        boolean actual = relationalNoSplit.checkRelational();

        assertTrue(actual);
    }

    @Test
    public void testCheckRelationalElseA() {

        boolean actual = relationalNoSplit.checkRelational();

        assertTrue(actual);
    }

    @Test
    public void testCheckConditionalIfIf() {

        boolean actual = relationalNoSplit.checkConditional();

        assertTrue(actual);
    }

    @Test
    public void testCheckConditionalIfElse() {

        boolean actual = relationalNoSplit.checkConditional();

        assertTrue(actual);
    }

    @Test
    public void testCheckConditionalElse() {

        boolean actual = relationalNoSplit.checkConditional();

        assertTrue(actual);
    }
}
