package org.codetab.uknit.itest.operator;

class RelationalNoSplit {

    public boolean checkRelational() {

        boolean result = true;
        int a = 1;
        int b = 1;

        if (a == b) {
            result = true;
        }
        if (a != b) {
            result = false;
        }
        if (a >= b) {
            result = true;
        }
        if (a <= b) {
            result = true;
        }
        if (a > b) {
            result = false;
        }
        if (a < b) {
            result = false;
        }
        return result;
    }

    public boolean checkConditional() {

        boolean result = true;
        int a = 1;
        int b = 1;
        int x = 1;
        int y = 1;

        if ((a == b) && (x == y)) {
            result = true;
        }

        if ((a == b) || (x == y)) {
            result = true;
        }

        return result;
    }

}
