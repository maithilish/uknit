package org.codetab.uknit.itest.exp.value;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.codetab.uknit.itest.exp.value.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class InternalCallTest {
    @InjectMocks
    private InternalCall internalCall;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInvokeInIM() {
        Foo foo = Mockito.mock(Foo.class);
        String city = "foo";
        String state = "bar";
        String str = "baz";
        String str2 = "zoo";
        internalCall.invokeInIM(foo);

        verify(foo, times(2)).appendString(city);
        // verify(foo).appendString(String.valueOf(city));
        verify(foo, times(2)).appendString(str);
        // verify(foo).appendString(String.valueOf(str));
        verify(foo, times(2)).appendString(state);
        // verify(foo).appendString(String.valueOf(state));
        verify(foo, times(2)).appendString(str2);
        // verify(foo).appendString(String.valueOf(str2));
    }

    @Test
    public void testArrayAccessByVar() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "x";
        // String grape = "x";
        String grape = "y";
        // String kiwi = "x";
        // String mango = "x";
        internalCall.arrayAccessByVar(foo);

        verify(foo, times(2)).appendString(apple);
        verify(foo, times(2)).appendString(String.valueOf(grape));
    }
}
