package org.codetab.uknit.itest.nested.statics;

import java.awt.geom.Arc2D;
import java.awt.geom.Arc2D.Float;

/**
 * Create nested static instance.
 *
 * @author Maithilish
 *
 */
public class Creation {

    public Arc2D createArc() {
        Arc2D arc = new Float(0, 1, 2, 3, 4, 5, Arc2D.CHORD);
        Float arc2 = new Float(10, 11, 12, 13, 14, 15, Arc2D.CHORD);
        arc.setArc(arc2);
        return arc;
    }

    public Arc2D.Float createArcQualified() {
        Arc2D.Float arc = new Arc2D.Float(0, 1, 2, 3, 4, 5, Arc2D.CHORD);
        Arc2D.Float arc2 = new Arc2D.Float(10, 11, 12, 13, 14, 15, Arc2D.CHORD);
        arc.setArc(arc2);
        return arc;
    }
}
