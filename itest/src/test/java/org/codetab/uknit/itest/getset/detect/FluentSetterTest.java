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

public class FluentSetterTest {
    @InjectMocks
    private FluentSetter fluentSetter;

    @Mock
    private Date birthday;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetName() {
        String name = "Foo";
        fluentSetter.setName(name);

        String actual = fluentSetter.getName();

        assertEquals(name, actual);
    }

    @Test
    public void testSetName() {
        String name = "Foo";

        FluentSetter actual = fluentSetter.setName(name);

        assertEquals(fluentSetter, actual);
    }

    @Test
    public void testGetBirthday() {

        Date actual = fluentSetter.getBirthday();

        assertSame(birthday, actual);
    }

    @Test
    public void testSetBirthday() {
        Date lbirthday = Mockito.mock(Date.class);

        FluentSetter actual = fluentSetter.setBirthday(lbirthday);

        assertEquals(fluentSetter, actual);
    }
}
