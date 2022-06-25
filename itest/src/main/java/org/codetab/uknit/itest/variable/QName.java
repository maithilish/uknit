package org.codetab.uknit.itest.variable;

import java.util.AbstractMap.SimpleEntry;

public class QName {

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
     * STEPIN - field access by qName, set point.desc
     */
    public char qNameAsCallRealObj(final Point point) {
        final int pos = 5;
        return point.desc.charAt(pos);
    }

    /*
     * STEPIN - field access by qName, set screen.point = point
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

class Screen {
    // CHECKSTYLE:OFF
    Point point;
    // CHECKSTYLE:ON

    public Point getPoint() {
        return point;
    }

    public void setPoint(final Point point) {
        this.point = point;
    }
}

class Point {

    // CHECKSTYLE:OFF
    int x;
    int y;
    String desc;
    CharSequence attchment;
    // CHECKSTYLE:ON

    public int getX() {
        return x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(final int y) {
        this.y = y;
    }
}
