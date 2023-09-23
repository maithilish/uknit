package org.codetab.uknit.itest.reassign;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.reassign.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class WhenVerifyTest {
    @InjectMocks
    private WhenVerify whenVerify;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReassignWhenVerify() {
        Foo foo = Mockito.mock(Foo.class);
        String name = "foo";
        String name2 = "Foo";

        when(foo.format(name)).thenReturn(name2);
        whenVerify.reassignWhenVerify(foo);

        verify(foo).append(name2);
    }

    @Test
    public void testReassignVerifyWhen() {
        Foo foo = Mockito.mock(Foo.class);
        String name = "foo";
        String name2 = "Foo";

        when(foo.format(name)).thenReturn(name2);
        whenVerify.reassignVerifyWhen(foo);

        verify(foo).append(name);
    }

    @Test
    public void testReassignOnceWhenVerify() {
        Foo foo = Mockito.mock(Foo.class);
        String name = "foo";
        String name2 = "Foo";
        String name3 = "foo2";
        String name4 = "Bar";

        when(foo.format(name)).thenReturn(name2);
        when(foo.format(name3)).thenReturn(name4);
        whenVerify.reassignOnceWhenVerify(foo);

        verify(foo).append(name2);
        verify(foo).append(name4);
    }

    @Test
    public void testReassignOnceVerifyWhen() {
        Foo foo = Mockito.mock(Foo.class);
        String name = "foo";
        String name2 = "Foo";
        String name3 = "foo2";
        String name4 = "Bar";

        when(foo.format(name)).thenReturn(name2);
        when(foo.format(name3)).thenReturn(name4);
        whenVerify.reassignOnceVerifyWhen(foo);

        verify(foo).append(name);
        verify(foo).append(name3);
    }

    @Test
    public void testReassignTwiceWhenVerify() {
        Foo foo = Mockito.mock(Foo.class);
        String name = "foo";
        String name2 = "Foo";
        String name3 = "foo2";
        String name4 = "Bar";
        String name5 = "foo3";
        String name6 = "Baz";

        when(foo.format(name)).thenReturn(name2);
        when(foo.format(name3)).thenReturn(name4);
        when(foo.format(name5)).thenReturn(name6);
        whenVerify.reassignTwiceWhenVerify(foo);

        verify(foo).append(name2);
        verify(foo).append(name4);
        verify(foo).append(name6);
    }

    @Test
    public void testReassignTwiceVerifyWhen() {
        Foo foo = Mockito.mock(Foo.class);
        String name = "foo";
        String name2 = "Foo";
        String name3 = "foo2";
        String name4 = "Bar";
        String name5 = "foo3";
        String name6 = "Baz";

        when(foo.format(name)).thenReturn(name2);
        when(foo.format(name3)).thenReturn(name4);
        when(foo.format(name5)).thenReturn(name6);
        whenVerify.reassignTwiceVerifyWhen(foo);

        verify(foo).append(name);
        verify(foo).append(name3);
        verify(foo).append(name5);
    }

    @Test
    public void testReassignTwiceTwoArgsWhenVerify() {
        Foo foo = Mockito.mock(Foo.class);
        String name = "foo";
        String city = "bar";
        String name2 = "Foo";
        String name3 = "foo2";
        String city2 = "bar2";
        String name4 = "Bar";
        String name5 = "foo3";
        String city3 = "bar3";
        String name6 = "Baz";

        when(foo.format(name, city)).thenReturn(name2);
        when(foo.format(name3, city2)).thenReturn(name4);
        when(foo.format(name5, city3)).thenReturn(name6);
        whenVerify.reassignTwiceTwoArgsWhenVerify(foo);

        verify(foo).append(name2, city);
        verify(foo).append(name4, city2);
        verify(foo).append(name6, city3);
    }

    @Test
    public void testReassignTwiceTwoArgsVerifyWhen() {
        Foo foo = Mockito.mock(Foo.class);
        String name = "foo";
        String city = "bar";
        String name2 = "Foo";
        String name3 = "foo2";
        String city2 = "bar2";
        String name4 = "Bar";
        String name5 = "foo3";
        String city3 = "bar3";
        String name6 = "Baz";

        when(foo.format(name, city)).thenReturn(name2);
        when(foo.format(name3, city2)).thenReturn(name4);
        when(foo.format(name5, city3)).thenReturn(name6);
        whenVerify.reassignTwiceTwoArgsVerifyWhen(foo);

        verify(foo).append(name, city);
        verify(foo).append(name3, city2);
        verify(foo).append(name5, city3);
    }

    @Test
    public void testMultiVarWhenVerify() {
        Foo foo = Mockito.mock(Foo.class);
        String name = "foo";
        String name2 = "Foo";
        String street = "boo";
        String street2 = "Bar";
        String street3 = "boo2";
        String street4 = "Baz";
        String city = "bar";
        String city2 = "Qux";
        String city3 = "bar2";
        String city4 = "Quux";
        String city5 = "baz3";
        String city6 = "Corge";
        String city7 = "baz4";
        String city8 = "Grault";
        String city9 = "baz5";
        String city10 = "Garply";

        when(foo.format(name)).thenReturn(name2);
        when(foo.format(street)).thenReturn(street2);
        when(foo.format(street3)).thenReturn(street4);
        when(foo.format(city)).thenReturn(city2);
        when(foo.format(city3)).thenReturn(city4);
        when(foo.format(city5)).thenReturn(city6);
        when(foo.format(city7)).thenReturn(city8);
        when(foo.format(city9)).thenReturn(city10);
        whenVerify.multiVarWhenVerify(foo);

        verify(foo).append(name2);
        verify(foo).append(street2);
        verify(foo).append(street4);
        verify(foo).append(city2);
        verify(foo).append(city4);
        verify(foo).append(city6);
        verify(foo).append(city8);
        verify(foo).append(city10);
    }

    @Test
    public void testMultiVarVerifyWhen() {
        Foo foo = Mockito.mock(Foo.class);
        String name = "foo";
        String name2 = "Foo";
        String street = "boo";
        String street2 = "Bar";
        String street3 = "boo2";
        String street4 = "Baz";
        String city = "bar";
        String city2 = "Qux";
        String city3 = "bar2";
        String city4 = "Quux";
        String city5 = "baz3";
        String city6 = "Corge";
        String city7 = "baz4";
        String city8 = "Grault";
        String city9 = "baz5";
        String city10 = "Garply";

        when(foo.format(name)).thenReturn(name2);
        when(foo.format(street)).thenReturn(street2);
        when(foo.format(street3)).thenReturn(street4);
        when(foo.format(city)).thenReturn(city2);
        when(foo.format(city3)).thenReturn(city4);
        when(foo.format(city5)).thenReturn(city6);
        when(foo.format(city7)).thenReturn(city8);
        when(foo.format(city9)).thenReturn(city10);
        whenVerify.multiVarVerifyWhen(foo);

        verify(foo).append(name);
        verify(foo).append(street);
        verify(foo).append(street3);
        verify(foo).append(city);
        verify(foo).append(city3);
        verify(foo).append(city5);
        verify(foo).append(city7);
        verify(foo).append(city9);
    }
}
