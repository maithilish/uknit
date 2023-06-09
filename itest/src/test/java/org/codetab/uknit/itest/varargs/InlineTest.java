package org.codetab.uknit.itest.varargs;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.codetab.uknit.itest.varargs.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class InlineTest {
    @InjectMocks
    private Inline inline;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTwoVaArg() {
        Foo foo = Mockito.mock(Foo.class);
        // String a = "foo";

        String va0 = "foo";
        String va1 = "bar";
        String va2 = "barx";
        String va3 = "foox";
        // String va4 = "barx";
        // String va5 = "foo";
        String apple = va0;
        String grape = va1;
        String orange = va2;
        String kiwi = va3;
        // String mango = va4;
        // String banana = va5;
        inline.twoVaArg(foo);

        verify(foo, times(4)).append(apple);
        verify(foo).append(grape);
        verify(foo, times(2)).append(orange);
        verify(foo).append(kiwi);
    }

    @Test
    public void testThreeVaArg() {
        Foo foo = Mockito.mock(Foo.class);
        String a = "baz";

        String va0 = "foo";
        String va1 = "bar";
        String va3 = "barx";
        String va4 = "foox";
        // String va6 = "barx";
        // String va7 = "foo";
        String apple = va0;
        String grape = va1;
        String orange = va3;
        String kiwi = va4;
        // String mango = va6;
        // String banana = va7;
        inline.threeVaArg(foo);

        verify(foo, times(2)).append(apple);
        verify(foo).append(grape);
        verify(foo, times(2)).append(a);
        verify(foo, times(2)).append(orange);
        verify(foo).append(kiwi);
    }

    @Test
    public void testReassignedTwoVaArg() {
        Foo foo = Mockito.mock(Foo.class);
        String a = "foo";
        String b = "bar";
        String apple = a;
        String grape = b;
        String a2 = "foox";
        String b2 = "barx";
        String orange = b2;
        String kiwi = a2;
        inline.reassignedTwoVaArg(foo);

        verify(foo, times(2)).append(apple);
        verify(foo).append(grape);
        verify(foo).append(orange);
        verify(foo).append(kiwi);
    }

    @Test
    public void testNullVaArg() {
        Foo foo = Mockito.mock(Foo.class);
        // String a = "foo";

        String va0 = "foo";
        String va1 = null;
        // String va2 = null;
        String va3 = "foox";
        // String va4 = null;
        // String va5 = null;
        String apple = va0;
        String grape = va1;
        // String orange = va2;
        String kiwi = va3;
        // String mango = va4;
        // String banana = va5;
        inline.nullVaArg(foo);

        verify(foo, times(3)).append(apple);
        verify(foo, times(4)).append(grape);
        verify(foo).append(kiwi);
    }
}
