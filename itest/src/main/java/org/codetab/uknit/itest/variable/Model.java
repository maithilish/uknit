package org.codetab.uknit.itest.variable;

class Model {

    static class StepInfo {
        private String name;

        public String getName() {
            return name;
        }
    }

    static class PrintPayload {
        public String getId() {
            return null;
        }
    }

    static class Screen {
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

    static class Point {

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
}
