package org.codetab.uknit.itest.ret;

public class Bitwise {

    public int bitwiseAnd() {
        int bitmask = 0x000F;
        int val = 0x2222;
        return val & bitmask;
    }

    public int shift() {
        int val = 0x2222;
        return val << 2;
    }

    public int unsignedShift() {
        int val = 0x2222;
        return val >>> 2;
    }

    public int toggle() {
        int val = -1;
        return ~val;
    }
}
