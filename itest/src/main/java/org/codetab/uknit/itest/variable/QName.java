package org.codetab.uknit.itest.variable;

import java.util.AbstractMap.SimpleEntry;

import org.codetab.uknit.itest.variable.Model.Point;
import org.codetab.uknit.itest.variable.Model.Screen;

class QName {

    public int returnQName(final Point point) {
        return point.x;
    }

    public int qNameInArg(final Point point) {
        point.setY(point.x);
        return point.y;
    }

    // STEPIN - initialize coordinates array
    public int qNameInAssign(final Point point, final int[] coordinates) {
        point.x = point.y;
        point.y = coordinates[1];
        return point.y;
    }

    public int[] assignQNameToArray(final Point point) {
        int[] coordinates = new int[2];
        coordinates[0] = point.x;
        coordinates[1] = point.y;
        return coordinates;
    }

    public int assignQNameFromGetter(final Point point) {
        point.x = point.getY();
        return point.y;
    }

    public boolean qNameInfix(final Point point) {
        boolean flg = point.x == point.y;
        return flg;
    }

    public boolean returnQNameInfix(final Point point) {
        return point.x == point.y;
    }

    /*
     * STEPIN - change mock to spy, field access by qName, set point.desc
     */
    public char qNameAsCallRealObj(final Point point) {
        final int pos = 5;
        return point.desc.charAt(pos);
    }

    /*
     * TODO L - explore creating spy instead of mock if field access.
     *
     * STEPIN - change mock to spy, field access by qName, set screen.point =
     * point
     */
    public int qNameAsCallMockObj(final Screen screen, final Point point) {
        screen.setPoint(point);
        return screen.point.getX();
    }

    public String qNameInCast(final Point point) {
        return (String) point.attchment;
    }

    public SimpleEntry<Integer, Integer> qNameInCreate(final Point point) {
        return new SimpleEntry<Integer, Integer>(point.x, point.y);
    }
}
