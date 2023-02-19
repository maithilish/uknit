package org.codetab.uknit.itest.imc.reassign;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.imc.reassign.Model.Bar;
import org.codetab.uknit.itest.imc.reassign.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class FieldTest {
    @InjectMocks
    private Field field;

    @Mock
    private Bar bar;
    @Mock
    private Foo foo;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReassignRealSimple() {
        String city3 = "Bar";
        String city2 = city3;

        when(foo.cntry()).thenReturn(city3);

        String actual = field.reassignRealSimple();

        assertEquals(city2, actual);
    }

    @Test
    public void testReassignReal() {
        Foo foo = Mockito.mock(Foo.class);
        String city3 = "a";
        String city4 = "Bar";
        String city5 = "b";
        String apple = "Baz";
        String city2 = apple;

        when(foo.format(city3)).thenReturn(city4);
        when(foo.format(city5)).thenReturn(apple);

        String actual = field.reassignReal(foo);

        assertEquals(city2, actual);

        verify(foo).append(city3);
        verify(foo).append(city5);
        verify(foo).append(city2);
    }

    @Test
    public void testReassignRealInitialized() {
        Foo foo = Mockito.mock(Foo.class);
        String town2 = "aaa";
        String town4 = "bbb";
        String town5 = "Bar";
        String town6 = "ccc";
        String apple = "Baz";
        String town3 = apple;

        when(foo.format(town4)).thenReturn(town5);
        when(foo.format(town6)).thenReturn(apple);

        String actual = field.reassignRealInitialized(foo);

        assertEquals(town3, actual);

        verify(foo).append(town2);
        verify(foo).append(town4);
        verify(foo).append(town6);
        verify(foo).append(town3);
    }

    @Test
    public void testReassignRealHide() {
        Foo foo = Mockito.mock(Foo.class);
        String city2 = "a";
        String city3 = "Bar";
        String city4 = "b";
        String city5 = "Baz";
        String city = city5;

        when(foo.format(city2)).thenReturn(city3);
        when(foo.format(city4)).thenReturn(city5);

        String actual = field.reassignRealHide(foo);

        assertEquals(city, actual);

        verify(foo).append(city2);
        verify(foo).append(city4);
        verify(foo).append(city);
    }

    @Test
    public void testReassignMock() {
        Foo foo = Mockito.mock(Foo.class);
        Bar bar4 = Mockito.mock(Bar.class);
        Bar bar5 = Mockito.mock(Bar.class);
        Bar bar2 = bar5;

        when(bar.format(foo)).thenReturn(bar4);
        when(bar4.format(foo)).thenReturn(bar5);

        Bar actual = field.reassignMock(foo);

        assertSame(bar2, actual);

        verify(bar).append(foo);
        verify(bar4).append(foo);
        verify(bar2).append(foo);
    }

    @Test
    public void testReassignMock2() {
        Bar bar3 = Mockito.mock(Bar.class);
        Bar bar4 = Mockito.mock(Bar.class);
        Bar bar2 = bar4;

        when(bar.format(bar)).thenReturn(bar3);
        when(bar3.format(bar3)).thenReturn(bar4);

        Bar actual = field.reassignMock2();

        assertSame(bar2, actual);

        verify(bar).append(bar);
        verify(bar3).append(bar3);
        verify(bar2).append(bar2);
    }

    @Test
    public void testReassignMockHide() {
        Foo foo = Mockito.mock(Foo.class);
        Bar bar = Mockito.mock(Bar.class);
        Bar bar3 = Mockito.mock(Bar.class);
        Bar bar4 = Mockito.mock(Bar.class);
        Bar bar2 = bar4;

        when(bar.format(foo)).thenReturn(bar3);
        when(bar3.format(foo)).thenReturn(bar4);

        Bar actual = field.reassignMockHide(foo, bar);

        assertSame(bar2, actual);

        verify(bar).append(foo);
        verify(bar3).append(foo);
        verify(bar2).append(foo);
    }

    @Test
    public void testNestedRealSimple() {
        String city3 = "Bar";
        String city4 = city3;
        String city2 = city4;

        when(foo.cntry()).thenReturn(city3);

        String actual = field.nestedRealSimple();

        assertEquals(city2, actual);
    }

    @Test
    public void testNestedReal() {
        Foo foo = Mockito.mock(Foo.class);
        String city3 = "a";
        String city4 = "Bar";
        String city5 = "b";
        String grape = "Baz";
        String apple = grape;
        String city2 = apple;

        when(foo.format(city3)).thenReturn(city4);
        when(foo.format(city5)).thenReturn(grape);

        String actual = field.nestedReal(foo);

        assertEquals(city2, actual);

        verify(foo).append(city3);
        verify(foo).append(city5);
        verify(foo).append(city2);
    }

    @Test
    public void testNestedRealInitialized() {
        Foo foo = Mockito.mock(Foo.class);
        String town2 = "aaa";
        String town4 = "bbb";
        String town5 = "Bar";
        String town6 = "ccc";
        String town7 = "Baz";
        String grape = "Qux";
        String apple = grape;
        String town3 = apple;

        when(foo.format(town4)).thenReturn(town5);
        when(foo.format(town6)).thenReturn(town7);
        when(foo.format(town7)).thenReturn(grape);

        String actual = field.nestedRealInitialized(foo);

        assertEquals(town3, actual);

        verify(foo).append(town2);
        verify(foo).append(town4);
        verify(foo).append(town6);
        verify(foo).append(town3);
    }

    @Test
    public void testNestedRealHide() {
        Foo foo = Mockito.mock(Foo.class);
        String city3 = "a";
        String city4 = "Bar";
        String city5 = "b";
        String city2 = city5;
        String city6 = "Baz";
        String city = city6;

        when(foo.format(city3)).thenReturn(city4);
        when(foo.format(city2)).thenReturn(city6);

        String actual = field.nestedRealHide(foo);

        assertEquals(city, actual);

        verify(foo).append(city3);
        verify(foo).append(city5);
        verify(foo).append(city);
    }

    @Test
    public void testNestedMock() {
        Foo foo = Mockito.mock(Foo.class);
        Bar bar5 = Mockito.mock(Bar.class);
        Bar bar3 = bar5;
        Bar bar4 = Mockito.mock(Bar.class);
        Bar bar6 = Mockito.mock(Bar.class);
        Bar bar2 = bar6;

        when(bar.format(foo)).thenReturn(bar5);
        when(bar3.format(foo)).thenReturn(bar4);
        when(bar4.format(foo)).thenReturn(bar6);

        Bar actual = field.nestedMock(foo);

        assertSame(bar2, actual);

        verify(bar).append(foo);
        verify(bar3).append(foo);
        verify(bar4).append(foo);
        verify(bar2).append(foo);
    }

    @Test
    public void testNestedMock2() {
        Bar bar3 = Mockito.mock(Bar.class);
        Bar bar4 = Mockito.mock(Bar.class);
        Bar bar2 = bar4;

        when(bar.format(bar)).thenReturn(bar3);
        when(bar3.format(bar3)).thenReturn(bar4);

        Bar actual = field.nestedMock2();

        assertSame(bar2, actual);

        verify(bar).append(bar);
        verify(bar3).append(bar3);
        verify(bar2).append(bar2);
    }

    @Test
    public void testNestedMockHide() {
        Foo foo = Mockito.mock(Foo.class);
        Bar bar = Mockito.mock(Bar.class);
        Bar bar3 = Mockito.mock(Bar.class);
        Bar bar4 = Mockito.mock(Bar.class);
        Bar bar5 = bar4;
        Bar bar2 = bar5;

        when(bar.format(foo)).thenReturn(bar3);
        when(bar3.format(foo)).thenReturn(bar4);

        Bar actual = field.nestedMockHide(foo, bar);

        assertSame(bar2, actual);

        verify(bar).append(foo);
        verify(bar3).append(foo);
        verify(bar2).append(foo);
    }

    @Test
    public void testNestedMockHide2() {
        Foo foo = Mockito.mock(Foo.class);
        Bar bar = Mockito.mock(Bar.class);
        Bar bar3 = Mockito.mock(Bar.class);
        Bar bar4 = bar3;
        Bar bar2 = bar4;

        when(bar.format(foo)).thenReturn(bar3);

        Bar actual = field.nestedMockHide2(foo, bar);

        assertSame(bar2, actual);
    }
}
