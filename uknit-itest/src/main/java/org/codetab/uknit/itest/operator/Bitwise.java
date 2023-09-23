package org.codetab.uknit.itest.operator;

class Bitwise {

    // STEPIN - result is set 1 but actually 2
    public int bitwiseAnd() {
        int bitmask = 0x000F;
        int val = 0x2222;
        int result = (val & bitmask);
        return result;
    }

    public int shift() {
        int val = 0x2222;
        int result = val << 2;
        return result;
    }

    public int unsignedShift() {
        int val = 0x2222;
        int result = val >>> 2;
        return result;
    }

    public int toggle() {
        int val = -1;
        int result = ~val;
        return result;
    }
}
