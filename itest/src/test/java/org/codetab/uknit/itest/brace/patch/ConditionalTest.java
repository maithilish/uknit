package org.codetab.uknit.itest.brace.patch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.codetab.uknit.itest.brace.patch.Model.Bar;
import org.codetab.uknit.itest.brace.patch.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ConditionalTest {
    @InjectMocks
    private Conditional conditional;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAssignConditional() {
        Foo foo = Mockito.mock(Foo.class);
        Bar bar = Mockito.mock(Bar.class);
        int apple = 1;
        String grape = "Foo";
        Locale locale2 = Mockito.mock(Locale.class);
        Locale locale3 = Mockito.mock(Locale.class);
        Locale locale = apple > 1 ? locale2 : locale3;

        when(foo.size()).thenReturn(apple);
        when(foo.lang()).thenReturn(grape);
        when(bar.locale(grape)).thenReturn(locale2);
        when(bar.locale("en")).thenReturn(locale3);

        Locale actual = conditional.assignConditional(foo, bar);

        assertEquals(locale, actual);
    }

    @Test
    public void testReturnConditional() {
        Foo foo = Mockito.mock(Foo.class);
        Bar bar = Mockito.mock(Bar.class);
        int apple = 1;
        String grape = "Foo";
        Locale locale = Mockito.mock(Locale.class);
        Locale locale2 = Mockito.mock(Locale.class);
        Locale locale3 = apple > 1 ? locale : locale2;

        when(foo.size()).thenReturn(apple);
        when(foo.lang()).thenReturn(grape);
        when(bar.locale(grape)).thenReturn(locale);
        when(bar.locale("en")).thenReturn(locale2);

        Locale actual = conditional.returnConditional(foo, bar);

        assertEquals(locale3, actual);
    }
}
