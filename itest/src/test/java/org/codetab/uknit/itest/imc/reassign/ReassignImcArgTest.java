package org.codetab.uknit.itest.imc.reassign;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.imc.reassign.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class ReassignImcArgTest {
    @InjectMocks
    private ReassignImcArg reassignImcArg;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDefineAndAssign() {
        Foo foo = Mockito.mock(Foo.class);
        int index = 1;
        String y = "Foo";
        String x = y;

        when(foo.get(index)).thenReturn(y);

        String actual = reassignImcArg.defineAndAssign(foo);

        assertEquals(x, actual);
    }

    @Test
    public void testReassignBeforeVarUse() {
        Foo foo = Mockito.mock(Foo.class);
        int index2 = 2;
        String y2 = "Foo";
        String y = y2;

        when(foo.get(index2)).thenReturn(y2);

        String actual = reassignImcArg.reassignBeforeVarUse(foo);

        assertEquals(y, actual);
    }

    @Test
    public void testReassignAfterVarUse() {
        Foo foo = Mockito.mock(Foo.class);
        int index = 1;
        String y2 = "Foo";
        String x = y2;
        int index2 = 2;
        String y3 = "Bar";
        String y = y3;
        String apple = x + y;

        when(foo.get(index)).thenReturn(y2);
        when(foo.get(index2)).thenReturn(y3);

        String actual = reassignImcArg.reassignAfterVarUse(foo);

        assertEquals(apple, actual);
    }

    @Test
    public void testReassignAfterVarUse2() {
        Foo foo = Mockito.mock(Foo.class);
        int index = 1;
        String y2 = "Foo";
        int index2 = 2;
        String y3 = "Baz";
        String y = y3;

        when(foo.get(index)).thenReturn(y2);
        when(foo.get(index2)).thenReturn(y3);

        String actual = reassignImcArg.reassignAfterVarUse2(foo);

        assertEquals(y, actual);
    }

    @Test
    public void testReassignNameVarConflicts() {
        Foo foo = Mockito.mock(Foo.class);
        int index = 1;
        String y2 = "Foo";
        String x = y2;
        int index3 = 2;
        String y3 = "Bar";
        String y = y3;
        String apple = x + y;

        when(foo.get(index)).thenReturn(y2);
        when(foo.get(index3)).thenReturn(y3);

        String actual = reassignImcArg.reassignNameVarConflicts(foo);

        assertEquals(apple, actual);
    }
}
