package org.codetab.uknit.itest.getset.detect;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ModifingSetterTest {
    @InjectMocks
    private ModifingSetter modifingSetter;

    @Mock
    private Date birthday;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetName() {
        String name = "Foo";
        String expected = "FooBar";
        modifingSetter.setName(name);

        String actual = modifingSetter.getName();

        assertEquals(expected, actual);
    }

    @Test
    public void testSetName() {
        String name = "Foo";

        String actual = modifingSetter.setName(name);

        String expected = "FooBar";
        assertEquals(expected, actual);
    }

    @Test
    public void testGetBirthday() {

        Date actual = modifingSetter.getBirthday();

        assertSame(birthday, actual);
    }

    @Test
    public void testSetBirthday() {
        Date lbirthday = Mockito.mock(Date.class);
        long apple = 1L;

        when(lbirthday.getTime()).thenReturn(apple);

        long actual = modifingSetter.setBirthday(lbirthday);

        assertEquals(apple, actual);
        verify(lbirthday).setTime(100);
    }
}
