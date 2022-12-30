package org.codetab.uknit.itest.getter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class SimpleGetSetTest {
    @InjectMocks
    private SimpleGetSet simpleGetSet;

    @Mock
    private Date birthday;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetName() {
        String name = "Foo";
        simpleGetSet.setName(name);

        String actual = simpleGetSet.getName();

        assertEquals(name, actual);
    }

    @Test
    public void testSetName() {
        String name = "Foo";
        simpleGetSet.setName(name);

        Object actual = simpleGetSet.getName();

        assertSame(name, actual);
    }

    @Test
    public void testGetBirthday() {

        Date actual = simpleGetSet.getBirthday();

        assertSame(birthday, actual);
    }

    @Test
    public void testSetBirthday() {
        Date birthday = Mockito.mock(Date.class);
        simpleGetSet.setBirthday(birthday);

        Object actual = simpleGetSet.getBirthday();

        assertSame(birthday, actual);
    }
}
