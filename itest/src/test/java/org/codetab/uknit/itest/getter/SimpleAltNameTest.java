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

public class SimpleAltNameTest {
    @InjectMocks
    private SimpleAltName simpleAltName;

    @Mock
    private Date birthday;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetName() {
        String name = "Foo";
        simpleAltName.setName(name);

        String actual = simpleAltName.getName();

        assertEquals(name, actual);
    }

    @Test
    public void testSetName() {
        String altName = "Foo";
        simpleAltName.setName(altName);

        Object actual = simpleAltName.getName();

        assertSame(altName, actual);
    }

    @Test
    public void testGetBirthday() {

        Date actual = simpleAltName.getBirthday();

        assertSame(birthday, actual);
    }

    @Test
    public void testSetBirthday() {
        Date altBirthday = Mockito.mock(Date.class);
        simpleAltName.setBirthday(altBirthday);

        Object actual = simpleAltName.getBirthday();

        assertSame(altBirthday, actual);
    }
}
