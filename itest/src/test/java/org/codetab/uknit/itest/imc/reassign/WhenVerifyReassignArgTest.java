package org.codetab.uknit.itest.imc.reassign;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.imc.reassign.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class WhenVerifyReassignArgTest {
    @InjectMocks
    private WhenVerifyReassignArg whenVerifyReassignArg;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCallReassignWhenVerify() {
        Foo foo = Mockito.mock(Foo.class);
        String name2 = "zoox";
        String name3 = name2;

        when(foo.format(name2)).thenReturn(name3);

        String actual = whenVerifyReassignArg.callReassignWhenVerify(foo);

        assertEquals(name3, actual);

        verify(foo).append(name2);
    }

    @Test
    public void testCallReassignVerifyWhen() {
        Foo foo = Mockito.mock(Foo.class);
        String name2 = "foo";
        String name4 = "Foo";
        String name3 = name4;

        when(foo.format(name2)).thenReturn(name4);

        String actual = whenVerifyReassignArg.callReassignVerifyWhen(foo);

        assertEquals(name3, actual);

        verify(foo).append(name2);
    }

    @Test
    public void testCallReassignOnceWhenVerify() {
        Foo foo = Mockito.mock(Foo.class);
        String name2 = "foo";
        String name4 = "Foo";
        String name5 = "foo2";
        String name6 = "Bar";
        String name3 = name6;

        when(foo.format(name2)).thenReturn(name4);
        when(foo.format(name5)).thenReturn(name6);

        String actual = whenVerifyReassignArg.callReassignOnceWhenVerify(foo);

        assertEquals(name3, actual);

        verify(foo).append(name4);
        verify(foo).append(name6);
    }

    @Test
    public void testCallReassignOnceVerifyWhen() {
        Foo foo = Mockito.mock(Foo.class);
        String name2 = "foo";
        String name4 = "Foo";
        String name5 = "foo2";
        String name6 = "Bar";
        String name3 = name6;

        when(foo.format(name2)).thenReturn(name4);
        when(foo.format(name5)).thenReturn(name6);

        String actual = whenVerifyReassignArg.callReassignOnceVerifyWhen(foo);

        assertEquals(name3, actual);

        verify(foo).append(name2);
        verify(foo).append(name5);
    }

    @Test
    public void testCallReassignTwiceWhenVerify() {
        Foo foo = Mockito.mock(Foo.class);
        String name2 = "foo";
        String name4 = "Foo";
        String name5 = "foo2";
        String name6 = "Bar";
        String name7 = "foo3";
        String name8 = "Baz";
        String name3 = name8;

        when(foo.format(name2)).thenReturn(name4);
        when(foo.format(name5)).thenReturn(name6);
        when(foo.format(name7)).thenReturn(name8);

        String actual = whenVerifyReassignArg.callReassignTwiceWhenVerify(foo);

        assertEquals(name3, actual);

        verify(foo).append(name4);
        verify(foo).append(name6);
        verify(foo).append(name8);
    }

    @Test
    public void testCallReassignTwiceVerifyWhen() {
        Foo foo = Mockito.mock(Foo.class);
        String name2 = "foo";
        String name4 = "Foo";
        String name5 = "foo2";
        String name6 = "Bar";
        String name7 = "foo3";
        String name8 = "Baz";
        String name3 = name8;

        when(foo.format(name2)).thenReturn(name4);
        when(foo.format(name5)).thenReturn(name6);
        when(foo.format(name7)).thenReturn(name8);

        String actual = whenVerifyReassignArg.callReassignTwiceVerifyWhen(foo);

        assertEquals(name3, actual);

        verify(foo).append(name2);
        verify(foo).append(name5);
        verify(foo).append(name7);
    }

    @Test
    public void testCallReassignTwiceTwoArgsWhenVerify() {
        Foo foo = Mockito.mock(Foo.class);
        String name2 = "foo";
        String city = "bar";
        String name4 = "Foo";
        String name5 = "foo2";
        String city2 = "bar2";
        String name6 = "Bar";
        String name7 = "foo3";
        String city3 = "bar3";
        String name8 = "Baz";
        String name3 = name8;

        when(foo.format(name2, city)).thenReturn(name4);
        when(foo.format(name5, city2)).thenReturn(name6);
        when(foo.format(name7, city3)).thenReturn(name8);

        String actual =
                whenVerifyReassignArg.callReassignTwiceTwoArgsWhenVerify(foo);

        assertEquals(name3, actual);

        verify(foo).append(name4, city);
        verify(foo).append(name6, city2);
        verify(foo).append(name8, city3);
    }

    @Test
    public void testCallReassignTwiceTwoArgsVerifyWhen() {
        Foo foo = Mockito.mock(Foo.class);
        String name2 = "foo";
        String city = "bar";
        String name4 = "Foo";
        String name5 = "foo2";
        String city2 = "bar2";
        String name6 = "Bar";
        String name7 = "foo3";
        String city3 = "bar3";
        String name8 = "Baz";
        String name3 = name8;

        when(foo.format(name2, city)).thenReturn(name4);
        when(foo.format(name5, city2)).thenReturn(name6);
        when(foo.format(name7, city3)).thenReturn(name8);

        String actual =
                whenVerifyReassignArg.callReassignTwiceTwoArgsVerifyWhen(foo);

        assertEquals(name3, actual);

        verify(foo).append(name2, city);
        verify(foo).append(name5, city2);
        verify(foo).append(name7, city3);
    }

    @Test
    public void testCallMultiVarWhenVerify() {
        Foo foo = Mockito.mock(Foo.class);
        String city2 = "bar";
        String name = "foo";
        String name2 = "Foo";
        String street = "boo";
        String street2 = "Bar";
        String street3 = "boo2";
        String street4 = "Baz";
        String city4 = "Qux";
        String city5 = "bar2";
        String city6 = "Quux";
        String city7 = "baz3";
        String city8 = "Corge";
        String city9 = "baz4";
        String city10 = "Grault";
        String city11 = "baz5";
        String city12 = "Garply";
        String city3 = city12;

        when(foo.format(name)).thenReturn(name2);
        when(foo.format(street)).thenReturn(street2);
        when(foo.format(street3)).thenReturn(street4);
        when(foo.format(city2)).thenReturn(city4);
        when(foo.format(city5)).thenReturn(city6);
        when(foo.format(city7)).thenReturn(city8);
        when(foo.format(city9)).thenReturn(city10);
        when(foo.format(city11)).thenReturn(city12);

        String actual = whenVerifyReassignArg.callMultiVarWhenVerify(foo);

        assertEquals(city3, actual);

        verify(foo).append(name2);
        verify(foo).append(street2);
        verify(foo).append(street4);
        verify(foo).append(city4);
        verify(foo).append(city6);
        verify(foo).append(city8);
        verify(foo).append(city10);
        verify(foo).append(city12);
    }

    @Test
    public void testCallMultiVarVerifyWhen() {
        Foo foo = Mockito.mock(Foo.class);
        String city2 = "bar";
        String name = "foo";
        String name2 = "Foo";
        String street = "boo";
        String street2 = "Bar";
        String street3 = "boo2";
        String street4 = "Baz";
        String city4 = "Qux";
        String city5 = "bar2";
        String city6 = "Quux";
        String city7 = "baz3";
        String city8 = "Corge";
        String city9 = "baz4";
        String city10 = "Grault";
        String city11 = "baz5";
        String city12 = "Garply";
        String city3 = city12;

        when(foo.format(name)).thenReturn(name2);
        when(foo.format(street)).thenReturn(street2);
        when(foo.format(street3)).thenReturn(street4);
        when(foo.format(city2)).thenReturn(city4);
        when(foo.format(city5)).thenReturn(city6);
        when(foo.format(city7)).thenReturn(city8);
        when(foo.format(city9)).thenReturn(city10);
        when(foo.format(city11)).thenReturn(city12);

        String actual = whenVerifyReassignArg.callMultiVarVerifyWhen(foo);

        assertEquals(city3, actual);

        verify(foo).append(name);
        verify(foo).append(street);
        verify(foo).append(street3);
        verify(foo).append(city2);
        verify(foo).append(city5);
        verify(foo).append(city7);
        verify(foo).append(city9);
        verify(foo).append(city11);
    }
}
