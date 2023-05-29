package org.codetab.uknit.itest.initializer;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.initializer.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class ArrayTest {
    @InjectMocks
    private Array array;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUseArrayAccessInWhen() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "bar";
        String grape = "Bar";

        when(foo.format(apple)).thenReturn(grape);
        array.useArrayAccessInWhen(foo);

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testUseArrayAccessInVerify() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "foo";
        array.useArrayAccessInVerify(foo);

        verify(foo).append(apple);
    }
}
