package org.codetab.uknit.itest.exp.value;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import org.codetab.uknit.itest.exp.value.Model.Box;
import org.codetab.uknit.itest.exp.value.Model.Foo;
import org.codetab.uknit.itest.exp.value.Model.StaticBox;
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
        String orange = "foo";
        String kiwi = "barx";
        arrayAccess.arrayItemType(foo);

        verify(foo).appendObj(apple);
        verify(foo).appendObj(grape);
        verify(foo).appendObj(orange);
        verify(foo).appendString(kiwi);
    }

    @Test
    public void testAccessWithVar() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "y";
        String grape = "x";
        // String orange = "y";
        // String banana = "x";
        arrayAccess.accessWithVar(foo);

        verify(foo, times(2)).appendString(apple);
        verify(foo, times(2)).appendString(grape);
    }

    @Test
    public void testReassignArrayItemSameValue() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = new String("bar");
        String grape = new String("foo");
        // String orange = "bar";
        arrayAccess.reassignArrayItemSameValue(foo);

        verify(foo, times(2)).appendString(apple);
        verify(foo).appendString(grape);
        // verify(foo).appendString(orange);
    }

    @Test
    public void testReassignIndex() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "foo";
        String grape = "bar";
        String orange = "baz";
        arrayAccess.reassignIndex(foo);

        verify(foo).appendString(apple);
        verify(foo).appendString(grape);
        verify(foo).appendString(orange);
    }

    @Test
    public void testReassignArrayItem() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = new String("bar");
        String grape = new String("foox");
        String orange = "barx";
        arrayAccess.reassignArrayItem(foo);

        verify(foo).appendString(apple);
        verify(foo).appendString(grape);
        verify(foo).appendString(orange);
    }

    @Test
    public void testAccessArrayCreation() {
        Foo foo = Mockito.mock(Foo.class);
        String orange = "foo";
        String kiwi = "barx";
        arrayAccess.accessArrayCreation(foo);

        verify(foo).appendString(orange);
        verify(foo).appendString(kiwi);
    }

    @Test
    public void testArrayIndexIsArrayCreation() {
        Foo foo = Mockito.mock(Foo.class);
        String orange = "foo";
        String kiwi = "barx";
        arrayAccess.arrayIndexIsArrayCreation(foo);

        verify(foo).appendString(orange);
        verify(foo).appendString(kiwi);
    }

    @Test
    public void testArrayExpIsArrayCreation() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = new String[] {"foo", "bar"}[0];
        String grape = new String[] {"foox", "barx"}[1];
        arrayAccess.arrayExpIsArrayCreation(foo);

        verify(foo).appendString(apple);
        verify(foo).appendString(grape);
    }

    @Test
    public void testAccessBoolean() {
        Foo foo = Mockito.mock(Foo.class);
        File file = Mockito.mock(File.class);
        boolean apple = true;
        boolean orange = true;
        boolean kiwi = false;
        // boolean mango = true;
        // Boolean banana = Boolean.valueOf(true);
        // boolean cherry = true;

        when(file.canExecute()).thenReturn(apple);
        arrayAccess.accessBoolean(foo, file);

        verify(foo, times(4)).appendObj(orange);
        verify(foo).appendObj(kiwi);
        // verify(foo).appendObj(banana);
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

        verify(foo).appendString(orange);
        verify(foo).appendString(kiwi);
        verify(foo).appendString(mango);
        verify(foo).appendString(banana);
        verify(foo).appendString(cherry);
        verify(foo).appendString(apricot);
    }

    @Test
    public void testArrayIndexIsCast() {
        Foo foo = Mockito.mock(Foo.class);
        String grape = "foo";
        String kiwi = new String("bar");
        arrayAccess.arrayIndexIsCast(foo);

        verify(foo).appendObj(grape);
        verify(foo).appendObj(kiwi);
    }

    @Test
    public void testAccessChar() {
        Foo foo = Mockito.mock(Foo.class);
        Character ch = Character.valueOf('A');
        char mango = 'A';
        Character banana = Character.valueOf('c');
        char cherry = 'a';
        // char apricot = 'A';
        // Character peach = Character.valueOf('c');
        // char fig = 'A';
        Character plum = Character.valueOf(('d'));
        arrayAccess.accessChar(foo, ch);

        verify(foo, times(3)).appendObj(mango);
        verify(foo, times(2)).appendObj(banana);
        verify(foo).appendObj(cherry);
        // verify(foo).appendObj(apricot);
        // verify(foo).appendObj(fig);
        verify(foo).appendObj(plum);
    }

    @Test
    public void testArrayIndexIsChar() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "bar";
        String grape = "baz";
        String kiwi = "foo";
        arrayAccess.arrayIndexIsChar(foo);

        verify(foo).appendString(apple);
        verify(foo).appendString(grape);
        verify(foo).appendString(kiwi);
    }

    @Test
    public void testAccessClassInstanceCreation() {
        Foo foo = Mockito.mock(Foo.class);
        File file = new File("foo");
        String apple = new String("bar");
        ArrayList<LocalDate> arrayList = new ArrayList<LocalDate>();
        String file2 = new String("barx");
        File grape = new File("foox");
        HashMap<String, LocalTime> arrayList2 =
                new HashMap<String, LocalTime>();
        arrayAccess.accessClassInstanceCreation(foo);

        verify(foo).appendObj(file);
        verify(foo).appendObj(apple);
        verify(foo).appendObj(arrayList);
        verify(foo).appendObj(file2);
        verify(foo).appendObj(grape);
        verify(foo).appendObj(arrayList2);
    }

    @Test
    public void testArrayIndexIsClassInstanceCreation() {
        Foo foo = Mockito.mock(Foo.class);
        File file = new File("foo");
        String apple = new String("bar");
        ArrayList<LocalDate> arrayList = new ArrayList<LocalDate>();
        String file2 = new String("barx");
        File grape = new File("foox");
        HashMap<String, LocalTime> arrayList2 =
                new HashMap<String, LocalTime>();
        arrayAccess.arrayIndexIsClassInstanceCreation(foo);

        verify(foo).appendObj(file);
        verify(foo).appendObj(apple);
        verify(foo).appendObj(arrayList);
        verify(foo).appendObj(file2);
        verify(foo).appendObj(grape);
        verify(foo).appendObj(arrayList2);
    }

    @Test
    public void testArrayNameIsClassInstanceCreation() {
        Foo foo = Mockito.mock(Foo.class);
        File file = (File) new Object[] {new File("foo"), new String("bar"),
                new ArrayList<LocalDate>()}[0];
        String apple = (String) new Object[] {new File("foo"),
                new String("bar"), new ArrayList<LocalDate>()}[1];
        @SuppressWarnings("unchecked")
        ArrayList<LocalDate> arrayList =
                (ArrayList<LocalDate>) new Object[] {new File("foo"),
                        new String("bar"), new ArrayList<LocalDate>()}[2];
        arrayAccess.arrayNameIsClassInstanceCreation(foo);

        verify(foo).appendObj(file);
        verify(foo).appendObj(apple);
        verify(foo).appendObj(arrayList);
    }

    @Test
    public void testAccessConditional() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "bar";
        String grape = "foo";
        String orange = "foox";
        String kiwi = "barx";
        arrayAccess.accessConditional(foo);

        verify(foo).appendString(apple);
        verify(foo).appendString(grape);
        verify(foo).appendString(orange);
        verify(foo).appendString(kiwi);
    }

    @Test
    public void testIndexIsconditional() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "foo";
        // String kiwi = "foo";
        String apricot = "bar";
        arrayAccess.indexIsconditional(foo);

        verify(foo, times(2)).appendString(apple);
        verify(foo).appendString(apricot);
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

        verify(foo).appendObj(flg ? cities : apple);
        verify(foo).appendString(orange);
        verify(foo).appendString(kiwi);
    }

    @Test
    public void testArrayIsField() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "ffoo";
        String grape = "fbar";
        String orange = "fbaz";
        // String kiwi = "ffoo";
        arrayAccess.arrayIsField(foo);

        verify(foo, times(2)).appendString(apple);
        verify(foo).appendString(grape);
        verify(foo).appendString(orange);
    }

    @Test
    public void testArrayIsFieldOfFields() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "ffoo";
        String grape = "fbar";
        String orange = "fbaz";
        // String kiwi = "fbar";
        arrayAccess.arrayIsFieldOfFields(foo);

        verify(foo).appendString(apple);
        verify(foo, times(2)).appendString(grape);
        verify(foo).appendString(orange);
    }

    @Test
    public void testArrayItemIsField() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "ffoo";
        String grape = "fbar";
        String orange = "fbaz";
        // String kiwi = "fbar";
        arrayAccess.arrayItemIsField(foo);

        verify(foo).appendString(apple);
        verify(foo, times(2)).appendString(grape);
        verify(foo).appendString(orange);
    }

    @Test
    public void testAccessIndexIsField() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "foo";
        String grape = "bar";
        String orange = "baz";
        // String kiwi = "bar";
        arrayAccess.accessIndexIsField(foo);

        verify(foo).appendString(apple);
        verify(foo, times(2)).appendString(grape);
        verify(foo).appendString(orange);
    }

    @Test
    public void testArrayItemAndIndexHidesField() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "foo";
        String grape = "bar";
        String orange = "baz";
        // String kiwi = "fbar";
        arrayAccess.arrayItemAndIndexHidesField(foo);

        verify(foo).appendString(apple);
        verify(foo, times(2)).appendString(grape);
        verify(foo).appendString(orange);
    }

    @Test
    public void testArrayIsFieldAccess() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.spy(Box.class);
        String apple = (box).items[1];
        arrayAccess.arrayIsFieldAccess(foo, box);

        verify(foo).appendString(apple);
    }

    @Test
    public void testLocalArrayIsFieldAccess() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.spy(Box.class);
        String apple = (box).items[0];
        arrayAccess.localArrayIsFieldAccess(foo, box);

        verify(foo).appendString(apple);
    }

    @Test
    public void testArrayItemIsFieldAccess() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.spy(Box.class);
        String orange = "foo";
        arrayAccess.arrayItemIsFieldAccess(foo, box);

        verify(foo).appendString(orange);
    }

    @Test
    public void testIndexIsFieldAccess() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.spy(Box.class);
        String apple = (box).items[(box).id];
        arrayAccess.indexIsFieldAccess(foo, box);

        verify(foo).appendString(apple);
    }

    @Test
    public void testArrayItemIsInfix() {
        Foo foo = Mockito.mock(Foo.class);
        int i = 0;
        int apple = i + 1;
        int grape = i - 1;
        arrayAccess.arrayItemIsInfix(foo);

        verify(foo).appendObj(apple);
        verify(foo).appendObj(grape);
    }

    @Test
    public void testArrayIndexIsInfix() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "bar";
        // String grape = "bar";
        // String orange = "foo";
        // String kiwi = "bar";
        arrayAccess.arrayIndexIsInfix(foo);

        verify(foo, times(4)).appendString(apple);
        // verify(foo).appendString(orange);
    }

    @Test
    public void testArrayItemIsInstanceOf() {
        Foo foo = Mockito.mock(Foo.class);
        Object bar = new File("foo");
        boolean apple = foo instanceof Foo;
        boolean grape = bar instanceof String;
        arrayAccess.arrayItemIsInstanceOf(foo);

        verify(foo).appendObj(apple);
        verify(foo).appendObj(grape);
    }

    @Test
    public void testArrayItemIsLambda() {
        Foo foo = Mockito.mock(Foo.class);
        // Function function = STEPIN;
        // Function function2 = STEPIN;
        arrayAccess.arrayItemIsLambda(foo);

        // verify(foo).appendObj(function);
        // verify(foo).appendObj(function2);
    }

    @Test
    public void testArrayIsInvoke() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.mock(Box.class);
        String[] apple = {"Foo", "Bar", "Baz"};
        String grape = "Foo";
        String[] orange = {"Foo", "Bar", "Baz"};
        String kiwi = "Bar";
        String[] mango = {"Foo", "Bar", "Baz"};
        String banana = "Baz";

        when(box.getItems()).thenReturn(apple).thenReturn(orange)
                .thenReturn(mango);
        arrayAccess.arrayIsInvoke(foo, box);

        verify(foo).appendString(grape);
        verify(foo).appendString(kiwi);
        verify(foo).appendString(banana);
    }

    @Test
    public void testArrayVarIsInvoke() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.mock(Box.class);
        String[] items = {"Foo", "Bar", "Baz"};
        String apple = "Foo";
        String grape = "Bar";
        String orange = "Baz";

        when(box.getItems()).thenReturn(items);
        arrayAccess.arrayVarIsInvoke(foo, box);

        verify(foo).appendString(apple);
        verify(foo).appendString(grape);
        verify(foo).appendString(orange);
    }

    @Test
    public void testArrayItemIsInvoke() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "Foo";
        String grape = "Bar";
        String orange = "Baz";
        String kiwi = apple;
        String mango = grape;
        String banana = orange;

        when(foo.lang()).thenReturn(apple);
        when(foo.cntry()).thenReturn(grape);
        when(foo.name()).thenReturn(orange);
        arrayAccess.arrayItemIsInvoke(foo);

        verify(foo).appendString(kiwi);
        verify(foo).appendString(mango);
        verify(foo).appendString(banana);
    }

    @Test
    public void testArrayAccessInvoke() {
        Foo foo = Mockito.mock(Foo.class);
        File f1 = Mockito.mock(File.class);
        File f2 = Mockito.mock(File.class);
        File file = f1;
        String apple = "Foo";
        String grape = "Bar";
        String orange = "Baz";
        File file4 = f2;
        String kiwi = "Qux";

        when(file.getAbsolutePath()).thenReturn(apple).thenReturn(grape)
                .thenReturn(orange);
        when(file4.getAbsolutePath()).thenReturn(kiwi);
        arrayAccess.arrayAccessInvoke(foo, f1, f2);

        verify(foo).appendString(apple);
        verify(foo).appendString(grape);
        verify(foo).appendString(orange);
        verify(foo).appendString(kiwi);
    }

    @Test
    public void testArrayIndexIsInvoke() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.mock(Box.class);
        int apple = 1;
        String grape = "bar";
        int orange = 1;
        // String kiwi = "bar";
        int mango = 1;
        // String banana = "bar";

        when(box.getId()).thenReturn(apple).thenReturn(orange)
                .thenReturn(mango);
        arrayAccess.arrayIndexIsInvoke(foo, box);

        verify(foo, times(3)).appendString(grape);
    }

    @Test
    public void testArrayIsStaticInvoke() {
        Foo foo = Mockito.mock(Foo.class);
        String grape = StaticBox.getItems()[0];
        String kiwi = StaticBox.getItems()[1];
        String banana = StaticBox.getItems()[2];
        // String apricot = (StaticBox.getItems())[(2)];
        arrayAccess.arrayIsStaticInvoke(foo);

        verify(foo).appendString(grape);
        verify(foo).appendString(kiwi);
        verify(foo, times(2)).appendString(banana);
    }

    @Test
    public void testArrayItemIsStaticInvoke() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.mock(Box.class);
        String kiwi = String.valueOf("foo");
        String mango = String.valueOf("bar");
        // String banana = String.valueOf("bar");
        // String cherry = String.valueOf("bar");
        arrayAccess.arrayItemIsStaticInvoke(foo, box);

        verify(foo).appendString(kiwi);
        verify(foo, times(3)).appendString(mango);
    }

    @Test
    public void testArrayIndexIsInvokeItemReassigned() {
        Foo foo = Mockito.mock(Foo.class);
        int apple = 1;
        String grape = "bar";
        int orange = 1;
        String kiwi = "barx";

        when(foo.index()).thenReturn(apple).thenReturn(orange);
        arrayAccess.arrayIndexIsInvokeItemReassigned(foo);

        verify(foo).appendString(grape);
        verify(foo).appendString(kiwi);
    }

    @Test
    public void testArrayIndexIsStaticInvoke() throws Exception {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.mock(Box.class);
        String grape = "foo";
        String kiwi = "bar";
        String banana = "baz";
        // String apricot = "Qux";
        arrayAccess.arrayIndexIsStaticInvoke(foo, box);

        verify(foo).appendString(grape);
        verify(foo).appendString(kiwi);
        verify(foo, times(2)).appendString(banana);
        // verify(foo).appendString(apricot);
    }

    @Test
    public void testArrayAccessInStaticInvoke() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "foox";
        // String orange = "foox";
        // String mango = "foox";
        String cherry = "baz";
        arrayAccess.arrayAccessInStaticInvoke(foo);

        verify(foo, times(3)).appendString(Foo.valueOf(apple));
        verify(foo).appendString(Foo.valueOf(cherry));
    }

    @Test
    public void testArrayItemIsNullLiteral() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = null;
        // String grape = null;
        String orange = "baz";
        arrayAccess.arrayItemIsNullLiteral(foo);

        verify(foo, times(2)).appendString(apple);
        verify(foo).appendString(orange);
    }

    @Test
    public void testArrayItemIsNumberLiteral() {
        Foo foo = Mockito.mock(Foo.class);
        int apple = 1;
        int grape = 2;
        int orange = 3;
        short kiwi = 11;
        short mango = 12;
        short banana = 13;
        long cherry = 21;
        long apricot = 22L;
        long peach = 23L;
        float fig = 31f;
        float plum = 32f;
        float lychee = 33f;
        double scrappy = 41.11d;
        double barracuda = 42.22d;
        double bionic = 43.33;
        arrayAccess.arrayItemIsNumberLiteral(foo);

        verify(foo).appendObj(apple);
        verify(foo).appendObj(grape);
        verify(foo).appendObj(orange);
        verify(foo).appendObj(kiwi);
        verify(foo).appendObj(mango);
        verify(foo).appendObj(banana);
        verify(foo).appendObj(cherry);
        verify(foo).appendObj(apricot);
        verify(foo).appendObj(peach);
        verify(foo).appendObj(fig);
        verify(foo).appendObj(plum);
        verify(foo).appendObj(lychee);
        verify(foo).appendObj(scrappy);
        verify(foo).appendObj(barracuda);
        verify(foo).appendObj(bionic);
    }

    @Test
    public void testArrayIsQName() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.spy(Box.class);
        String apple = box.items[1];
        // String grape = (box.items)[1];
        arrayAccess.arrayIsQName(foo, box);

        verify(foo, times(2)).appendString(apple);
    }

    @Test
    public void testLocalArrayIsQName() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.spy(Box.class);
        // String[] items = box.items;
        String apple = box.items[0];
        // String grape = box.items[0];
        arrayAccess.localArrayIsQName(foo, box);

        verify(foo, times(2)).appendString(apple);
    }

    @Test
    public void testArrayItemIsQName() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.spy(Box.class);
        String orange = "foo";
        String kiwi = "bar";
        arrayAccess.arrayItemIsQName(foo, box);

        verify(foo).appendString(orange);
        verify(foo).appendString(kiwi);
    }

    @Test
    public void testIndexIsQName() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.spy(Box.class);
        String apple = box.items[box.id];
        // String grape = (box.items)[(box.id)];
        arrayAccess.indexIsQName(foo, box);

        verify(foo, times(2)).appendString(apple);
    }

    @Test
    public void testArrayExpHasThis() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = arrayAccess.tDevices[0];
        String grape = arrayAccess.tDevices[1];
        String orange = arrayAccess.tDevices[2];
        arrayAccess.arrayExpHasThis(foo);

        verify(foo).appendString(apple);
        verify(foo).appendString(grape);
        verify(foo).appendString(orange);
    }

    @Test
    public void testArrayItemsHasThis() {
        Foo foo = Mockito.mock(Foo.class);
        String kiwi = "foo";
        String mango = "bar";
        String banana = "baz";
        arrayAccess.arrayItemsHasThis(foo);

        verify(foo).appendString(kiwi);
        verify(foo).appendString(mango);
        verify(foo).appendString(banana);
    }

    @Test
    public void testArrayIndexHasThis() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = arrayAccess.tDevices[arrayAccess.tIndexes[0]];
        String grape = arrayAccess.tDevices[arrayAccess.tIndexes[1]];
        String orange = arrayAccess.tDevices[arrayAccess.tIndexes[2]];
        arrayAccess.arrayIndexHasThis(foo);

        verify(foo).appendString(apple);
        verify(foo).appendString(grape);
        verify(foo).appendString(orange);
    }

    @Test
    public void testArrayExpHasTypeLiteral() throws Exception {
        Foo foo = Mockito.mock(Foo.class);
        Method method = String.class.getDeclaredMethods()[0];
        Method method2 = Integer.class.getDeclaredMethods()[0];
        Method method3 = File.class.getDeclaredMethods()[0];
        arrayAccess.arrayExpHasTypeLiteral(foo);

        verify(foo).appendObj(method);
        verify(foo).appendObj(method2);
        verify(foo).appendObj(method3);
    }

    @Test
    public void testArrayItemIsTypeLiteral() {
        Foo foo = Mockito.mock(Foo.class);
        Class<String> clz = String.class;
        Class<Integer> clz2 = Integer.class;
        Class<File> clz3 = File.class;
        // Class<File> clz4 = File.class;
        arrayAccess.arrayItemIsTypeLiteral(foo);

        verify(foo).appendObj(clz);
        verify(foo).appendObj(clz2);
        verify(foo, times(2)).appendObj(clz3);
    }

    @Test
    public void testArrayIndexHasTypeLiteral() {
        Foo foo = Mockito.mock(Foo.class);
        Class<Integer> clz = Integer.class;
        // Class<Integer> clz2 = Integer.class;
        // Class<Integer> clz3 = Integer.class;
        arrayAccess.arrayIndexHasTypeLiteral(foo);

        verify(foo, times(3)).appendObj(clz);
    }
}
