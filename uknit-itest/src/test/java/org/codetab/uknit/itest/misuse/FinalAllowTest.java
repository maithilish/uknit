package org.codetab.uknit.itest.misuse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.misuse.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class FinalAllowTest {
    @InjectMocks
    private FinalAllow finalAllow;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFoo() {
        Foo foo = Mockito.mock(Foo.class);
        int flag = 1;

        when(foo.finalMethod("foo", "bar")).thenReturn(flag);

        int actual = finalAllow.foo(foo);

        assertEquals(flag, actual);
    }
}
