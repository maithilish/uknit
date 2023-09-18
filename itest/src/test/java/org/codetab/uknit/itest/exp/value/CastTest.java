package org.codetab.uknit.itest.exp.value;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.codetab.uknit.itest.exp.value.Model.Box;
import org.codetab.uknit.itest.exp.value.Model.Foo;
import org.codetab.uknit.itest.exp.value.Model.Pitbull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class CastTest {
    @InjectMocks
    private Cast cast;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExpIsArrayAccess() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "foo";
        String grape = "bar";
        // String orange = "bar";
        String kiwi = "baz";
        String mango = "barx";
        cast.expIsArrayAccess(foo);

        verify(foo).appendString(apple);
        verify(foo, times(2)).appendString(grape);
        verify(foo).appendString(kiwi);
        verify(foo).appendString(mango);
    }

    // @Test
    // public void testExpIsArrayCreation() {
    // Foo foo = Mockito.mock(Foo.class);
    // cast.expIsArrayCreation(foo);
    //
    // verify(foo, times(3)).appendStringArray((String[]) new Object[]{"foo",
    // "bar"});
    // }

    @Test
    public void testExpIsBooleanLiteral() {
        Foo foo = Mockito.mock(Foo.class);
        boolean t = true;
        boolean f = false;
        cast.expIsBooleanLiteral(foo);

        verify(foo).appendBoolean(t);
        verify(foo, times(2)).appendBoolean(f);
    }

    @Test
    public void testExpIsCastExp() {
        Foo foo = Mockito.mock(Foo.class);
        Pitbull dog = Mockito.mock(Pitbull.class);
        cast.expIsCastExp(foo, dog);

        verify(foo, times(2)).appendPitbull(dog);
    }

    @Test
    public void testExpIsCharacter() {
        Foo foo = Mockito.mock(Foo.class);
        char a = 'a';
        char b = 'b';
        cast.expIsCharacter(foo);

        verify(foo).appendCharacter(a);
        verify(foo, times(2)).appendCharacter(b);
    }

    // @Test
    // public void testExpIsNewInstanceCreation() {
    // Foo foo = Mockito.mock(Foo.class);
    // cast.expIsNewInstanceCreation(foo);
    //
    // verify(foo, times(2)).appendPitbull((Pitbull) new Dog());
    // }

    @Test
    public void testExpIsConditional() {
        Foo foo = Mockito.mock(Foo.class);
        boolean flg = true;
        cast.expIsConditional(foo);

        verify(foo).appendObj(flg ? (Integer) 2 : (Float) 2f);
        verify(foo, times(2)).appendObj(flg ? (Integer) 3 : (Float) 3f);
    }

    @Test
    public void testExpIsFieldAccess() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.mock(Box.class);
        cast.expIsFieldAccess(foo, box);

        verify(foo, times(3)).appendString((String) (box).obj);
    }

    @Test
    public void testExpIsMethodRef() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "bar";
        String grape = "foobar";
        cast.expIsMethodRef(foo);

        verify(foo).appendString(apple);
        verify(foo).appendString(grape);
    }

    @Test
    public void testExpIsNullLiteral() {
        Foo foo = Mockito.mock(Foo.class);
        cast.expIsNullLiteral(foo);

        verify(foo, times(2)).appendString((String) null);
    }

    @Test
    public void testExpIsNumberLiteral() {
        Foo foo = Mockito.mock(Foo.class);
        cast.expIsNumberLiteral(foo);

        verify(foo).appendString(String.valueOf((Integer) 1));
        verify(foo, times(2)).appendString(String.valueOf((Integer) 2));
    }

    @Test
    public void testExpIsPostfix() {
        Foo foo = Mockito.mock(Foo.class);
        int a = 5;
        int b = 6;
        cast.expIsPostfix(foo);

        verify(foo).appendString(String.valueOf((Integer) a++));
        verify(foo).appendString(String.valueOf((Integer) b++));
        verify(foo).appendString(String.valueOf((Integer) b++));
    }

    @Test
    public void testExpIsPrefix() {
        Foo foo = Mockito.mock(Foo.class);
        int a = 5;
        int b = 8;
        cast.expIsPrefix(foo);

        verify(foo).appendString(String.valueOf((Integer) (--a)));
        verify(foo).appendString(String.valueOf((Integer) (--b)));
        verify(foo).appendString(String.valueOf((Integer) (--b)));
    }

    @Test
    public void testExpIsQName() {
        Foo foo = Mockito.mock(Foo.class);
        Box box = Mockito.mock(Box.class);
        cast.expIsQName(foo, box);

        verify(foo, times(3)).appendString((String) box.obj);
    }

    @Test
    public void testExpIsSimpleName() {
        Foo foo = Mockito.mock(Foo.class);
        String o = "foo";
        cast.expIsSimpleName(foo);

        verify(foo, times(3)).appendString(o);
    }

    @Test
    public void testExpIsThisExp() {
        Foo foo = Mockito.mock(Foo.class);
        cast.expIsThisExp(foo);

        verify(foo, times(3)).appendString((String) cast.tobj);
    }
}
