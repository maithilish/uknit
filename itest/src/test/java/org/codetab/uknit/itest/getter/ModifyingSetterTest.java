package org.codetab.uknit.itest.getter;

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

class ModifyingSetterTest {
    @InjectMocks
    private ModifyingSetter modifyingSetter;

    @Mock
    private Date birthday;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetName() {
        String name = "FooBar";
        modifyingSetter.setName("Foo");

        String actual = modifyingSetter.getName();

        assertEquals(name, actual);
    }

    @Test
    public void testSetName() {
        String name = "Bar";
        String apple = name + "Bar";

        String actual = modifyingSetter.setName(name);

        assertEquals(apple, actual);
    }

    @Test
    public void testGetBirthday() {

        Date actual = modifyingSetter.getBirthday();

        assertSame(birthday, actual);
    }

    @Test
    public void testSetBirthday() {
        Date birthday = Mockito.mock(Date.class);
        long apple = 1L;

        when(birthday.getTime()).thenReturn(apple);

        long actual = modifyingSetter.setBirthday(birthday);

        assertEquals(apple, actual);
        verify(birthday).setTime(100);
    }
}
