package org.codetab.uknit.itest.exp.value;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import org.codetab.uknit.itest.exp.value.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class ArrayAccessTest {
    @InjectMocks
    private ArrayAccess arrayAccess;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testArrayItemType() {
        Foo foo = Mockito.mock(Foo.class);
        int apple = 10;
        int grape = 20;
        Object orange = "foo";
        String kiwi = "barx";
        arrayAccess.arrayItemType(foo);

        verify(foo).append(apple);
        verify(foo).append(grape);
        verify(foo).append(orange);
        verify(foo).append(kiwi);
    }

    @Test
    public void testAccessWithVar() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "y";
        String grape = "x";
        String orange = "y";
        String banana = "x";
        arrayAccess.accessWithVar(foo);

        verify(foo, times(2)).append(apple);
        verify(foo, times(2)).append(grape);
    }

    @Test
    public void testReassignArrayItemSameValue() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = new String("bar");
        String grape = new String("foo");
        String orange = "bar";
        arrayAccess.reassignArrayItemSameValue(foo);

        verify(foo, times(2)).append(apple);
        verify(foo).append(grape);
        // verify(foo).append(orange);
    }

    @Test
    public void testReassignArrayItem() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = new String("bar");
        String grape = new String("foox");
        String orange = "barx";
        arrayAccess.reassignArrayItem(foo);

        verify(foo).append(apple);
        verify(foo).append(grape);
        verify(foo).append(orange);
    }

    @Test
    public void testAccessArrayCreation() {
        Foo foo = Mockito.mock(Foo.class);
        String orange = "foo";
        String kiwi = "barx";
        arrayAccess.accessArrayCreation(foo);

        verify(foo).append(orange);
        verify(foo).append(kiwi);
    }

    @Test
    public void testArrayIndexIsArrayCreation() {
        Foo foo = Mockito.mock(Foo.class);
        String orange = "foo";
        String kiwi = "barx";
        arrayAccess.arrayIndexIsArrayCreation(foo);

        verify(foo).append(orange);
        verify(foo).append(kiwi);
    }

    @Test
    public void testArrayExpIsArrayCreation() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = new String[] {"foo", "bar"}[0];
        String grape = new String[] {"foox", "barx"}[1];
        arrayAccess.arrayExpIsArrayCreation(foo);

        verify(foo).append(apple);
        verify(foo).append(grape);
    }

    @Test
    public void testAccessBoolean() {
        Foo foo = Mockito.mock(Foo.class);
        File file = Mockito.mock(File.class);
        boolean apple = true;
        boolean orange = true;
        boolean kiwi = false;
        boolean mango = apple;
        Boolean banana = Boolean.valueOf(true);
        boolean cherry = true;

        when(file.canExecute()).thenReturn(apple);
        arrayAccess.accessBoolean(foo, file);

        // verify(foo, times(2)).append(orange);
        verify(foo, times(4)).append(orange);
        verify(foo).append(kiwi);
        // verify(foo).append(banana);
        // verify((foo)).append(cherry);
    }

    @Test
    public void testAccessCast() {
        Foo foo = Mockito.mock(Foo.class);
        String orange = "foo";
        String kiwi = "bar";
        String mango = "foox";
        String banana = "barx";
        String cherry = "baz";
        String apricot = "qux";
        arrayAccess.accessCast(foo);

        verify(foo).append(orange);
        verify(foo).append(kiwi);
        verify(foo).append(mango);
        verify(foo).append(banana);
        verify(foo).append(cherry);
        verify(foo).append(apricot);
    }

    @Test
    public void testArrayIndexIsCast() {
        Foo foo = Mockito.mock(Foo.class);
        Object grape = "foo";
        Object kiwi = new String("bar");
        arrayAccess.arrayIndexIsCast(foo);

        verify(foo).append(grape);
        verify(foo).append(kiwi);
    }

    @Test
    public void testAccessChar() {
        Foo foo = Mockito.mock(Foo.class);
        Character ch = Character.valueOf('A');
        char mango = 'A';
        Character banana = Character.valueOf('c');
        char cherry = 'a';
        char apricot = 'A';
        Character peach = Character.valueOf('c');
        char fig = 'A';
        Character plum = Character.valueOf(('d'));
        arrayAccess.accessChar(foo, ch);

        // verify(foo).append(mango);
        verify(foo, times(3)).append(mango);
        verify(foo, times(2)).append(banana);
        verify(foo).append(cherry);
        // verify(foo).append(apricot);
        // verify(foo).append(fig);
        verify(foo).append(plum);
    }

    @Test
    public void testArrayIndexIsChar() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "bar";
        String grape = "baz";
        String kiwi = "foo";
        arrayAccess.arrayIndexIsChar(foo);

        verify(foo).append(apple);
        verify(foo).append(grape);
        verify(foo).append(kiwi);
    }

    @Test
    public void testAccessClassInstanceCreation() {
        Foo foo = Mockito.mock(Foo.class);
        File file = new File("foo");
        Object apple = new String("bar");
        ArrayList<LocalDate> arrayList = new ArrayList<LocalDate>();
        Object file2 = new String("barx");
        File grape = new File("foox");
        HashMap<String, LocalTime> arrayList2 =
                new HashMap<String, LocalTime>();
        arrayAccess.accessClassInstanceCreation(foo);

        verify(foo).append(file);
        verify(foo).append(apple);
        verify(foo).append(arrayList);
        verify(foo).append(file2);
        verify(foo).append(grape);
        verify(foo).append(arrayList2);
    }

    @Test
    public void testArrayIndexIsClassInstanceCreation() {
        Foo foo = Mockito.mock(Foo.class);
        File file = new File("foo");
        Object apple = new String("bar");
        ArrayList<LocalDate> arrayList = new ArrayList<LocalDate>();
        Object file2 = new String("barx");
        File grape = new File("foox");
        HashMap<String, LocalTime> arrayList2 =
                new HashMap<String, LocalTime>();
        arrayAccess.arrayIndexIsClassInstanceCreation(foo);

        verify(foo).append(file);
        verify(foo).append(apple);
        verify(foo).append(arrayList);
        verify(foo).append(file2);
        verify(foo).append(grape);
        verify(foo).append(arrayList2);
    }

    @Test
    public void testArrayNameIsClassInstanceCreation() {
        Foo foo = Mockito.mock(Foo.class);
        File file = (File) new Object[] {new File("foo"), new String("bar"),
                new ArrayList<LocalDate>()}[0];
        Object apple = new Object[] {new File("foo"), new String("bar"),
                new ArrayList<LocalDate>()}[1];
        @SuppressWarnings("unchecked")
        ArrayList<LocalDate> arrayList =
                (ArrayList<LocalDate>) new Object[] {new File("foo"),
                        new String("bar"), new ArrayList<LocalDate>()}[2];
        arrayAccess.arrayNameIsClassInstanceCreation(foo);

        verify(foo).append(file);
        verify(foo).append(apple);
        verify(foo).append(arrayList);
    }

    @Test
    public void testAccessConditional() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "bar";
        String grape = "foo";
        String orange = "foox";
        String kiwi = "barx";
        arrayAccess.accessConditional(foo);

        verify(foo).append(apple);
        verify(foo).append(grape);
        verify(foo).append(orange);
        verify(foo).append(kiwi);
    }

    @Test
    public void testIndexIsconditional() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "foo";
        String kiwi = "foo";
        String apricot = "bar";
        arrayAccess.indexIsconditional(foo);

        verify(foo, times(2)).append(apple);
        verify(foo).append(apricot);
    }

    @Test
    public void testArrayExpressionIsConditional() {
        Foo foo = Mockito.mock(Foo.class);
        boolean flg = false;
        String[] cities = {"foo", "bar"};
        String apple = "foox";
        String orange = "barx";
        String kiwi = "foo";
        arrayAccess.arrayExpressionIsConditional(foo);

        verify(foo).append(flg ? cities : apple);
        verify(foo).append(orange);
        verify(foo).append(kiwi);
    }
}
