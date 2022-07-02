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
        Person p = Mockito.mock(Person.class);
        Sex sex = Person.Sex.MALE;
        int grape = 18;
        int mango = 25;

        when(p.getGender()).thenReturn(sex);
        when(p.getAge()).thenReturn(grape).thenReturn(mango);

        boolean actual = checkPerson.check(p);

        assertTrue(actual);
    }
}
