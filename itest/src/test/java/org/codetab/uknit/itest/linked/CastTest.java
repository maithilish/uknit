package org.codetab.uknit.itest.linked;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.codetab.uknit.itest.linked.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class CastTest {
    @InjectMocks
    private Cast cast;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCastCreated() {
        Date date = new Date();

        Date actual = cast.castCreated();

        assertEquals(date, actual);
    }

    @Test
    public void testCastTwiceCreated() {
        Date date = new Date();
        Date date2 = date;

        Date actual = cast.castTwiceCreated();

        assertEquals(date2, actual);
    }

    @Test
    public void testCastThriceCreated() {
        Date date = new Date();
        Date date2 = date;
        Date date3 = date2;

        Date actual = cast.castThriceCreated();

        assertEquals(date3, actual);
    }

    @Test
    public void testCastInvoke() {
        Foo foo = Mockito.mock(Foo.class);
        Date date = Mockito.mock(Date.class);
        Date date2 = date;
        Date date3 = date2;

        when(foo.date()).thenReturn(date);

        Date actual = cast.castInvoke(foo);

        assertSame(date3, actual);
    }

    @Test
    public void testCastLiteral() {
        Foo foo = Mockito.mock(Foo.class);
        String name = "foo";
        String name2 = name;
        String name3 = name2;

        String actual = cast.castLiteral(foo);

        assertEquals(name3, actual);
    }

    @Test
    public void testCastArrayAccess() {
        Object[] names = {"foo"};
        String name = (String) names[0];
        String name2 = name;
        String name3 = name2;

        String actual = cast.castArrayAccess(names);

        assertEquals(name3, actual);
    }
}
