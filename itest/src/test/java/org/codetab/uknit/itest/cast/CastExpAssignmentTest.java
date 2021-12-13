package org.codetab.uknit.itest.cast;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class CastExpAssignmentTest {
    @InjectMocks
    private CastExpAssignment castExpAssignment;

    @Mock
    private Dog fDog;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAssign() {
        Pets pets = Mockito.mock(Pets.class);
        Dog dog = Mockito.mock(Dog.class);

        when(pets.getPet("foo")).thenReturn(dog);
        castExpAssignment.assign(pets);
    }

    @Test
    public void testAssignToField() {
        Pets pets = Mockito.mock(Pets.class);
        // CHECKSTYLE:OFF
        Dog fDog = Mockito.mock(Dog.class);
        // CHECKSTYLE:ON

        when(pets.getPet("foo")).thenReturn(fDog);
        castExpAssignment.assignToField(pets);
    }

    @Test
    public void testAssignToArray() {
        Pets pets = Mockito.mock(Pets.class);
        Dog grape = Mockito.mock(Dog.class);

        when(pets.getPet("foo")).thenReturn(grape);
        castExpAssignment.assignToArray(pets);
    }

    @Test
    public void testAssignToParameterArray() {
        Pets pets = Mockito.mock(Pets.class);
        Dog[] dogs = new Dog[2];
        Dog grape = Mockito.mock(Dog.class);

        when(pets.getPet("foo")).thenReturn(grape);
        castExpAssignment.assignToParameterArray(pets, dogs);
    }
}
