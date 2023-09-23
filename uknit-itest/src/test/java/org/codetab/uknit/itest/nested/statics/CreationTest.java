package org.codetab.uknit.itest.nested.statics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.geom.Arc2D;
import java.awt.geom.Arc2D.Float;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class CreationTest {
    @InjectMocks
    private Creation creation;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateArc() {
        Float arc = new Float(10, 11, 12, 13, 14, 15, Arc2D.CHORD);

        Arc2D actual = creation.createArc();

        assertEquals(arc, actual);
    }

    @Test
    public void testCreateArcQualified() {
        Arc2D.Float arc = new Float(10, 11, 12, 13, 14, 15, Arc2D.CHORD);

        Arc2D.Float actual = creation.createArcQualified();

        assertEquals(arc, actual);
    }
}
