package org.codetab.uknit.itest.brace.patch;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.brace.patch.Model.Bar;
import org.codetab.uknit.itest.brace.patch.Model.Factory;
import org.codetab.uknit.itest.brace.patch.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class NameChangeTest {
    @InjectMocks
    private NameChange nameChange;

    @Mock
    private Factory factory;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testVarNameChangeInReturn() {
        Foo foo = Mockito.mock(Foo.class);
        Foo foo2 = Mockito.mock(Foo.class);
        Foo otherFoo = foo2;

        when((factory).createFoo()).thenReturn(foo).thenReturn(foo2);

        Foo actual = nameChange.varNameChangeInReturn();

        assertSame(otherFoo, actual);
    }

    @Test
    public void testVarNameChangeInSimpleNameInvoke() {
        Foo foo = Mockito.mock(Foo.class);
        Foo foo2 = Mockito.mock(Foo.class);
        Bar bar = Mockito.mock(Bar.class);
        Foo otherFoo = foo2;

        when(factory.createFoo()).thenReturn(foo);
        when((factory).createFoo()).thenReturn(foo2);
        when(foo2.bar()).thenReturn(bar);

        Foo actual = nameChange.varNameChangeInSimpleNameInvoke();

        assertSame(otherFoo, actual);
    }

    @Test
    public void testVarNameChangeInExpInvoke() {
        Foo foo = Mockito.mock(Foo.class);
        Foo foo2 = Mockito.mock(Foo.class);
        Bar bar = Mockito.mock(Bar.class);
        Foo otherFoo = foo2;

        when((factory).createFoo()).thenReturn(foo).thenReturn(foo2);
        when(foo2.bar()).thenReturn(bar);

        Foo actual = nameChange.varNameChangeInExpInvoke();

        assertSame(otherFoo, actual);
        verify(bar).baz();
    }
}
