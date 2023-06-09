package org.codetab.uknit.itest.brace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class RecursiveTest {
    @InjectMocks
    private Recursive recursive;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testArray1() {
        int foo = 10;

        int actual = recursive.array1();

        assertEquals(foo, actual);
    }

    @Test
    public void testArray2() {
        int foo = 20;

        int actual = recursive.array2();

        assertEquals(foo, actual);
    }

    @Test
    public void testArray3() {
        int foo = 30;

        int actual = recursive.array3();

        assertEquals(foo, actual);
    }

    @Test
    public void testReturnArrayAccess() {
        int orange = 30;

        int actual = recursive.returnArrayAccess();

        assertEquals(orange, actual);
    }

    @Test
    public void testInvokeInit() {
        Locale locale = Mockito.mock(Locale.class);
        String apple = "Foo";
        String foo = apple;

        when(locale.getDisplayName()).thenReturn(apple);

        String actual = recursive.invokeInit(locale);

        assertEquals(foo, actual);
    }
}
