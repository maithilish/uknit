package org.codetab.uknit.itest.internal;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.codetab.uknit.itest.internal.Model.Foo;
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
    public void testArgParamSame() {
        Foo foo = Mockito.mock(Foo.class);
        String grape = "foo";
        String orange = "bar";
        String kiwi = "foox";
        String mango = new String("barx");
        // String apple = "foo";
        arrayAccess.argParamSame(foo);

        verify(foo, times(2)).append(grape);
        verify(foo).append(orange);
        verify(foo).append(kiwi);
        verify(foo).append(mango);
    }

    @Test
    public void testArgParamDiff() {
        Foo foo = Mockito.mock(Foo.class);
        String grape = "foo";
        String orange = new String("bar");
        String kiwi = new String("foox");
        String mango = "barx";
        // String apple = "foo";
        arrayAccess.argParamDiff(foo);

        verify(foo, times(2)).append(grape);
        verify(foo).append(orange);
        verify(foo).append(kiwi);
        verify(foo).append(mango);
    }

    @Test
    public void testArrayDefinedInIM() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = new String("foo");
        String grape = "bar";
        // String orange = new String("foo");
        // String kiwi = "bar";
        arrayAccess.arrayDefinedInIM(foo);

        verify(foo, times(2)).append(apple);
        verify(foo, times(2)).append(grape);
    }
}
