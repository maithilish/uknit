package org.codetab.uknit.itest.brace.linked;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.brace.linked.Model.Dog;
import org.codetab.uknit.itest.brace.linked.Model.Pet;
import org.codetab.uknit.itest.brace.linked.Model.Pitbull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class CastHierarchyTest {
    @InjectMocks
    private CastHierarchy castHierarchy;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHigerLast() {
        Dog obj = Mockito.mock(Dog.class);
        Pet pet = (obj);
        String apple = "Foo";
        Dog dog = (obj);
        String grape = "Bar";

        when(pet.sex()).thenReturn(apple);
        when(dog.breed()).thenReturn(grape);

        String actual = castHierarchy.higerLast(obj);

        assertEquals(grape, actual);
    }

    @Test
    public void testLowerLast() {
        Dog obj = Mockito.mock(Dog.class);
        Dog dog = (obj);
        String apple = "Foo";
        Pet pet = (obj);
        String grape = "Bar";

        when(dog.breed()).thenReturn(apple);
        when(pet.sex()).thenReturn(grape);

        String actual = castHierarchy.lowerLast(obj);

        assertEquals(grape, actual);
    }

    @Test
    public void testHigherMiddle() {
        Pitbull obj = Mockito.mock(Pitbull.class);
        Dog dog = (obj);
        String apple = "Foo";
        Pitbull pitbull = (obj);
        String grape = "Bar";
        Pet pet = (obj);
        String orange = "Baz";

        when(dog.breed()).thenReturn(apple);
        when(pitbull.name()).thenReturn(grape);
        when(pet.sex()).thenReturn(orange);

        String actual = castHierarchy.higherMiddle(obj);

        assertEquals(orange, actual);
    }

    @Test
    public void testHigherLast() {
        Pitbull obj = Mockito.mock(Pitbull.class);
        Dog dog = (obj);
        String apple = "Foo";
        Pet pet = (obj);
        String grape = "Bar";
        Pitbull pitbull = (obj);
        String orange = "Baz";

        when(dog.breed()).thenReturn(apple);
        when(pet.sex()).thenReturn(grape);
        when(pitbull.name()).thenReturn(orange);

        String actual = castHierarchy.higherLast(obj);

        assertEquals(orange, actual);
    }

    @Test
    public void testHigherFirst() {
        Pitbull obj = Mockito.mock(Pitbull.class);
        Pitbull pitbull = (obj);
        String apple = "Foo";
        Pet pet = (obj);
        String grape = "Bar";
        Dog dog = (obj);
        String orange = "Baz";

        when(pitbull.name()).thenReturn(apple);
        when(pet.sex()).thenReturn(grape);
        when(dog.breed()).thenReturn(orange);

        String actual = castHierarchy.higherFirst(obj);

        assertEquals(orange, actual);
    }
}
