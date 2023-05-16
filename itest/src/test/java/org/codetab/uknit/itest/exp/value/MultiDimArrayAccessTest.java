package org.codetab.uknit.itest.exp.value;

import static org.mockito.Mockito.verify;

import org.codetab.uknit.itest.exp.value.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
class MultiDimArrayAccessTest {
    @InjectMocks
    private MultiDimArrayAccess multiDimArrayAccess;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLiteral() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "foo";
        String grape = "bar";
        int orange = 1;
        int kiwi = 2;
        int mango = 11;
        int banana = 12;
        String cherry = "foo1";
        String apricot = "bar1";
        String peach = "foo2";
        String fig = "bar2";
        String plum = "foox";
        String lychee = "barx";
        multiDimArrayAccess.literal(foo);

        verify(foo).append(apple);
        verify(foo).append(grape);
        verify(foo).append(orange);
        verify(foo).append(kiwi);
        verify(foo).append(mango);
        verify(foo).append(banana);
        verify(foo).append(cherry);
        verify(foo).append(apricot);
        verify(foo).append(peach);
        verify(foo).append(fig);
        verify(foo).append(plum);
        verify(foo).append(lychee);
    }
}
