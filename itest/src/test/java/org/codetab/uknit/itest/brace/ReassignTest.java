package org.codetab.uknit.itest.brace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.brace.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class ReassignTest {
    @InjectMocks
    private Reassign reassign;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDefineAndAssign() {
        Foo foo = Mockito.mock(Foo.class);
        int index = 1;
        String x = "Foo";

        when((foo).get((index))).thenReturn(x);

        String actual = reassign.defineAndAssign(foo);

        assertEquals(x, actual);
    }

    @Test
    public void testReassignBeforeVarUse() {
        Foo foo = Mockito.mock(Foo.class);
        int index2 = 2;
        String y = "Foo";

        when((foo).get(index2)).thenReturn(y);

        String actual = reassign.reassignBeforeVarUse(foo);

        assertEquals(y, actual);
    }

    @Test
    public void testReassignAfterVarUse() {
        Foo foo = Mockito.mock(Foo.class);
        int index = 1;
        String x = "Foo";
        int index2 = 2;
        String y = "Bar";
        String apple = x + y;

        when((foo).get((index))).thenReturn(x);
        when((foo).get(index2)).thenReturn(y);

        String actual = reassign.reassignAfterVarUse(foo);

        assertEquals(apple, actual);
    }

    @Test
    public void testReassignNameVarConflicts() {
        Foo foo = Mockito.mock(Foo.class);
        int index = 1;
        String x = "Foo";
        int index3 = 2;
        String y = "Bar";
        String apple = (x) + (y);

        when((foo).get((index))).thenReturn(x);
        when((foo).get(index3)).thenReturn(y);

        String actual = reassign.reassignNameVarConflicts(foo);

        assertEquals(apple, actual);
    }
}
