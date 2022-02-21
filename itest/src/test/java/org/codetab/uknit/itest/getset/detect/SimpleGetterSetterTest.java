package org.codetab.uknit.itest.getset.detect;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class SimpleGetterSetterTest {
    @InjectMocks
    private SimpleGetterSetter simpleGetterSetter;

    @Mock
    private Date birthday;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetName() {
        String name = "Foo";
        simpleGetterSetter.setName(name);

        String actual = simpleGetterSetter.getName();

        assertEquals(name, actual);
    }

    @Test
    public void testSetName() {
        String name = "Foo";
        simpleGetterSetter.setName(name);

        Object actual = simpleGetterSetter.getName();

        assertSame(name, actual);
    }

    @Test
    public void testGetBirthday() {

        Date actual = simpleGetterSetter.getBirthday();

        assertSame(birthday, actual);
    }

    @Test
    public void testSetBirthday() {
        Date lbirthday = Mockito.mock(Date.class);
        simpleGetterSetter.setBirthday(lbirthday);

        Object actual = simpleGetterSetter.getBirthday();

        assertSame(lbirthday, actual);
    }
}
