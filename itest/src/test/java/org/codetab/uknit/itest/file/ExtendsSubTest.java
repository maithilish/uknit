package org.codetab.uknit.itest.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.file.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class ExtendsSubTest {
    @SuppressWarnings("rawtypes")
    @InjectMocks
    private ExtendsSub extendsSub;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFoo() {
        Foo foo = Mockito.mock(Foo.class);
        String grape = null;
        String config = grape;
        String apple = "Bar";

        when(foo.format(config)).thenReturn(apple);

        String actual = extendsSub.foo(foo);

        assertEquals(apple, actual);
    }
}
