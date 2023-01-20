package org.codetab.uknit.itest.cast;

import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.cast.Model.Dog;
import org.codetab.uknit.itest.cast.Model.Pets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class CastTest {
    @InjectMocks
    private Cast cast;

    @Mock
    private Dog fDog;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDeclare() {
        Pets pets = Mockito.mock(Pets.class);
        Dog dog2 = Mockito.mock(Dog.class);

        when(pets.getPet("foo")).thenReturn(dog2);
        cast.declare(pets);
    }

    @Test
    public void testAssign() {
        Pets pets = Mockito.mock(Pets.class);
        Dog dog2 = Mockito.mock(Dog.class);

        when(pets.getPet("foo")).thenReturn(dog2);
        cast.assign(pets);
    }

    @Test
    public void testAssignToField() {
        Pets pets = Mockito.mock(Pets.class);
        Dog dog = Mockito.mock(Dog.class);

        when(pets.getPet("foo")).thenReturn(dog);
        cast.assignToField(pets);
    }

    @Test
    public void testAssignToArray() {
        Pets pets = Mockito.mock(Pets.class);
        Dog dog = Mockito.mock(Dog.class);

        when(pets.getPet("foo")).thenReturn(dog);
        cast.assignToArray(pets);
    }

    @Test
    public void testAssignToParameterArray() {
        Pets pets = Mockito.mock(Pets.class);

        Dog dog = Mockito.mock(Dog.class);
        Dog[] dogs = {dog};

        when(pets.getPet("foo")).thenReturn(dog);
        cast.assignToParameterArray(pets, dogs);
    }
}
