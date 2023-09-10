package org.codetab.uknit.itest.exp.rejig;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.exp.rejig.Model.Box;
import org.codetab.uknit.itest.exp.rejig.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class RejigThisTest {
    @InjectMocks
    private RejigThis rejigThis;

    @Mock
    private RejigThis o1;
    @Mock
    private RejigThis o2;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testThisInArrayCreation() {
        Foo foo = Mockito.mock(Foo.class);
        RejigThis rejigThis = new RejigThis();
        // RejigThis rejigThis2 = new RejigThis();
        // RejigThis rejigThis3 = new RejigThis();
        String apple = "foo";
        rejigThis.thisInArrayCreation(foo);

        verify(foo, times(3)).appendObj(rejigThis);
        // verify(foo).appendObj(rejigThis2);
        // verify(foo).appendObj(rejigThis3);
        verify(foo).appendObj(apple);
        verify(foo).appendObj(new Object[] {"foo"});
        verify(foo, times(2)).appendObj(
                new Object[] {rejigThis, rejigThis, rejigThis, "bar"});
    }

    @Test
    public void testThisInArrayInitializer() {
        Foo foo = Mockito.mock(Foo.class);
        RejigThis rejigThis = new RejigThis();
        // RejigThis rejigThis2 = new RejigThis();
        // RejigThis rejigThis3 = new RejigThis();
        String apple = "foo";
        rejigThis.thisInArrayInitializer(foo);

        verify(foo, times(3)).appendObj(rejigThis);
        // verify(foo).appendObj(rejigThis2);
        // verify(foo).appendObj(rejigThis3);
        verify(foo).appendObj(apple);
    }

    @Test
    public void testThisInClassInstanceCreation() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = new Box(rejigThis);
        rejigThis.thisInClassInstanceCreation(foo);

        verify(foo, times(4)).appendBox(box);
    }

    @Test
    public void testThisInConditionalExp() {
        Foo foo = Mockito.mock(Foo.class);
        // int fcode = 0;
        rejigThis.thisInConditionalExp(foo);

        verify(foo).appendObj(rejigThis.fcode);
        verify(foo).appendObj(rejigThis.fcode > 1 ? rejigThis : "bar");
        verify(foo, times(3))
                .appendObj(rejigThis.fcode > 1 ? "foo" : rejigThis);
    }

    @Test
    public void testThisInConditionalAnswer() {
        Foo foo = Mockito.mock(Foo.class);
        int a = 0;
        // Object code = a > 1 ? rejigThis : "bar";
        rejigThis.thisInConditionalAnswer(foo);

        verify(foo, times(2)).appendObj("bar");
        // verify(foo).appendObj(a > 1 ? rejigThis : "bar");
        verify(foo, times(3)).appendObj(a > 1 ? "foo" : rejigThis);
    }

    @Test
    public void testThisInMethodRef() {
        Foo foo = Mockito.mock(Foo.class);
        Integer apple = Integer.valueOf(10);
        rejigThis.thisInMethodRef(foo);

        verify(foo).appendObj(apple);
    }

    @Test
    public void testThisInFieldAccess() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.mock(Box.class);
        // int fcode = 0;
        rejigThis.thisInFieldAccess(foo, box);

        verify(foo, times(3)).appendObj(rejigThis.fcode);
    }

    @Test
    public void testThisInInfix() {
        Foo foo = Mockito.mock(Foo.class);
        rejigThis.thisInInfix(foo);

        verify(foo, times(8)).appendObj(false);
        // verify(foo, times(2)).appendObj(rejigThis == o1);
        // verify(foo, times(2)).appendObj(rejigThis == o2);
        // verify(foo, times(3)).appendObj(o1 == rejigThis);
        // verify(foo).appendObj(o2 == rejigThis);
    }

    @Test
    public void testThisFieldAccessInInfix() {
        Foo foo = Mockito.mock(Foo.class);
        rejigThis.thisFieldAccessInInfix(foo);

        // verify(foo, times(3)).appendObj(rejigThis.fcode + 1);
        verify(foo, times(6)).appendObj(rejigThis.fcode + 1);
        verify(foo, times(2)).appendObj(rejigThis.fcode + 2);
        // verify(foo, times(3)).appendObj(1 + rejigThis.fcode);
        // verify(foo).appendObj(2 + rejigThis.fcode);
    }

    @Test
    public void testThisInLambda() {
        Foo foo = Mockito.mock(Foo.class);
        Integer apple = Integer.valueOf(10);
        rejigThis.thisInLambda(foo);

        verify(foo).appendObj(apple);
    }

    @Test
    public void testThisInInstanceof() {
        Foo foo = Mockito.mock(Foo.class);
        rejigThis.thisInInstanceof(foo);

        verify(foo, times(3)).appendObj(rejigThis instanceof RejigThis);
    }

    @Test
    public void testThisInMIExp() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.mock(Box.class);
        Integer apple = Integer.valueOf(10);
        // Integer grape = Integer.valueOf(10);
        // Integer orange = Integer.valueOf(10);
        rejigThis.thisInMIExp(foo, box);

        verify(foo, times(3)).appendObj(apple);
        // verify(foo).appendObj(grape);
        // verify(foo).appendObj(orange);
    }

    @Test
    public void testThisInMIArg() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.mock(Box.class);
        Object obj1 = Mockito.mock(Object.class);
        Object obj2 = Mockito.mock(Object.class);

        when(box.strip(rejigThis)).thenReturn(obj1).thenReturn(obj2);
        rejigThis.thisInMIArg(foo, box);

        verify(foo, times(2)).appendObj(obj1);
        verify(foo).appendObj(obj2);
    }

    @Test
    public void testThisFieldAccessInPostfix() {
        Foo foo = Mockito.mock(Foo.class);
        // int c = rejigThis.fcode++;
        // int fcode = 0;
        rejigThis.thisFieldAccessInPostfix(foo);

        verify(foo, times(2)).appendInt(1);
        verify(foo).appendInt(2);
    }

    @Test
    public void testThisFieldAccessInPrefix() {
        Foo foo = Mockito.mock(Foo.class);
        // int c = ++rejigThis.fcode;
        // int fcode = 0;
        rejigThis.thisFieldAccessInPrefix(foo);

        verify(foo).appendInt(2);
        verify(foo).appendInt(3);
        verify(foo).appendInt(4);
    }

    @Test
    public void testThisInthisExpression() {
        Foo foo = Mockito.mock(Foo.class);
        rejigThis.thisInthisExpression(foo);

        verify(foo, times(2)).appendObj(rejigThis);
    }
}
