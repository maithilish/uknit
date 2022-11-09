package org.codetab.uknit.itest.linked;

import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.linked.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class UnusedTest {
    @InjectMocks
    private Unused unused;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUnusedButInovked() {
        Foo foo = Mockito.mock(Foo.class);
        Object unObj = Mockito.mock(Object.class);

        when(foo.obj()).thenReturn(unObj);
        unused.unusedButInovked(foo);
    }

    @Test
    public void testUnusedButInovkedAndCreated() {
        Foo foo = Mockito.mock(Foo.class);
        unused.unusedButInovkedAndCreated(foo);
    }
}
