package org.codetab.uknit.itest.exp.value;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.function.Function;

import org.codetab.uknit.itest.exp.value.Model.Box;
import org.codetab.uknit.itest.exp.value.Model.Dog;
import org.codetab.uknit.itest.exp.value.Model.Foo;
import org.codetab.uknit.itest.exp.value.Model.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class InstanceOfTest {
    @InjectMocks
    private InstanceOf instanceOf;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLeftExpIsArrayAccess() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "foo";
        Object localDate2 = LocalDate.now();
        // String grape = "foo";
        // String orange = "foo";
        instanceOf.leftExpIsArrayAccess(foo);

        verify(foo, times(3)).appendBoolean(apple instanceof String);
        verify(foo).appendBoolean(localDate2 instanceof String);
    }

    @Test
    public void testLeftExpIsArrayCreation() {
        Foo foo = Mockito.mock(Foo.class);
        Object apple = new Object[] {"foo", LocalDate.now()}[0];
        Object localDate3 = new Object[] {"foo", LocalDate.now()}[1];
        // String grape = new Object[]{"foo", LocalDate.now()}[(0)];
        // String orange = new Object[]{("foo"), (LocalDate.now())}[((0))];
        instanceOf.leftExpIsArrayCreation(foo);

        verify(foo, times(3)).appendBoolean(apple instanceof String);
        verify(foo).appendBoolean(localDate3 instanceof String);
    }

    @Test
    public void testLeftExpIsCast() {
        Foo foo = Mockito.mock(Foo.class);
        Dog dog = new Dog();
        // Pet pet = new Pet();
        instanceOf.leftExpIsCast(foo);

        // verify(foo, times(3)).appendBoolean(dog instanceof Pet);
        verify(foo, times(4)).appendBoolean(dog instanceof Pet);
        // verify(foo).appendBoolean(pet instanceof Pet);
    }

    @Test
    public void testLeftExpIsClassInstanceCreation() {
        Foo foo = Mockito.mock(Foo.class);
        instanceOf.leftExpIsClassInstanceCreation(foo);

        // verify(foo, times(3)).appendBoolean(new Dog() instanceof Dog);
        verify(foo, times(4)).appendBoolean(new Dog() instanceof Dog);
        // verify(foo).appendBoolean(new Pet() instanceof Pet);
    }

    @Test
    public void testLeftExpIsConditional() {
        Foo foo = Mockito.mock(Foo.class);
        int count = 0;
        instanceOf.leftExpIsConditional(foo);

        // verify(foo, times(3)).appendBoolean((count > 0 ? LocalDate.now() :
        // new Dog()) instanceof Dog);
        verify(foo, times(4)).appendBoolean(
                (count > 0 ? LocalDate.now() : new Dog()) instanceof Dog);
        // verify(foo).appendBoolean((count == 0 ? new Dog() : LocalDate.now())
        // instanceof Dog);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testLeftExpIsMethodRef() {
        Foo foo = Mockito.mock(Foo.class);
        Integer apple = Integer.valueOf(1);
        Integer grape = Integer.valueOf(1);
        Integer orange = Integer.valueOf(1);
        Integer kiwi = Integer.valueOf(1);

        when(foo.valueOf(eq("1"), any(Function.class))).thenReturn(apple)
                .thenReturn(orange).thenReturn(kiwi);
        when(foo.valueOf(eq("2"), any(Function.class))).thenReturn(grape);
        instanceOf.leftExpIsMethodRef(foo);

        ArgumentCaptor<Function<String, Integer>> captorA =
                ArgumentCaptor.forClass(Function.class);
        ArgumentCaptor<Function<String, Integer>> captorB =
                ArgumentCaptor.forClass(Function.class);
        // ArgumentCaptor<Function<String, Integer>> captorC =
        // ArgumentCaptor.forClass(Function.class);
        // ArgumentCaptor<Function<String, Integer>> captorD =
        // ArgumentCaptor.forClass(Function.class);

        // verify(foo).valueOf(eq("1"), captorA.capture());
        verify(foo, times(3)).valueOf(eq("1"), captorA.capture());
        // verify(foo).appendBoolean(apple instanceof Integer);
        verify(foo, times(4)).appendBoolean(apple instanceof Integer);
        verify(foo).valueOf(eq("2"), captorB.capture());
        // verify(foo).appendBoolean(grape instanceof Integer);
        // verify(foo, times(2)).valueOf("1", Integer::valueOf);
        // verify(foo).appendBoolean(orange instanceof Integer);
        // verify(foo).appendBoolean(kiwi instanceof Integer);
    }

    @Test
    public void testLeftExpIsFieldAccess() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.mock(Box.class);
        instanceOf.leftExpIsFieldAccess(foo, box);

        // verify(foo, times(3)).appendBoolean((box).iid instanceof Integer);
        verify(foo, times(4)).appendBoolean((box).iid instanceof Integer);
        // verify(foo).appendBoolean((box).iid instanceof Double);
    }

    @Test
    public void testLeftExpIsInfix() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.mock(Box.class);
        String a = "foo";
        String b = "bar";
        instanceOf.leftExpIsInfix(foo, box);

        // verify(foo, times(3)).appendBoolean(a + b instanceof String);
        verify(foo, times(4)).appendBoolean(a + b instanceof String);
        // verify(foo).appendBoolean(a + b instanceof CharSequence);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testLeftExpIsLambda() {
        Foo foo = Mockito.mock(Foo.class);
        Integer apple = Integer.valueOf(1);
        Integer grape = Integer.valueOf(1);
        Integer orange = Integer.valueOf(1);
        Integer kiwi = Integer.valueOf(1);

        when(foo.valueOf(eq("1"), any(Function.class))).thenReturn(apple)
                .thenReturn(orange).thenReturn(kiwi);
        when(foo.valueOf(eq("2"), any(Function.class))).thenReturn(grape);
        instanceOf.leftExpIsLambda(foo);

        ArgumentCaptor<Function<String, Integer>> captorA =
                ArgumentCaptor.forClass(Function.class);
        ArgumentCaptor<Function<String, Integer>> captorB =
                ArgumentCaptor.forClass(Function.class);
        // ArgumentCaptor<Function<String, Integer>> captorC =
        // ArgumentCaptor.forClass(Function.class);
        // ArgumentCaptor<Function<String, Integer>> captorD =
        // ArgumentCaptor.forClass(Function.class);

        // verify(foo).valueOf(eq("1"), captorA.capture());
        verify(foo, times(3)).valueOf(eq("1"), captorA.capture());
        // verify(foo).valueOf(eq("3"), captorA.capture());
        // verify(foo).appendBoolean(apple instanceof Integer);
        verify(foo, times(4)).appendBoolean(apple instanceof Integer);
        verify(foo).valueOf(eq("2"), captorB.capture());
        // verify(foo).appendBoolean(grape instanceof Integer);
        // verify(foo, times(2)).valueOf("1", s -> Integer.valueOf(s));
        // verify(foo).appendBoolean(orange instanceof Integer);
        // verify(foo).appendBoolean(kiwi instanceof Integer);
    }

    @Test
    public void testLeftExpIsMI() {
        Foo foo = Mockito.mock(Foo.class);
        Object apple = Mockito.mock(Object.class);
        Object grape = Mockito.mock(Object.class);
        Object orange = Mockito.mock(Object.class);
        Object kiwi = Mockito.mock(Object.class);

        when(foo.getObj()).thenReturn(apple).thenReturn(grape)
                .thenReturn(orange).thenReturn(kiwi);
        instanceOf.leftExpIsMI(foo);

        // verify(foo).appendBoolean(apple instanceof String);
        verify(foo, times(4)).appendBoolean(apple instanceof String);
        // verify(foo).appendBoolean(grape instanceof LocalDate);
        // verify(foo).appendBoolean(orange instanceof String);
        // verify(foo).appendBoolean(kiwi instanceof String);
    }

    @Test
    public void testLeftExpIsPostfix() {
        Foo foo = Mockito.mock(Foo.class);
        Integer a = Integer.valueOf(1);
        // Integer b = Integer.valueOf(1);
        instanceOf.leftExpIsPostfix(foo);

        // verify(foo, times(3)).appendBoolean(a++ instanceof Integer);
        verify(foo, times(4)).appendBoolean(a++ instanceof Integer);
        // verify(foo).appendBoolean(b++ instanceof Integer);
    }

    @Test
    public void testLeftExpIsPrefix() {
        Foo foo = Mockito.mock(Foo.class);
        Integer a = Integer.valueOf(1);
        // Integer b = Integer.valueOf(1);
        instanceOf.leftExpIsPrefix(foo);

        // verify(foo, times(3)).appendBoolean(++a instanceof Integer);
        verify(foo, times(4)).appendBoolean(++a instanceof Integer);
        // verify(foo).appendBoolean(++b instanceof Integer);
    }

    @Test
    public void testLeftExpIsQName() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.mock(Box.class);
        instanceOf.leftExpIsQName(foo, box);

        // verify(foo, times(3)).appendBoolean(box.iid instanceof Integer);
        verify(foo, times(4)).appendBoolean(box.iid instanceof Integer);
        // verify(foo).appendBoolean(box.iid instanceof Double);
    }

    @Test
    public void testLeftExpIsSimpleName() {
        Foo foo = Mockito.mock(Foo.class);
        Integer a = Integer.valueOf(1);
        Object b = Integer.valueOf(2);
        instanceOf.leftExpIsSimpleName(foo);

        verify(foo, times(3)).appendBoolean(a instanceof Integer);
        verify(foo).appendBoolean(b instanceof Double);
    }

    @Test
    public void testLeftExpIsStringLiteral() {
        Foo foo = Mockito.mock(Foo.class);
        instanceOf.leftExpIsStringLiteral(foo);

        // verify(foo, times(3)).appendBoolean("foo" instanceof String);
        verify(foo, times(4)).appendBoolean("foo" instanceof String);
        // verify(foo).appendBoolean("bar" instanceof String);
    }

    @Test
    public void testLeftExpIsThis() {
        Foo foo = Mockito.mock(Foo.class);
        // String bar = "bar";
        // String baz = "baz";
        instanceOf.leftExpIsThis(foo);

        // verify(foo, times(3)).appendBoolean(instanceOf.bar instanceof
        // String);
        verify(foo, times(4)).appendBoolean(instanceOf.bar instanceof String);
        // verify(foo).appendBoolean(instanceOf.baz instanceof String);
    }

    @Test
    public void testLeftExpIsTypeLiteral() {
        Foo foo = Mockito.mock(Foo.class);
        instanceOf.leftExpIsTypeLiteral(foo);

        // verify(foo, times(3)).appendBoolean(String.class instanceof Class);
        verify(foo, times(4)).appendBoolean(String.class instanceof Class);
        // verify(foo).appendBoolean(LocalDate.class instanceof Class);
    }
}
