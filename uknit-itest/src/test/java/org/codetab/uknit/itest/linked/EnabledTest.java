package org.codetab.uknit.itest.linked;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.codetab.uknit.itest.linked.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class EnabledTest {
    @InjectMocks
    private Enabled enabled;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDisableUnusedVars() {
        Locale obj = new Locale("");
        Object obj3 = obj;

        Object actual = enabled.disableUnusedVars();

        assertEquals(obj3, actual);
    }

    @Test
    public void testDisableUnusedVarsFoo() {
        Foo foo = Mockito.mock(Foo.class);
        Object obj = Mockito.mock(Object.class);
        Object obj3 = obj;

        when(foo.obj()).thenReturn(obj);

        Object actual = enabled.disableUnusedVars(foo);

        assertSame(obj3, actual);
    }

    @Test
    public void testDisableUnusedVarsCreated() {
        Foo foo = Mockito.mock(Foo.class);
        Object obj = Mockito.mock(Object.class);
        Object obj3 = obj;

        when(foo.obj()).thenReturn(obj);

        Object actual = enabled.disableUnusedVarsCreated(foo);

        assertSame(obj3, actual);
    }

    @Test
    public void testEnableUnusedVarsButWhenExists() {
        Foo foo = Mockito.mock(Foo.class);
        Locale obj = new Locale("");
        Object obj4 = Mockito.mock(Object.class);
        Object obj3 = obj;

        when(foo.obj()).thenReturn(obj4);

        Object actual = enabled.enableUnusedVarsButWhenExists(foo);

        assertEquals(obj3, actual);
    }
}
