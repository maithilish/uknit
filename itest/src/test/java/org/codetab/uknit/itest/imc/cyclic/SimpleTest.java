package org.codetab.uknit.itest.imc.cyclic;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.codetab.uknit.itest.imc.cyclic.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class SimpleTest {
    @InjectMocks
    private Simple simple;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCallEachOther11() {
        Foo foo = Mockito.mock(Foo.class);
        simple.callEachOther11(foo);

        verify(foo).append("imc12");
        verify(foo).append("imc13");
    }

    @Test
    public void testCallEachOther21() {
        Foo foo = Mockito.mock(Foo.class);
        simple.callEachOther21(foo);

        verify(foo).append("imc22 before");
        verify(foo).append("imc23 before");
        verify(foo).append("imc23 after");
        verify(foo).append("imc22 after");
    }

    @Test
    public void testCallAncestor31() {
        Foo foo = Mockito.mock(Foo.class);
        simple.callAncestor31(foo);

        verify(foo).append("imc32 before");
        verify(foo).append("imc33 before");
        verify(foo).append("imc34 before");
        verify(foo).append("imc34 after");
        verify(foo).append("imc33 after");
        verify(foo).append("imc32 after");
    }

    @Test
    public void testCallBase41IfFinish() {
        Foo foo = Mockito.mock(Foo.class);
        boolean finish = true;
        simple.callBase41(foo, finish);

        verify(foo, never()).append("imc42 before");
        verify(foo, never()).append("imc43 before");
        verify(foo, never()).append("imc43 after");
        verify(foo, never()).append("imc42 after");
    }

    @Test
    public void testCallBase41ElseFinish() {
        Foo foo = Mockito.mock(Foo.class);
        boolean finish = false;
        simple.callBase41(foo, finish);

        verify(foo).append("imc42 before");
        verify(foo).append("imc43 before");
        verify(foo).append("imc43 after");
        verify(foo).append("imc42 after");
    }
}
