package org.codetab.uknit.itest.interfaces;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.model.Person;
import org.codetab.uknit.itest.model.Person.Sex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class CheckPersonTest {
    @InjectMocks
    private CheckPerson checkPerson;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCheck() {
        Sex apple = Person.Sex.MALE;
        int grape = 18;
        int orange = 25;
        Person p = Mockito.mock(Person.class);

        when(p.getGender()).thenReturn(apple);
        when(p.getAge()).thenReturn(grape).thenReturn(orange);

        boolean actual = checkPerson.check(p);

        assertTrue(actual);
    }
}
