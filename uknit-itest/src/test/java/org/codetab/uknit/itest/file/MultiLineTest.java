package org.codetab.uknit.itest.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.file.Model.Duck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class MultiLineTest {
    @InjectMocks
    private MultiLine multiLine;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFoo() {
        Duck duck = Mockito.mock(Duck.class);
        String orange = "Foo";
        String grape = orange;
        String apple = grape;

        when(duck.fly("dive in super 2")).thenReturn(orange);

        String actual = multiLine.foo(duck);

        assertEquals(apple, actual);

        verify(duck).dive("dive in super 1");
        verify(duck).dive("dive in super 2");
    }
}
