package org.codetab.uknit.itest.variable;

public class Primitive {

    public char charLiteral() {
        char capitalC = 'C';
        return capitalC;
    }

    public int intLiteral() {
        int i = 100000;
        return i;
    }

    public int hexLiteral() {
        int hexVal = 0x1a;
        return hexVal;
    }

    public int binLiteral() {
        int binVal = 0b11010;
        return binVal;
    }

    public boolean boolLiteral() {
        boolean result = true;
        return result;
    }

    public byte byteLiteral() {
        byte b = 100;
        return b;
    }

    public short shortLiteral() {
        short s = 10000;
        return s;
    }

    public double doubleLiteral() {
        double d1 = 123.4;
        return d1;
    }

    public double doubleELiteral() {
        double d2 = 1.234e2;
        return d2;
    }

    public float floatLiteral() {
        float f1 = 123.4f;
        return f1;
    }

    public long longUnderscore() {
        long creditCardNumber = 1234_5678_9012_3456L;
        return creditCardNumber;
    }

    public float floatUnderscore() {
        float pi = 3.14_15F;
        return pi;
    }

    public long hexByteUnderscore() {
        long hexBytes = 0xFF_EC_DE_5E;
        return hexBytes;
    }

    public long hexWordUnderscore() {
        long hexWords = 0xCAFE_BABE;
        return hexWords;
    }

    public byte binUnderscore() {
        byte nybbles = 0b0010_0101;
        return nybbles;
    }

    public long binWordUnderscore() {
        long bytes = 0b11010010_01101001_10010100_10010010;
        return bytes;
    }
}
