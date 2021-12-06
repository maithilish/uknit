package org.codetab.uknit.itest.ret;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class UnmodifiableTest {
    @InjectMocks
    private Unmodifiable unmodifiable;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReturnByte() {
        Byte expected = Byte.valueOf("100");

        Byte actual = unmodifiable.returnByte();

        assertEquals(expected, actual);
    }

    @Test
    public void testReturnByteVar() {
        Byte value = Byte.valueOf("101");

        Byte actual = unmodifiable.returnByteVar();

        assertEquals(value, actual);
    }

    @Test
    public void testReturnByteNull() {
        Byte apple = null;

        Byte actual = unmodifiable.returnByteNull();

        assertEquals(apple, actual);
    }

    @Test
    public void testReturnByteVarNull() {
        Byte value = null;

        Byte actual = unmodifiable.returnByteVarNull();

        assertEquals(value, actual);
    }

    @Test
    public void testReturnByteVarConfigured() {
        Byte value = Byte.valueOf("65");

        Byte actual = unmodifiable.returnByteVarConfigured(value);

        assertEquals(value, actual);
    }

    @Test
    public void testReturnShort() {
        Short expected = Short.valueOf("7");

        Short actual = unmodifiable.returnShort();

        assertEquals(expected, actual);
    }

    @Test
    public void testReturnShortVar() {
        Short value = Short.valueOf("7");

        Short actual = unmodifiable.returnShortVar();

        assertEquals(value, actual);
    }

    @Test
    public void testReturnShortNull() {
        Short apple = null;

        Short actual = unmodifiable.returnShortNull();

        assertEquals(apple, actual);
    }

    @Test
    public void testReturnShortVarNull() {
        Short value = null;

        Short actual = unmodifiable.returnShortVarNull();

        assertEquals(value, actual);
    }

    @Test
    public void testReturnShortVarConfigured() {
        Short value = Short.valueOf("1");

        Short actual = unmodifiable.returnShortVarConfigured(value);

        assertEquals(value, actual);
    }

    @Test
    public void testReturnCharacter() {
        Character expected = Character.valueOf('A');

        Character actual = unmodifiable.returnCharacter();

        assertEquals(expected, actual);
    }

    @Test
    public void testReturnCharacterVar() {
        Character value = Character.valueOf('B');

        Character actual = unmodifiable.returnCharacterVar();

        assertEquals(value, actual);
    }

    @Test
    public void testReturnCharacterNull() {
        Character apple = null;

        Character actual = unmodifiable.returnCharacterNull();

        assertEquals(apple, actual);
    }

    @Test
    public void testReturnCharacterVarNull() {
        Character value = null;

        Character actual = unmodifiable.returnCharacterVarNull();

        assertEquals(value, actual);
    }

    @Test
    public void testReturnCharacterVarConfigured() {
        Character value = Character.valueOf('A');

        Character actual = unmodifiable.returnCharacterVarConfigured(value);

        assertEquals(value, actual);
    }

    @Test
    public void testReturnInteger() {
        Integer expected = Integer.valueOf(7);

        Integer actual = unmodifiable.returnInteger();

        assertEquals(expected, actual);
    }

    @Test
    public void testReturnIntegerVar() {
        Integer value = Integer.valueOf(7);

        Integer actual = unmodifiable.returnIntegerVar();

        assertEquals(value, actual);
    }

    @Test
    public void testReturnIntegerNull() {
        Integer apple = null;

        Integer actual = unmodifiable.returnIntegerNull();

        assertEquals(apple, actual);
    }

    @Test
    public void testReturnIntegerVarNull() {
        Integer value = null;

        Integer actual = unmodifiable.returnIntegerVarNull();

        assertEquals(value, actual);
    }

    @Test
    public void testReturnIntegerVarConfigured() {
        Integer value = Integer.valueOf(1);

        Integer actual = unmodifiable.returnIntegerVarConfigured(value);

        assertEquals(value, actual);
    }

    @Test
    public void testReturnLong() {
        Long expected = Long.valueOf(200L);

        Long actual = unmodifiable.returnLong();

        assertEquals(expected, actual);
    }

    @Test
    public void testReturnLongVar() {
        Long value = Long.valueOf(300L);

        Long actual = unmodifiable.returnLongVar();

        assertEquals(value, actual);
    }

    @Test
    public void testReturnLongNull() {
        Long apple = null;

        Long actual = unmodifiable.returnLongNull();

        assertEquals(apple, actual);
    }

    @Test
    public void testReturnLongVarNull() {
        Long value = null;

        Long actual = unmodifiable.returnLongVarNull();

        assertEquals(value, actual);
    }

    @Test
    public void testReturnLongVarConfigured() {
        Long value = Long.valueOf(1L);

        Long actual = unmodifiable.returnLongVarConfigured(value);

        assertEquals(value, actual);
    }

    @Test
    public void testReturnFloat() {
        Float expected = Float.valueOf(200.10f);

        Float actual = unmodifiable.returnFloat();

        assertEquals(expected, actual);
    }

    @Test
    public void testReturnFloatVar() {
        Float value = Float.valueOf(300.20f);

        Float actual = unmodifiable.returnFloatVar();

        assertEquals(value, actual);
    }

    @Test
    public void testReturnFloatNull() {
        Float apple = null;

        Float actual = unmodifiable.returnFloatNull();

        assertEquals(apple, actual);
    }

    @Test
    public void testReturnFloatVarNull() {
        Float value = null;

        Float actual = unmodifiable.returnFloatVarNull();

        assertEquals(value, actual);
    }

    @Test
    public void testReturnFloatVarConfigured() {
        Float value = Float.valueOf(1.0f);

        Float actual = unmodifiable.returnFloatVarConfigured(value);

        assertEquals(value, actual);
    }

    @Test
    public void testReturnDouble() {
        Double expected = Double.valueOf(400.10d);

        Double actual = unmodifiable.returnDouble();

        assertEquals(expected, actual);
    }

    @Test
    public void testReturnDoubleVar() {
        Double value = Double.valueOf(500.20d);

        Double actual = unmodifiable.returnDoubleVar();

        assertEquals(value, actual);
    }

    @Test
    public void testReturnDoubleNull() {
        Double apple = null;

        Double actual = unmodifiable.returnDoubleNull();

        assertEquals(apple, actual);
    }

    @Test
    public void testReturnDoubleVarNull() {
        Double value = null;

        Double actual = unmodifiable.returnDoubleVarNull();

        assertEquals(value, actual);
    }

    @Test
    public void testReturnDoubleVarConfigured() {
        Double value = Double.valueOf(1.0d);

        Double actual = unmodifiable.returnDoubleVarConfigured(value);

        assertEquals(value, actual);
    }

    @Test
    public void testReturnBoolean() {
        Boolean expected = Boolean.valueOf(true);

        Boolean actual = unmodifiable.returnBoolean();

        assertEquals(expected, actual);
    }

    @Test
    public void testReturnBooleanVar() {
        Boolean value = Boolean.valueOf(false);

        Boolean actual = unmodifiable.returnBooleanVar();

        assertEquals(value, actual);
    }

    @Test
    public void testReturnBooleanNull() {
        Boolean apple = null;

        Boolean actual = unmodifiable.returnBooleanNull();

        assertEquals(apple, actual);
    }

    @Test
    public void testReturnBooleanVarNull() {
        Boolean value = null;

        Boolean actual = unmodifiable.returnBooleanVarNull();

        assertEquals(value, actual);
    }

    @Test
    public void testReturnBooleanVarConfigured() {
        Boolean value = Boolean.valueOf(true);

        Boolean actual = unmodifiable.returnBooleanVarConfigured(value);

        assertEquals(value, actual);
    }

    @Test
    public void testReturnString() {
        String expected = String.valueOf("foo");

        String actual = unmodifiable.returnString();

        assertEquals(expected, actual);
    }

    @Test
    public void testReturnStringVar() {
        String value = String.valueOf("bar");

        String actual = unmodifiable.returnStringVar();

        assertEquals(value, actual);
    }

    @Test
    public void testReturnStringNull() {
        String apple = null;

        String actual = unmodifiable.returnStringNull();

        assertEquals(apple, actual);
    }

    @Test
    public void testReturnStringVarNull() {
        String value = null;

        String actual = unmodifiable.returnStringVarNull();

        assertEquals(value, actual);
    }

    @Test
    public void testReturnStringVarConfigured() {
        String value = "foo";

        String actual = unmodifiable.returnStringVarConfigured(value);

        assertEquals(value, actual);
    }

    @Test
    public void testReturnClass() {
        Class<?> apple = String.class;

        Class<?> actual = unmodifiable.returnClass();

        assertEquals(apple, actual);
    }

    @Test
    public void testReturnClassVar() {
        Class<?> value = String.class;

        Class<?> actual = unmodifiable.returnClassVar();

        assertEquals(value, actual);
    }

    @Test
    public void testReturnClassNull() {
        Class<?> apple = null;

        Class<?> actual = unmodifiable.returnClassNull();

        assertEquals(apple, actual);
    }

    @Test
    public void testReturnClassVarNull() {
        Class<?> value = null;

        Class<?> actual = unmodifiable.returnClassVarNull();

        assertEquals(value, actual);
    }

    @Test
    public void testReturnClassVarConfigured() {
        Class<?> value = String.class;

        Class<?> actual = unmodifiable.returnClassVarConfigured(value);

        assertEquals(value, actual);
    }
}
