package org.codetab.uknit.itest.interfaces;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.interfaces.Model.Person;
import org.codetab.uknit.itest.interfaces.Model.Person.Sex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class InterfacesTest {
    @InjectMocks
    private Interfaces interfaces;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCheck() {
        Person p = Mockito.mock(Person.class);
        Sex sex = Sex.FEMALE;
        int apple = 18;

        when(p.getGender()).thenReturn(sex);
        when(p.getAge()).thenReturn(apple);

        boolean actual = interfaces.check(p);

        assertTrue(actual);
    }
}
