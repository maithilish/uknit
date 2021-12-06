package org.codetab.uknit.itest.ret;

public class UnmodifiablePrimitive {

    // byte
    public byte returnbyte() {
        return 100;
    }

    public byte returnbyteVar() {
        byte value = 101;
        return value;
    }

    public byte returnbyteVarConfigured(final byte value) {
        return value;
    }

    // short
    public short returnshort() {
        return 7;
    }

    public short returnshortVar() {
        short value = 8;
        return value;
    }

    public short returnshortVarConfigured(final short value) {
        return value;
    }

    // character
    public char returnchar() {
        return 'A';
    }

    public char returncharVar() {
        char value = 'B';
        return value;
    }

    public char returncharVarConfigured(final char value) {
        return value;
    }

    // integer
    public int returnint() {
        return 7;
    }

    public int returnintVar() {
        int value = 8;
        return value;
    }

    public int returnintVarConfigured(final int value) {
        return value;
    }

    // long
    public long returnlong() {
        return 200L;
    }

    public long returnlongVar() {
        long value = 300L;
        return value;
    }

    public long returnlongVarConfigured(final long value) {
        return value;
    }

    // float
    public float returnfloat() {
        return 200.10f;
    }

    public float returnfloatVar() {
        float value = 300.20f;
        return value;
    }

    public float returnfloatVarConfigured(final float value) {
        return value;
    }

    // double
    public double returndouble() {
        return 400.10d;
    }

    public double returndoubleVar() {
        double value = 500.20d;
        return value;
    }

    public double returndoubleVarConfigured(final double value) {
        return value;
    }

    // boolean
    public boolean returnboolean() {
        return true;
    }

    public boolean returnbooleanVar() {
        boolean value = false;
        return value;
    }

    public boolean returnbooleanVarConfigured(final boolean value) {
        return value;
    }
}
