package org.codetab.uknit.itest.exp.value;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.function.Function;

import org.codetab.uknit.itest.exp.value.Model.Box;
import org.codetab.uknit.itest.exp.value.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class ArrayCreationTest {
    @InjectMocks
    private ArrayCreation arrayCreation;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExpIsArrayAccess() {
        Foo foo = Mockito.mock(Foo.class);
        int apple = 1;
        int[] ints = new int[apple];
        int grape = 2;
        int[] codes = new int[grape];
        arrayCreation.expIsArrayAccess(foo);

        verify(foo, times(2)).appendObj(ints);
        verify(foo).appendObj(codes);
    }

    @Test
    public void testExpIsArrayAccess2() {
        Foo foo = Mockito.mock(Foo.class);
        int apple = 1;
        int grape = 2;
        // int orange = 1;
        arrayCreation.expIsArrayAccess2(foo);

        verify(foo, times(2)).appendObj(new int[apple]);
        verify(foo).appendObj(new int[grape]);
    }

    @Test
    public void testExpIsArrayCreation() {
        Foo foo = Mockito.mock(Foo.class);
        int apple = new int[] {1}[0];
        int grape = new int[] {2}[0];
        // int orange = new int[]{(1)}[(0)];
        // int kiwi = new int[]{((1))}[((0))];
        arrayCreation.expIsArrayCreation(foo);

        verify(foo, times(3)).appendObj(new int[apple]);
        verify(foo).appendObj(new int[grape]);
    }

    @Test
    public void testExpIsCast() {
        Foo foo = Mockito.mock(Foo.class);
        int apple = 1;
        int grape = 2;
        // int orange = 1;
        arrayCreation.expIsCast(foo);

        verify(foo, times(2)).appendObj(new int[apple]);
        verify(foo).appendObj(new int[grape]);
    }

    @Test
    public void testExpIsCharLiteral() {
        Foo foo = Mockito.mock(Foo.class);
        arrayCreation.expIsCharLiteral(foo);

        verify(foo, times(2)).appendObj(new int['a']);
        verify(foo).appendObj(new int['b']);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testExpIsClassInstanceCreation() {
        Foo foo = Mockito.mock(Foo.class);
        arrayCreation.expIsClassInstanceCreation(foo);

        verify(foo, times(2)).appendObj(new String[new Integer(1)]);
        verify(foo).appendObj(new String[new Integer(2)]);
    }

    @Test
    public void testExpIsConditional() {
        Foo foo = Mockito.mock(Foo.class);
        int code = 1;
        arrayCreation.expIsConditional(foo);

        verify(foo, times(3)).appendObj(new String[code > 0 ? 1 : 0]);
        verify(foo).appendObj(new String[code > 1 ? 1 : 0]);
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
        arrayCreation.expIsMethodRef(foo);

        ArgumentCaptor<Function<String, Integer>> captorA =
                ArgumentCaptor.forClass(Function.class);
        ArgumentCaptor<Function<String, Integer>> captorB =
                ArgumentCaptor.forClass(Function.class);
        // ArgumentCaptor<Function<String, Integer>> captorC =
        // ArgumentCaptor.forClass(Function.class);
        // ArgumentCaptor<Function<String, Integer>> captorD =
        // ArgumentCaptor.forClass(Function.class);

        verify(foo, times(3)).valueOf(eq("1"), captorA.capture());
        verify(foo, times(4)).appendObj(new String[apple]);
        verify(foo).valueOf(eq("2"), captorB.capture());
        // verify(foo).appendObj(new String[grape]);
        // verify(foo, times(2)).valueOf("1", Integer::valueOf);
        // verify(foo).appendObj(new String[orange]);
        // verify(foo).appendObj(new String[kiwi]);
    }

    @Test
    public void testExpIsFieldAccess() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.mock(Box.class);
        arrayCreation.expIsFieldAccess(foo, box);

        verify(foo, times(3)).appendObj(new String[(box).id]);
        verify(foo).appendObj(new String[(box).id2]);
    }

    @Test
    public void testExpIsInfix() {
        Foo foo = Mockito.mock(Foo.class);
        arrayCreation.expIsInfix(foo);

        verify(foo, times(3)).appendObj(new String[1 + 1]);
        verify(foo).appendObj(new String[2 + 2]);
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
        arrayCreation.expIsLambda(foo);

        ArgumentCaptor<Function<String, Integer>> captorA =
                ArgumentCaptor.forClass(Function.class);
        ArgumentCaptor<Function<String, Integer>> captorB =
                ArgumentCaptor.forClass(Function.class);
        // ArgumentCaptor<Function<String, Integer>> captorC =
        // ArgumentCaptor.forClass(Function.class);
        // ArgumentCaptor<Function<String, Integer>> captorD =
        // ArgumentCaptor.forClass(Function.class);

        verify(foo, times(3)).valueOf(eq("1"), captorA.capture());
        verify(foo, times(4)).appendObj(new String[apple]);
        verify(foo).valueOf(eq("2"), captorB.capture());
        // verify(foo).appendObj(new String[grape]);
        // verify(foo, times(2)).valueOf("1", i -> Integer.valueOf(i));
        // verify(foo).appendObj(new String[orange]);
        // verify(foo).appendObj(new String[kiwi]);
    }

    @Test
    public void testExpIsMI() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.mock(Box.class);
        Box box2 = Mockito.mock(Box.class);
        int apple = 1;
        // int grape = 1;
        // int orange = 1;
        // int kiwi = 1;
        int grape = 2;
        int orange = 3;
        int kiwi = 4;

        when(box.getId()).thenReturn(apple).thenReturn(orange).thenReturn(kiwi);
        when(box2.getId()).thenReturn(grape);
        arrayCreation.expIsMI(foo, box, box2);

        verify(foo).appendObj(new String[apple]);
        verify(foo).appendObj(new String[grape]);
        verify(foo).appendObj(new String[orange]);
        verify(foo).appendObj(new String[kiwi]);
    }

    @Test
    public void testExpIsNumberLiteral() {
        Foo foo = Mockito.mock(Foo.class);
        arrayCreation.expIsNumberLiteral(foo);

        verify(foo, times(3)).appendObj(new String[1]);
        verify(foo).appendObj(new String[2]);
    }

    @Test
    public void testExpIsPostfix() {
        Foo foo = Mockito.mock(Foo.class);
        int a = 1;
        int b = 11;
        arrayCreation.expIsPostfix(foo);

        // verify(foo, times(3)).appendObj(new String[a++]);
        verify(foo).appendObj(new String[a++]);
        verify(foo).appendObj(new String[a++]);
        verify(foo).appendObj(new String[a++]);
        verify(foo).appendObj(new String[b++]);
    }

    @Test
    public void testExpIsPrefix() {
        Foo foo = Mockito.mock(Foo.class);
        int a = 1;
        int b = 11;
        arrayCreation.expIsPrefix(foo);

        // verify(foo, times(3)).appendObj(new String[++a]);
        verify(foo).appendObj(new String[++a]);
        verify(foo).appendObj(new String[++a]);
        verify(foo).appendObj(new String[++a]);
        verify(foo).appendObj(new String[++b]);
    }

    @Test
    public void testExpIsQName() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.mock(Box.class);
        arrayCreation.expIsQName(foo, box);

        verify(foo, times(3)).appendObj(new String[box.id]);
        verify(foo).appendObj(new String[box.id2]);
    }

    @Test
    public void testExpIsSimpleName() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.mock(Box.class);
        int a = 1;
        int b = 11;
        arrayCreation.expIsSimpleName(foo, box);

        verify(foo, times(3)).appendObj(new String[a]);
        verify(foo).appendObj(new String[b]);
    }

    @Test
    public void testExpIsThis() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.mock(Box.class);
        // int fa = 1;
        // int fb = 11;
        arrayCreation.expIsThis(foo, box);

        verify(foo, times(3)).appendObj(new String[arrayCreation.fa]);
        verify(foo).appendObj(new String[arrayCreation.fb]);
    }
}
