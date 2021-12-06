package org.codetab.uknit.itest.ret;

public class Unmodifiable {

    // byte
    public Byte returnByte() {
        return Byte.valueOf("100");
    }

    public Byte returnByteVar() {
        Byte value = Byte.valueOf("101");
        return value;
    }

    public Byte returnByteNull() {
        return null;
    }

    public Byte returnByteVarNull() {
        Byte value = null;
        return value;
    }

    public Byte returnByteVarConfigured(final Byte value) {
        return value;
    }

    // short
    public Short returnShort() {
        return Short.valueOf("7");
    }

    public Short returnShortVar() {
        Short value = Short.valueOf("7");
        return value;
    }

    public Short returnShortNull() {
        return null;
    }

    public Short returnShortVarNull() {
        Short value = null;
        return value;
    }

    public Short returnShortVarConfigured(final Short value) {
        return value;
    }

    // character
    public Character returnCharacter() {
        return Character.valueOf('A');
    }

    public Character returnCharacterVar() {
        Character value = Character.valueOf('B');
        return value;
    }

    public Character returnCharacterNull() {
        return null;
    }

    public Character returnCharacterVarNull() {
        Character value = null;
        return value;
    }

    public Character returnCharacterVarConfigured(final Character value) {
        return value;
    }

    // integer
    public Integer returnInteger() {
        return Integer.valueOf(7);
    }

    public Integer returnIntegerVar() {
        Integer value = Integer.valueOf(7);
        return value;
    }

    public Integer returnIntegerNull() {
        return null;
    }

    public Integer returnIntegerVarNull() {
        Integer value = null;
        return value;
    }

    public Integer returnIntegerVarConfigured(final Integer value) {
        return value;
    }

    // long
    public Long returnLong() {
        return Long.valueOf(200L);
    }

    public Long returnLongVar() {
        Long value = Long.valueOf(300L);
        return value;
    }

    public Long returnLongNull() {
        return null;
    }

    public Long returnLongVarNull() {
        Long value = null;
        return value;
    }

    public Long returnLongVarConfigured(final Long value) {
        return value;
    }

    // float
    public Float returnFloat() {
        return Float.valueOf(200.10f);
    }

    public Float returnFloatVar() {
        Float value = Float.valueOf(300.20f);
        return value;
    }

    public Float returnFloatNull() {
        return null;
    }

    public Float returnFloatVarNull() {
        Float value = null;
        return value;
    }

    public Float returnFloatVarConfigured(final Float value) {
        return value;
    }

    // double
    public Double returnDouble() {
        return Double.valueOf(400.10d);
    }

    public Double returnDoubleVar() {
        Double value = Double.valueOf(500.20d);
        return value;
    }

    public Double returnDoubleNull() {
        return null;
    }

    public Double returnDoubleVarNull() {
        Double value = null;
        return value;
    }

    public Double returnDoubleVarConfigured(final Double value) {
        return value;
    }

    // Boolean
    public Boolean returnBoolean() {
        return Boolean.valueOf(true);
    }

    public Boolean returnBooleanVar() {
        Boolean value = Boolean.valueOf(false);
        return value;
    }

    public Boolean returnBooleanNull() {
        return null;
    }

    public Boolean returnBooleanVarNull() {
        Boolean value = null;
        return value;
    }

    public Boolean returnBooleanVarConfigured(final Boolean value) {
        return value;
    }

    // String
    public String returnString() {
        return String.valueOf("foo");
    }

    public String returnStringVar() {
        String value = String.valueOf("bar");
        return value;
    }

    public String returnStringNull() {
        return null;
    }

    public String returnStringVarNull() {
        String value = null;
        return value;
    }

    public String returnStringVarConfigured(final String value) {
        return value;
    }

    // Class
    public Class<?> returnClass() {
        return String.class;
    }

    public Class<?> returnClassVar() {
        Class<?> value = String.class;
        return value;
    }

    public Class<?> returnClassNull() {
        return null;
    }

    public Class<?> returnClassVarNull() {
        Class<?> value = null;
        return value;
    }

    // STEPIN - value is not initialized by mow
    public Class<?> returnClassVarConfigured(final Class<?> value) {
        return value;
    }
}
