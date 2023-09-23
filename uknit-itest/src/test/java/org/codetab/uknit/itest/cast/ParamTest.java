package org.codetab.uknit.itest.cast;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.cast.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class ParamTest {
    @InjectMocks
    private Param param;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCastParam() {
        Foo foo = Mockito.mock(Foo.class);
        Foo fooObj = Mockito.mock(Foo.class);
        Object count = Mockito.mock(Object.class);
        int apple = 1;
        int grape = 2;

        when(fooObj.index()).thenReturn(apple).thenReturn(grape);
        param.castParam(foo, fooObj, count);

        verify(foo).append(apple);
        verify(foo).append(grape);
    }
}
