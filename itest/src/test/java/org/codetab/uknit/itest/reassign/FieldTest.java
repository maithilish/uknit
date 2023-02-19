package org.codetab.uknit.itest.reassign;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.reassign.Model.Bar;
import org.codetab.uknit.itest.reassign.Model.Foo;
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

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReassignReal() {
        Foo foo = Mockito.mock(Foo.class);
        String city2 = "a";
        String city3 = "Bar";
        String city4 = "b";
        String city5 = "Baz";

        when(foo.format(city2)).thenReturn(city3);
        when(foo.format(city4)).thenReturn(city5);

        String actual = field.reassignReal(foo);

        assertEquals(city5, actual);

        verify(foo).append(city2);
        verify(foo).append(city4);
        verify(foo).append(city5);
    }

    @Test
    public void testReassignRealInitialized() {
        Foo foo = Mockito.mock(Foo.class);
        String town2 = "aaa";
        String town3 = "Bar";
        String town4 = "bbb";
        String town5 = "Baz";

        when(foo.format(town2)).thenReturn(town3);
        when(foo.format(town4)).thenReturn(town5);

        String actual = field.reassignRealInitialized(foo);

        assertEquals(town5, actual);

        verify(foo).append(town2);
        verify(foo).append(town4);
        verify(foo).append(town5);
    }

    @Test
    public void testReassignRealHide() {
        Foo foo = Mockito.mock(Foo.class);
        String city = "a";
        String city2 = "Bar";
        String city3 = "b";
        String city4 = "Baz";

        when(foo.format(city)).thenReturn(city2);
        when(foo.format(city3)).thenReturn(city4);

        String actual = field.reassignRealHide(foo);

        assertEquals(city4, actual);

        verify(foo).append(city);
        verify(foo).append(city3);
        verify(foo).append(city4);
    }

    @Test
    public void testReassignMock() {
        Foo foo = Mockito.mock(Foo.class);
        Bar bar2 = Mockito.mock(Bar.class);
        Bar bar3 = Mockito.mock(Bar.class);

        when(bar.format(foo)).thenReturn(bar2);
        when(bar2.format(foo)).thenReturn(bar3);

        Bar actual = field.reassignMock(foo);

        assertSame(bar3, actual);

        verify(bar).append(foo);
        verify(bar2).append(foo);
        verify(bar3).append(foo);
    }

    @Test
    public void testReassignMock2() {
        Bar bar2 = Mockito.mock(Bar.class);
        Bar bar3 = Mockito.mock(Bar.class);

        when(bar.format(bar)).thenReturn(bar2);
        when(bar2.format(bar2)).thenReturn(bar3);

        Bar actual = field.reassignMock2();

        assertSame(bar3, actual);

        verify(bar).append(bar);
        verify(bar2).append(bar2);
        verify(bar3).append(bar3);
    }

    @Test
    public void testReassignMockHide() {
        Foo foo = Mockito.mock(Foo.class);
        Bar bar = Mockito.mock(Bar.class);
        Bar bar2 = Mockito.mock(Bar.class);
        Bar bar3 = Mockito.mock(Bar.class);

        when(bar.format(foo)).thenReturn(bar2);
        when(bar2.format(foo)).thenReturn(bar3);

        Bar actual = field.reassignMockHide(foo, bar);

        assertSame(bar3, actual);

        verify(bar).append(foo);
        verify(bar2).append(foo);
        verify(bar3).append(foo);
    }
}
