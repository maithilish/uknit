package org.codetab.uknit.itest.misuse;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.codetab.uknit.itest.misuse.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class FinalDisallowTest {
    @InjectMocks
    private FinalDisallow finalDisallow;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFoo() {
        Foo foo = Mockito.mock(Foo.class);
        int flag = 0;

        int actual = finalDisallow.foo(foo);

        assertEquals(flag, actual);
    }
}
