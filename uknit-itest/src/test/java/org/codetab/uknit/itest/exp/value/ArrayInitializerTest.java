package org.codetab.uknit.itest.exp.value;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.function.Function;

import org.codetab.uknit.itest.exp.value.Model.Box;
import org.codetab.uknit.itest.exp.value.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class ArrayInitializerTest {
    @InjectMocks
    private ArrayInitializer arrayInitializer;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExpIsArrayAccess() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "foo";
        String grape = "bar";
        String orange = "baz";
        String kiwi = "zoo";
        // String mango = "foo";
        // String banana = "bar";
        // String cherry = "foo";
        // String apricot = "bar";
        arrayInitializer.expIsArrayAccess(foo);

        verify(foo, times(3)).appendObj(new String[] {apple, grape});
        verify(foo).appendObj(new String[] {orange, kiwi});
    }

    @Test
    public void testExpIsArrayCreation() {
        Foo foo = Mockito.mock(Foo.class);
        arrayInitializer.expIsArrayCreation(foo);

        verify(foo, times(3)).appendObj(eq(
                new String[][] {new String[] {"foo"}, new String[] {"bar"}}));
        verify(foo).appendObj(eq(
                new String[][] {new String[] {"baz"}, new String[] {"zoo"}}));
    }

    @Test
    public void testExpIsBoolean() {
        Foo foo = Mockito.mock(Foo.class);
        arrayInitializer.expIsBoolean(foo);

        verify(foo, times(3)).appendObj(new Boolean[] {true, false});
        verify(foo).appendObj(new Boolean[] {false, true});
    }

    @Test
    public void testExpIsCast() {
        Foo foo = Mockito.mock(Foo.class);
        String a = "foo";
        String b = "bar";
        arrayInitializer.expIsCast(foo);

        verify(foo, times(3)).appendObj(new String[] {a, b});
        verify(foo).appendObj(new String[] {b, a});
    }

    @Test
    public void testExpIsCharacterLiteral() {
        Foo foo = Mockito.mock(Foo.class);
        arrayInitializer.expIsCharacterLiteral(foo);

        verify(foo, times(3)).appendObj(new char[] {'a', 'b'});
        verify(foo).appendObj(new char[] {'b', 'a'});
    }

    @Test
    public void testExpIsClassInstanceCreation() {
        Foo foo = Mockito.mock(Foo.class);
        arrayInitializer.expIsClassInstanceCreation(foo);

        verify(foo, times(3))
                .appendObj(new String[] {new String("foo"), new String("bar")});
        verify(foo)
                .appendObj(new String[] {new String("bar"), new String("foo")});
    }

    @Test
    public void testExpIsConditional() {
        Foo foo = Mockito.mock(Foo.class);
        int a = 1;
        arrayInitializer.expIsConditional(foo);

        verify(foo, times(3)).appendObj(
                new String[] {a > 1 ? new String("foo") : new String("bar")});
        verify(foo).appendObj(
                new String[] {a > 0 ? new String("baz") : new String("zoo")});
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testExpIsMethodRef() {
        Foo foo = Mockito.mock(Foo.class);
        Integer apple = Integer.valueOf(1);
        Integer grape = Integer.valueOf(1);
        Integer orange = Integer.valueOf(1);
        Integer kiwi = Integer.valueOf(1);

        when(foo.valueOf(eq("1"), any(Function.class))).thenReturn(apple)
                .thenReturn(orange).thenReturn(kiwi);
        when(foo.valueOf(eq("2"), any(Function.class))).thenReturn(grape);
        arrayInitializer.expIsMethodRef(foo);

        ArgumentCaptor<Function<String, Integer>> captorA =
                ArgumentCaptor.forClass(Function.class);
        ArgumentCaptor<Function<String, Integer>> captorB =
                ArgumentCaptor.forClass(Function.class);
        // ArgumentCaptor<Function<String, Integer>> captorC =
        // ArgumentCaptor.forClass(Function.class);
        // ArgumentCaptor<Function<String, Integer>> captorD =
        // ArgumentCaptor.forClass(Function.class);

        verify(foo, times(3)).valueOf(eq("1"), captorA.capture());
        verify(foo, times(4)).appendObj(new int[] {apple});
        verify(foo).valueOf(eq("2"), captorB.capture());
        // verify(foo).appendObj(new int[]{grape});
        // verify(foo, times(2)).valueOf("1", Integer::valueOf);
        // verify(foo).appendObj(new int[]{orange});
        // verify(foo).appendObj(new int[]{kiwi});
    }

    @Test
    public void testExpIsFieldAccess() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.mock(Box.class);
        box.id = 1;
        box.id2 = 11;
        arrayInitializer.expIsFieldAccess(foo, box);

        verify(foo, times(3)).appendObj(new int[] {(box).id, (box).id2});
        verify(foo).appendObj(new int[] {(box).id2, (box).id});
    }

    @Test
    public void testExpIsInfix() {
        Foo foo = Mockito.mock(Foo.class);
        arrayInitializer.expIsInfix(foo);

        verify(foo, times(3)).appendObj(new int[] {1 + 1, 1 + 12});
        verify(foo).appendObj(new int[] {1 + 12, 1 + 1});
    }

    @Test
    public void testExpIsInstanceof() {
        Foo foo = Mockito.mock(Foo.class);
        String a = "foo";
        Object b = "bar";
        arrayInitializer.expIsInstanceof(foo);

        verify(foo, times(3)).appendObj(
                new boolean[] {a instanceof String, b instanceof LocalDate});
        verify(foo).appendObj(
                new boolean[] {b instanceof LocalDate, a instanceof String});
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testExpIsLambda() {
        Foo foo = Mockito.mock(Foo.class);
        Integer apple = Integer.valueOf(1);
        Integer grape = Integer.valueOf(1);
        Integer orange = Integer.valueOf(1);
        Integer kiwi = Integer.valueOf(1);

        when(foo.valueOf(eq("1"), any(Function.class))).thenReturn(apple)
                .thenReturn(orange).thenReturn(kiwi);
        when(foo.valueOf(eq("2"), any(Function.class))).thenReturn(grape);
        arrayInitializer.expIsLambda(foo);

        ArgumentCaptor<Function<String, Integer>> captorA =
                ArgumentCaptor.forClass(Function.class);
        ArgumentCaptor<Function<String, Integer>> captorB =
                ArgumentCaptor.forClass(Function.class);
        // ArgumentCaptor<Function<String, Integer>> captorC =
        // ArgumentCaptor.forClass(Function.class);
        // ArgumentCaptor<Function<String, Integer>> captorD =
        // ArgumentCaptor.forClass(Function.class);

        verify(foo, times(3)).valueOf(eq("1"), captorA.capture());
        verify(foo, times(4)).appendObj(new int[] {apple});
        verify(foo).valueOf(eq("2"), captorB.capture());
        // verify(foo).appendObj(new int[]{grape});
        // verify(foo, times(2)).valueOf("1", a -> Integer.valueOf(a));
        // verify(foo).appendObj(new int[]{orange});
        // verify(foo).appendObj(new int[]{kiwi});
    }

    @Test
    public void testExpIsMi() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.mock(Box.class);
        int apple = 1;
        Number number = Mockito.mock(Number.class);
        Number number2 = Mockito.mock(Number.class);
        int grape = 1;
        int orange = 1;
        Number number3 = Mockito.mock(Number.class);
        int kiwi = 1;
        Number number4 = Mockito.mock(Number.class);

        when(box.getId()).thenReturn(apple).thenReturn(grape).thenReturn(orange)
                .thenReturn(kiwi);
        when(box.getFid()).thenReturn(number).thenReturn(number2)
                .thenReturn(number3).thenReturn(number4);
        arrayInitializer.expIsMi(foo, box);

        verify(foo).appendObj(new Number[] {apple, number});
        verify(foo).appendObj(new Number[] {number2, grape});
        verify(foo).appendObj(new Number[] {orange, number3});
        verify(foo).appendObj(new Number[] {kiwi, number4});
    }

    @Test
    public void testExpIsNull() {
        Foo foo = Mockito.mock(Foo.class);
        arrayInitializer.expIsNull(foo);

        verify(foo, times(3)).appendObj(new String[] {null, null});
        verify(foo).appendObj(new String[] {null, null, null});
    }

    @Test
    public void testExpIsNumberLiteral() {
        Foo foo = Mockito.mock(Foo.class);
        arrayInitializer.expIsNumberLiteral(foo);

        verify(foo, times(3)).appendObj(new Number[] {1, 11});
        verify(foo).appendObj(new Number[] {11, 1});
    }

    @Test
    public void testExpIsPostfix() {
        Foo foo = Mockito.mock(Foo.class);
        int a = 1;
        int b = 11;
        arrayInitializer.expIsPostfix(foo);

        // verify(foo, times(3)).appendObj(new Number[]{a++, b++});
        verify(foo).appendObj(new Number[] {a++, b++});
        verify(foo).appendObj(new Number[] {b++, a++});
        verify(foo).appendObj(new Number[] {a++, b++});
        verify(foo).appendObj(new Number[] {a++, b++});
    }

    @Test
    public void testExpIsPrefix() {
        Foo foo = Mockito.mock(Foo.class);
        int a = 1;
        int b = 11;
        arrayInitializer.expIsPrefix(foo);

        // verify(foo, times(3)).appendObj(new Number[]{++a, ++b});
        verify(foo).appendObj(new Number[] {++a, ++b});
        verify(foo).appendObj(new Number[] {++b, ++a});
        verify(foo).appendObj(new Number[] {++a, ++b});
        verify(foo).appendObj(new Number[] {++a, ++b});
    }

    @Test
    public void testExpIsQName() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.mock(Box.class);
        box.id = 1;
        box.id2 = 11;
        arrayInitializer.expIsQName(foo, box);

        verify(foo, times(3)).appendObj(new int[] {box.id, box.id2});
        verify(foo).appendObj(new int[] {box.id2, box.id});
    }

    @Test
    public void testExpIsSimpleName() {
        Foo foo = Mockito.mock(Foo.class);
        int a = 1;
        int b = 11;
        arrayInitializer.expIsSimpleName(foo);

        verify(foo, times(3)).appendObj(new Number[] {a, b});
        verify(foo).appendObj(new Number[] {b, a});
    }

    @Test
    public void testExpIsStringLiteral() {
        Foo foo = Mockito.mock(Foo.class);
        arrayInitializer.expIsStringLiteral(foo);

        verify(foo, times(3)).appendObj(new String[] {"foo", "bar"});
        verify(foo).appendObj(new String[] {"baz", "zoo"});
    }

    @Test
    public void testExpIsThisExp() {
        Foo foo = Mockito.mock(Foo.class);
        // int fa = 1;
        // int fb = 11;
        arrayInitializer.expIsThisExp(foo);

        verify(foo, times(3)).appendObj(
                new Number[] {arrayInitializer.fa, arrayInitializer.fb});
        verify(foo).appendObj(
                new Number[] {arrayInitializer.fb, arrayInitializer.fa});
    }

    @Test
    public void testExpIsTypeLiteral() {
        Foo foo = Mockito.mock(Foo.class);
        arrayInitializer.expIsTypeLiteral(foo);

        verify(foo, times(3)).appendObj(
                new Class[] {String.class, Integer.class, void.class});
        verify(foo).appendObj(
                new Class[] {Integer.class, String.class, void.class});
    }
}
