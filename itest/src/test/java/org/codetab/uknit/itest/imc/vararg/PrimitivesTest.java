package org.codetab.uknit.itest.imc.vararg;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.codetab.uknit.itest.imc.vararg.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class PrimitivesTest {
    @InjectMocks
    private Primitives primitives;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBooleans() {
        Foo foo = Mockito.mock(Foo.class);
        boolean a = false;
        boolean apple = a;
        boolean orange = a;
        // boolean va0 = false;
        boolean va2 = true;
        // boolean va3 = true;
        // boolean kiwi = va0;
        // boolean banana = va0;
        boolean cherry = va2;
        boolean peach = va2;
        // boolean fig = va3;
        // boolean lychee = va3;

        primitives.booleans(foo);

        verify(foo, times(2)).appendString(String.valueOf(apple));
        verify(foo, times(2)).appendBoolean(orange);
        verify(foo, times(2)).appendString(String.valueOf(cherry));
        verify(foo, times(2)).appendBoolean(peach);
    }

    @Test
    public void testInts() {
        Foo foo = Mockito.mock(Foo.class);
        int va0 = 1;
        // int va2 = 1;
        int apple = va0;
        int orange = va0;
        // int kiwi = va2;
        // int banana = va2;

        primitives.ints(foo);

        verify(foo, times(2)).appendString(String.valueOf(apple));
        verify(foo, times(2)).appendInt(orange);
    }
}
