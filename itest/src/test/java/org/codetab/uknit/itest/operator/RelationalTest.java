package org.codetab.uknit.itest.operator;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class RelationalTest {
    @InjectMocks
    private Relational relational;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCheckRelationalIf() {

        boolean actual = relational.checkRelational();

        assertTrue(actual);
    }

    @Test
    public void testCheckRelational() {

        boolean actual = relational.checkRelational();

        assertTrue(actual);
    }

    @Test
    public void testCheckRelationalIf2() {

        boolean actual = relational.checkRelational();

        assertTrue(actual);
    }

    @Test
    public void testCheckRelational2() {

        boolean actual = relational.checkRelational();

        assertTrue(actual);
    }

    @Test
    public void testCheckRelationalIf3() {

        boolean actual = relational.checkRelational();

        assertTrue(actual);
    }

    @Test
    public void testCheckRelational3() {

        boolean actual = relational.checkRelational();

        assertTrue(actual);
    }

    @Test
    public void testCheckRelationalIf4() {

        boolean actual = relational.checkRelational();

        assertTrue(actual);
    }

    @Test
    public void testCheckRelational4() {

        boolean actual = relational.checkRelational();

        assertTrue(actual);
    }

    @Test
    public void testCheckRelationalIf5() {

        boolean actual = relational.checkRelational();

        assertTrue(actual);
    }

    @Test
    public void testCheckRelational5() {

        boolean actual = relational.checkRelational();

        assertTrue(actual);
    }

    @Test
    public void testCheckRelationalIf6() {

        boolean actual = relational.checkRelational();

        assertTrue(actual);
    }

    @Test
    public void testCheckRelational6() {

        boolean actual = relational.checkRelational();

        assertTrue(actual);
    }

    @Test
    public void testCheckConditionalIf() {

        boolean actual = relational.checkConditional();

        assertTrue(actual);
    }

    @Test
    public void testCheckConditional() {

        boolean actual = relational.checkConditional();

        assertTrue(actual);
    }

    @Test
    public void testCheckConditionalIf2() {

        boolean actual = relational.checkConditional();

        assertTrue(actual);
    }

    @Test
    public void testCheckConditional2() {

        boolean actual = relational.checkConditional();

        assertTrue(actual);
    }
}
