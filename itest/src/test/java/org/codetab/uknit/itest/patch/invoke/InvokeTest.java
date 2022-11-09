package org.codetab.uknit.itest.patch.invoke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.codetab.uknit.itest.patch.invoke.Model.Bar;
import org.codetab.uknit.itest.patch.invoke.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class InvokeTest {
    @InjectMocks
    private Invoke invoke;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAssignInvoke() {
        Bar bar = Mockito.mock(Bar.class);
        Foo foo = Mockito.mock(Foo.class);
        String apple = "Foo";
        Locale locale = Mockito.mock(Locale.class);

        when(foo.lang()).thenReturn(apple);
        when(bar.locale(apple)).thenReturn(locale);

        Locale actual = invoke.assignInvoke(bar, foo);

        assertSame(locale, actual);
    }

    @Test
    public void testReturnInvoke() {
        Bar bar = Mockito.mock(Bar.class);
        Foo foo = Mockito.mock(Foo.class);
        String apple = "Foo";
        Locale locale = Mockito.mock(Locale.class);

        when(foo.lang()).thenReturn(apple);
        when(bar.locale(apple)).thenReturn(locale);

        Locale actual = invoke.returnInvoke(bar, foo);

        assertSame(locale, actual);
    }

    @Test
    public void testAssignInvoke2() {
        Bar bar = Mockito.mock(Bar.class);
        Foo foo = Mockito.mock(Foo.class);
        String apple = "Foo";
        String grape = "Bar";
        Locale locale = Mockito.mock(Locale.class);

        when(foo.lang()).thenReturn(apple);
        when(foo.cntry()).thenReturn(grape);
        when(bar.locale(apple, grape)).thenReturn(locale);

        Locale actual = invoke.assignInvoke2(bar, foo);

        assertSame(locale, actual);
    }

    @Test
    public void testReturnInvoke2() {
        Bar bar = Mockito.mock(Bar.class);
        Foo foo = Mockito.mock(Foo.class);
        String apple = "Foo";
        String grape = "Bar";
        Locale locale = Mockito.mock(Locale.class);

        when(foo.lang()).thenReturn(apple);
        when(foo.cntry()).thenReturn(grape);
        when(bar.locale(apple, grape)).thenReturn(locale);

        Locale actual = invoke.returnInvoke2(bar, foo);

        assertSame(locale, actual);
    }

    @Test
    public void testAssignInvokeNested() {
        Bar bar = Mockito.mock(Bar.class);
        Foo foo = Mockito.mock(Foo.class);
        String apple = "Foo";
        String grape = "Bar";
        String orange = "Baz";
        Locale locale = Mockito.mock(Locale.class);

        when(foo.cntry()).thenReturn(apple).thenReturn(orange);
        when(foo.lang(apple)).thenReturn(grape);
        when(bar.locale(grape, orange)).thenReturn(locale);

        Locale actual = invoke.assignInvokeNested(bar, foo);

        assertSame(locale, actual);
    }

    @Test
    public void testReturnInvokeNested() {
        Bar bar = Mockito.mock(Bar.class);
        Foo foo = Mockito.mock(Foo.class);
        String apple = "Foo";
        String grape = "Bar";
        String orange = "Baz";
        Locale locale = Mockito.mock(Locale.class);

        when(foo.cntry()).thenReturn(apple).thenReturn(orange);
        when(foo.lang(apple)).thenReturn(grape);
        when(bar.locale(grape, orange)).thenReturn(locale);

        Locale actual = invoke.returnInvokeNested(bar, foo);

        assertSame(locale, actual);
    }

    @Test
    public void testAssginInvokeOnExpression() {
        Foo foo = Mockito.mock(Foo.class);
        Bar bar = Mockito.mock(Bar.class);
        String apple = "en";
        String grape = "en";
        Locale locale = Mockito.mock(Locale.class);
        String displayName = "Baz";

        when(foo.lang()).thenReturn(apple).thenReturn(grape);
        when(bar.locale(grape)).thenReturn(locale);

        String actual = invoke.assginInvokeOnExpression(foo, bar);

        assertEquals(displayName, actual);
    }

    @Test
    public void testReturnInvokeOnExpression() {
        Foo foo = Mockito.mock(Foo.class);
        Bar bar = Mockito.mock(Bar.class);
        String apple = "Foo";
        String grape = "Bar";
        Locale locale = Mockito.mock(Locale.class);
        String orange = "Baz";

        when(foo.lang()).thenReturn(apple).thenReturn(grape);
        when(bar.locale(grape)).thenReturn(locale);

        String actual = invoke.returnInvokeOnExpression(foo, bar);

        assertEquals(orange, actual);
    }

    @Test
    public void testAssginInvokeOnExpressionParenthesized() {
        Foo foo = Mockito.mock(Foo.class);
        Bar bar = Mockito.mock(Bar.class);
        String apple = "Foo";
        String grape = "Bar";
        Locale locale = Mockito.mock(Locale.class);
        String displayName = "Baz";

        when(foo.lang()).thenReturn(apple).thenReturn(grape);
        when(bar.locale(grape)).thenReturn(locale);

        String actual = invoke.assginInvokeOnExpressionParenthesized(foo, bar);

        assertEquals(displayName, actual);
    }

    @Test
    public void testReturnInvokeOnExpressionParenthesized() {
        Foo foo = Mockito.mock(Foo.class);
        Bar bar = Mockito.mock(Bar.class);
        String apple = "Foo";
        String grape = "Bar";
        Locale locale = Mockito.mock(Locale.class);
        String orange = "Baz";

        when(foo.lang()).thenReturn(apple).thenReturn(grape);
        when(bar.locale(grape)).thenReturn(locale);

        String actual = invoke.returnInvokeOnExpressionParenthesized(foo, bar);

        assertEquals(orange, actual);
    }
}
