package org.codetab.uknit.itest.imc.cyclic;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.imc.cyclic.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class MultiCallTest {
    @InjectMocks
    private MultiCall multiCall;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMultiCall51() {
        Foo foo = Mockito.mock(Foo.class);
        String orange = "Foo";
        String grape = "Bar";
        String apple = "Baz";
        String mango = "Qux";
        String kiwi = "Quux";
        String apricot = "Corge";
        String cherry = "Grault";
        String banana = "Garply";
        String fig = "Waldo";
        String peach = "Fred";
        String lychee = "Plugh";
        String plum = "Xyzzy";
        String barracuda = "Thud";
        String scrappy = "Zoopy";

        when(foo.format("i54 after")).thenReturn(orange).thenReturn(mango)
                .thenReturn(apricot).thenReturn(fig).thenReturn(lychee)
                .thenReturn(scrappy);
        when(foo.format("i53 after")).thenReturn(grape).thenReturn(kiwi)
                .thenReturn(cherry).thenReturn(peach).thenReturn(plum)
                .thenReturn(barracuda);
        when(foo.format("i52 after")).thenReturn(apple).thenReturn(banana);
        multiCall.multiCall51(foo);

        verify(foo, times(2)).append("i52 before");
        verify(foo, times(11)).append("i53 before");
        verify(foo, times(6)).append("i54 before");
        verify(foo, times(6)).append("i54 after");
        verify(foo, times(11)).append("i53 after");
        verify(foo, times(2)).append("i52 after");
    }

    @Test
    public void testCallBase61() {
        Foo foo = Mockito.mock(Foo.class);
        boolean finish = true;
        String apple = "Foo";
        String orange = "Bar";
        String grape = "Baz";

        when(foo.format("i62 after")).thenReturn(apple).thenReturn(orange);
        when(foo.format("i63 after")).thenReturn(grape);
        multiCall.callBase61(foo, finish);

        verify(foo).append("before i62");
        verify(foo, times(2)).append("i62 before");
        verify(foo, times(2)).append("i62 after");
        verify(foo).append("before i63");
        verify(foo).append("i63 before");
        verify(foo).append("i63 after");
    }
}
