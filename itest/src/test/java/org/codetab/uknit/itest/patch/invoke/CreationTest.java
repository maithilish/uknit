package org.codetab.uknit.itest.patch.invoke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.codetab.uknit.itest.patch.invoke.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class CreationTest {
    @InjectMocks
    private Creation creation;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAssginCreate() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "Foo";
        Locale locale = new Locale(apple);

        when(foo.lang()).thenReturn(apple);

        Locale actual = creation.assginCreate(foo);

        assertEquals(locale, actual);
    }

    @Test
    public void testReturnCreate() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "Foo";
        Locale locale = new Locale(apple);

        when(foo.lang()).thenReturn(apple);

        Locale actual = creation.returnCreate(foo);

        assertEquals(locale, actual);
    }

    @Test
    public void testAssginCreate2() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "Foo";
        String grape = "Bar";
        Locale locale = new Locale(apple, grape);

        when(foo.lang()).thenReturn(apple);
        when(foo.cntry()).thenReturn(grape);

        Locale actual = creation.assginCreate2(foo);

        assertEquals(locale, actual);
    }

    @Test
    public void testReturnCreate2() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "Foo";
        String grape = "Bar";
        Locale locale = new Locale(apple, grape);

        when(foo.lang()).thenReturn(apple);
        when(foo.cntry()).thenReturn(grape);

        Locale actual = creation.returnCreate2(foo);

        assertEquals(locale, actual);
    }

    @Test
    public void testAssginCreateNestedInvoke() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "Foo";
        String grape = "Bar";
        Locale locale = new Locale(grape);

        when(foo.cntry()).thenReturn(apple);
        when(foo.lang(apple)).thenReturn(grape);

        Locale actual = creation.assginCreateNestedInvoke(foo);

        assertEquals(locale, actual);
    }

    @Test
    public void testReturnCreateNestedInvoke() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "Foo";
        String grape = "Bar";
        Locale locale = new Locale(grape);

        when(foo.cntry()).thenReturn(apple);
        when(foo.lang(apple)).thenReturn(grape);

        Locale actual = creation.returnCreateNestedInvoke(foo);

        assertEquals(locale, actual);
    }
}
