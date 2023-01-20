package org.codetab.uknit.itest.brace.im;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.brace.im.Model.Foo;
import org.codetab.uknit.itest.brace.im.Model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class InlineArgTest {
    @InjectMocks
    private InlineArg inlineArg;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLiteralArg() {
        Foo foo = Mockito.mock(Foo.class);
        String orange = "Foo";
        String kiwi = "Bar";
        String grape = kiwi;
        int index = 10;
        int index2 = 20;

        when((foo).get((index))).thenReturn(orange);
        when((foo).get(index2)).thenReturn(kiwi);

        String actual = inlineArg.literalArg(foo);

        assertEquals(grape, actual);
    }

    @Test
    public void testInfixArg() {
        Foo foo = Mockito.mock(Foo.class);
        int index = (10) + (1);
        String orange = "Foo";
        int index2 = 20 - 1;
        String kiwi = "Bar";
        String grape = kiwi;

        when((foo).get((index))).thenReturn(orange);
        when((foo).get(index2)).thenReturn(kiwi);

        String actual = inlineArg.infixArg(foo);

        assertEquals(grape, actual);
    }

    @Test
    public void testInfixMixArg() {
        Foo foo = Mockito.mock(Foo.class);
        int offset = 1;
        int index = (10) + (offset);
        String orange = "Foo";
        int index2 = 20 - offset;
        String kiwi = "Bar";
        String grape = kiwi;

        when((foo).get((index))).thenReturn(orange);
        when((foo).get(index2)).thenReturn(kiwi);

        String actual = inlineArg.infixMixArg(foo, offset);

        assertEquals(grape, actual);
    }

    @Test
    public void testCastArg() {
        Foo foo = Mockito.mock(Foo.class);
        String orange = "Foo";
        String kiwi = "Bar";
        String grape = kiwi;
        int index = (int) (10L);
        int index2 = (int) 20D;

        when((foo).get((index))).thenReturn(orange);
        when((foo).get(index2)).thenReturn(kiwi);

        String actual = inlineArg.castArg(foo);

        assertEquals(grape, actual);
    }

    @Test
    public void testFieldAccessArg() {
        Foo foo = Mockito.mock(Foo.class);
        Person person = Mockito.mock(Person.class);
        int index = (person).id;
        String orange = "Foo";
        int index2 = person.id;
        String kiwi = "Bar";
        String grape = kiwi;

        when((foo).get((index))).thenReturn(orange);
        when((foo).get(index2)).thenReturn(kiwi);

        String actual = inlineArg.fieldAccessArg(foo, person);

        assertEquals(grape, actual);
    }

    @Test
    public void testFieldAccessMixArg() {
        Foo foo = Mockito.mock(Foo.class);
        Person person = Mockito.mock(Person.class);
        int index = person.id;
        String orange = "Foo";
        int index2 = (person).id;
        String kiwi = "Bar";
        String grape = kiwi;

        when((foo).get((index))).thenReturn(orange);
        when((foo).get(index2)).thenReturn(kiwi);

        String actual = inlineArg.fieldAccessMixArg(foo, person);

        assertEquals(grape, actual);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testInstanceCreationArg() {
        Foo foo = Mockito.mock(Foo.class);
        int index = new Integer((10));
        String orange = "Foo";
        int index2 = new Integer((20));
        String kiwi = "Bar";
        String grape = kiwi;

        when((foo).get((index))).thenReturn(orange);
        when((foo).get(index2)).thenReturn(kiwi);

        String actual = inlineArg.instanceCreationArg(foo);

        assertEquals(grape, actual);
    }

    @Test
    public void testStaticCallArg() {
        Foo foo = Mockito.mock(Foo.class);
        Integer apple = Integer.valueOf(1);
        String mango = "Foo";
        Integer orange = Integer.valueOf(1);
        String banana = null;
        String kiwi = banana;

        when((foo).get(apple)).thenReturn(mango);
        when((foo).get(orange)).thenReturn(banana);

        String actual = inlineArg.staticCallArg(foo);

        assertEquals(kiwi, actual);
    }

    @Test
    public void testArrayAccessArg() {
        Foo foo = Mockito.mock(Foo.class);
        int[] indexes = {1, 2};
        int index = (indexes)[0];
        String orange = "Foo";
        int index2 = indexes[1];
        String kiwi = "Bar";
        String grape = kiwi;

        when((foo).get((index))).thenReturn(orange);
        when((foo).get(index2)).thenReturn(kiwi);

        String actual = inlineArg.arrayAccessArg(foo, indexes);

        assertEquals(grape, actual);
    }
}
