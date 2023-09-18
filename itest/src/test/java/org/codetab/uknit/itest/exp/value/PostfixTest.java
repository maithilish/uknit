package org.codetab.uknit.itest.exp.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.codetab.uknit.itest.exp.value.Model.Box;
import org.codetab.uknit.itest.exp.value.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class PostfixTest {
    @InjectMocks
    private Postfix postfix;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAssignIsPostfix() {
        int i = 0;
        int i2 = i++;

        int actual = postfix.assignIsPostfix();

        assertEquals(i2, actual);
    }

    @Test
    public void testReturnExpIsPostfix() {
        int i = 0;
        int apple = i++;

        int actual = postfix.returnExpIsPostfix();

        assertEquals(apple, actual);
    }

    @Test
    public void testExpIsArrayAccess() {
        Foo foo = Mockito.mock(Foo.class);
        int apple = 1;
        int grape = 11;
        // int orange = 1;
        // int kiwi = 1;
        postfix.expIsArrayAccess(foo);

        verify(foo).appendInt(apple++);
        verify(foo).appendInt(apple++);
        verify(foo).appendInt(apple++);
        verify(foo).appendInt(grape++);
    }

    @Test
    public void testExpIsArrayCreation() {
        Foo foo = Mockito.mock(Foo.class);
        int apple = new int[] {1, 11}[0];
        int grape = new int[] {1, 11}[1];
        // int orange = new int[]{(1), (11)}[(0)];
        // int kiwi = new int[]{(1), (11)}[(0)];
        postfix.expIsArrayCreation(foo);

        verify(foo, times(3)).appendInt(apple++);
        verify(foo).appendInt(grape++);
    }

    @Test
    public void testExpIsCast() {
        Foo foo = Mockito.mock(Foo.class);
        int a = 1;
        int b = 11;
        postfix.expIsCast(foo);

        verify(foo).appendString(String.valueOf((Integer) a++));
        verify(foo).appendString(String.valueOf((Integer) a++));
        verify(foo).appendString(String.valueOf((Integer) b++));
        verify(foo).appendString(String.valueOf(((Integer) a++)));
    }

    @Test
    public void testExpIsFieldAccess() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.mock(Box.class);
        postfix.expIsFieldAccess(foo, box);

        verify(foo).appendInt(0);
        verify(foo).appendInt(1);
        verify(foo).appendInt(2);
        verify(foo).appendObj(0.0f);
    }

    @Test
    public void testExpIsQName() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.mock(Box.class);
        postfix.expIsQName(foo, box);

        verify(foo).appendInt(0);
        verify(foo).appendInt(1);
        verify(foo).appendInt(2);
        verify(foo).appendObj(0.0f);
    }

    @Test
    public void testExpIsSimpleName() {
        Foo foo = Mockito.mock(Foo.class);
        int a = 1;
        int b = 11;
        postfix.expIsSimpleName(foo);

        verify(foo).appendInt(a++);
        verify(foo).appendInt(a++);
        verify(foo).appendInt(a++);
        verify(foo).appendObj(b++);
    }

    @Test
    public void testExpIsThis() {
        Foo foo = Mockito.mock(Foo.class);
        postfix.expIsThis(foo);

        verify(foo).appendInt(1);
        verify(foo).appendInt(2);
        verify(foo).appendInt(3);
        verify(foo).appendObj(11);
    }
}
