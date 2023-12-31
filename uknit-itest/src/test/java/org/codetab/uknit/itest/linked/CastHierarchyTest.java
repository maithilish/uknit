package org.codetab.uknit.itest.linked;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.linked.Model.Dog;
import org.codetab.uknit.itest.linked.Model.Pet;
import org.codetab.uknit.itest.linked.Model.Pitbull;
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
        Pet pet = (Pet) obj;
        String apple = "Foo";
        Dog dog = (Dog) obj;
        String grape = "Bar";

        when(pet.sex()).thenReturn(apple);
        when(dog.breed()).thenReturn(grape);

        String actual = castHierarchy.higerLast(obj);

        assertEquals(grape, actual);
    }

    @Test
    public void testLowerLast() {
        Dog obj = Mockito.mock(Dog.class);
        Dog dog = (Dog) obj;
        String apple = "Foo";
        Pet pet = (Pet) obj;
        String grape = "Bar";

        when(dog.breed()).thenReturn(apple);
        when(pet.sex()).thenReturn(grape);

        String actual = castHierarchy.lowerLast(obj);

        assertEquals(grape, actual);
    }

    @Test
    public void testHigherMiddle() {
        Pitbull obj = Mockito.mock(Pitbull.class);
        Dog dog = (Dog) obj;
        String apple = "Foo";
        Pitbull pitbull = (Pitbull) obj;
        String grape = "Bar";
        Pet pet = (Pet) obj;
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
        Dog dog = (Dog) obj;
        String apple = "Foo";
        Pet pet = (Pet) obj;
        String grape = "Bar";
        Pitbull pitbull = (Pitbull) obj;
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
        Pitbull pitbull = (Pitbull) obj;
        String apple = "Foo";
        Pet pet = (Pet) obj;
        String grape = "Bar";
        Dog dog = (Dog) obj;
        String orange = "Baz";

        when(pitbull.name()).thenReturn(apple);
        when(pet.sex()).thenReturn(grape);
        when(dog.breed()).thenReturn(orange);

        String actual = castHierarchy.higherFirst(obj);

        assertEquals(orange, actual);
    }
}
