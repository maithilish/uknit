package org.codetab.uknit.itest.infix;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.File;

import org.codetab.uknit.itest.infix.Model.Person;
import org.codetab.uknit.itest.infix.Model.Person.Sex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class SimpleInfixTest {
    @InjectMocks
    private SimpleInfix simpleInfix;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAssignInfixLeft() {
        File file = Mockito.mock(File.class);
        File other = Mockito.mock(File.class);
        int apple = 1;

        when(file.compareTo(other)).thenReturn(apple);

        boolean actual = simpleInfix.assignInfixLeft(file, other);

        assertTrue(actual);
    }

    @Test
    public void testReturnInfixLeft() {
        File file = Mockito.mock(File.class);
        File other = Mockito.mock(File.class);
        int apple = 1;

        when(file.compareTo(other)).thenReturn(apple);

        boolean actual = simpleInfix.returnInfixLeft(file, other);

        assertTrue(actual);
    }

    @Test
    public void testAssignInfixRight() {
        File file = Mockito.mock(File.class);
        File other = Mockito.mock(File.class);
        int apple = 1;

        when(file.compareTo(other)).thenReturn(apple);

        boolean actual = simpleInfix.assignInfixRight(file, other);

        assertTrue(actual);
    }

    @Test
    public void testReturnInfixRight() {
        File file = Mockito.mock(File.class);
        File other = Mockito.mock(File.class);
        int apple = 1;

        when(file.compareTo(other)).thenReturn(apple);

        boolean actual = simpleInfix.returnInfixRight(file, other);

        assertTrue(actual);
    }

    @Test
    public void testAssignInfixExtended() {
        int a = 1;
        int b = 1;
        int c = 1;
        int d = 1;
        int answer = a + b + c + d;

        int actual = simpleInfix.assignInfixExtended(a, b, c, d);

        assertEquals(answer, actual);
    }

    @Test
    public void testReturnInfixExtended() {
        int a = 1;
        int b = 1;
        int c = 1;
        int d = 1;
        int apple = a + b + c + d;

        int actual = simpleInfix.returnInfixExtended(a, b, c, d);

        assertEquals(apple, actual);
    }

    @Test
    public void testAssignComparision() {
        Person p = Mockito.mock(Person.class);
        Sex sex = Sex.FEMALE;
        int apple = 0;
        int grape = 1;
        int orange = 1;

        when(p.getGender()).thenReturn(sex);
        when(p.getAge()).thenReturn(apple).thenReturn(grape).thenReturn(orange);

        boolean actual = simpleInfix.assignComparision(p);

        assertFalse(actual);
    }

    @Test
    public void testReturnComparition() {
        Person p = Mockito.mock(Person.class);
        Sex sex = Sex.FEMALE;
        int apple = 0;
        int grape = 1;
        int orange = 1;

        when(p.getGender()).thenReturn(sex);
        when(p.getAge()).thenReturn(apple).thenReturn(grape).thenReturn(orange);

        boolean actual = simpleInfix.returnComparition(p);

        assertFalse(actual);
    }
}
