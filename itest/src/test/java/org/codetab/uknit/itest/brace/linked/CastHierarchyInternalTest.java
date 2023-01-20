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

class CastHierarchyInternalTest {
    @InjectMocks
    private CastHierarchyInternal castHierarchyInternal;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHigerLast() {
        Dog obj = Mockito.mock(Dog.class);
        Pet pet2 = (Pet) (obj);
        Pet pet = pet2;
        String apple = "Foo";
        Dog dog2 = (Dog) (obj);
        Dog dog = dog2;
        String grape = "Bar";

        when((pet).sex()).thenReturn(apple);
        when((dog).breed()).thenReturn(grape);

        String actual = castHierarchyInternal.higerLast(obj);

        assertEquals(grape, actual);
    }

    @Test
    public void testLowerLast() {
        Dog obj = Mockito.mock(Dog.class);
        Dog dog2 = (Dog) (obj);
        Dog dog = dog2;
        String apple = "Foo";
        Pet pet2 = (Pet) (obj);
        Pet pet = pet2;
        String grape = "Bar";

        when((dog).breed()).thenReturn(apple);
        when((pet).sex()).thenReturn(grape);

        String actual = castHierarchyInternal.lowerLast(obj);

        assertEquals(grape, actual);
    }

    @Test
    public void testHigherMiddle() {
        Pitbull obj = Mockito.mock(Pitbull.class);
        Dog dog2 = (Dog) (obj);
        Dog dog = dog2;
        String apple = "Foo";
        Pitbull pitbull2 = (Pitbull) (obj);
        Pitbull pitbull = pitbull2;
        String grape = "Bar";
        Pet pet2 = (Pet) (obj);
        Pet pet = pet2;
        String orange = "Baz";

        when((dog).breed()).thenReturn(apple);
        when((pitbull).name()).thenReturn(grape);
        when((pet).sex()).thenReturn(orange);

        String actual = castHierarchyInternal.higherMiddle(obj);

        assertEquals(orange, actual);
    }

    @Test
    public void testHigherLast() {
        Pitbull obj = Mockito.mock(Pitbull.class);
        Dog dog2 = (Dog) (obj);
        Dog dog = dog2;
        String apple = "Foo";
        Pet pet2 = (Pet) (obj);
        Pet pet = pet2;
        String grape = "Bar";
        Pitbull pitbull2 = (Pitbull) (obj);
        Pitbull pitbull = pitbull2;
        String orange = "Baz";

        when((dog).breed()).thenReturn(apple);
        when((pet).sex()).thenReturn(grape);
        when((pitbull).name()).thenReturn(orange);

        String actual = castHierarchyInternal.higherLast(obj);

        assertEquals(orange, actual);
    }

    @Test
    public void testHigherFirst() {
        Pitbull obj = Mockito.mock(Pitbull.class);
        Pitbull pitbull2 = (Pitbull) (obj);
        Pitbull pitbull = pitbull2;
        String apple = "Foo";
        Pet pet2 = (Pet) (obj);
        Pet pet = pet2;
        String grape = "Bar";
        Dog dog2 = (Dog) (obj);
        Dog dog = dog2;
        String orange = "Baz";

        when((pitbull).name()).thenReturn(apple);
        when((pet).sex()).thenReturn(grape);
        when((dog).breed()).thenReturn(orange);

        String actual = castHierarchyInternal.higherFirst(obj);

        assertEquals(orange, actual);
    }

    @Test
    public void testHigerLastMix() {
        Dog obj = Mockito.mock(Dog.class);
        Pet pet = (Pet) (obj);
        String apple = "Foo";
        Dog dog2 = (Dog) (obj);
        Dog dog = dog2;
        String grape = "Bar";

        when((pet).sex()).thenReturn(apple);
        when((dog).breed()).thenReturn(grape);

        String actual = castHierarchyInternal.higerLastMix(obj);

        assertEquals(grape, actual);
    }

    @Test
    public void testLowerLastMix() {
        Dog obj = Mockito.mock(Dog.class);
        Dog dog = (Dog) (obj);
        String apple = "Foo";
        Pet pet2 = (Pet) (obj);
        Pet pet = pet2;
        String grape = "Bar";

        when((dog).breed()).thenReturn(apple);
        when((pet).sex()).thenReturn(grape);

        String actual = castHierarchyInternal.lowerLastMix(obj);

        assertEquals(grape, actual);
    }

    @Test
    public void testHigherMiddleMix() {
        Pitbull obj = Mockito.mock(Pitbull.class);
        Dog dog2 = (Dog) (obj);
        Dog dog = dog2;
        String apple = "Foo";
        Pitbull pitbull = (Pitbull) obj;
        String grape = "Bar";
        Pet pet2 = (Pet) (obj);
        Pet pet = pet2;
        String orange = "Baz";

        when((dog).breed()).thenReturn(apple);
        when((pitbull).name()).thenReturn(grape);
        when((pet).sex()).thenReturn(orange);

        String actual = castHierarchyInternal.higherMiddleMix(obj);

        assertEquals(orange, actual);
    }

    @Test
    public void testHigherLastMix() {
        Pitbull obj = Mockito.mock(Pitbull.class);
        Dog dog = (Dog) (obj);
        String apple = "Foo";
        Pet pet = (Pet) (obj);
        String grape = "Bar";
        Pitbull pitbull2 = (Pitbull) (obj);
        Pitbull pitbull = pitbull2;
        String orange = "Baz";

        when((dog).breed()).thenReturn(apple);
        when((pet).sex()).thenReturn(grape);
        when((pitbull).name()).thenReturn(orange);

        String actual = castHierarchyInternal.higherLastMix(obj);

        assertEquals(orange, actual);
    }

    @Test
    public void testHigherFirstMix() {
        Pitbull obj = Mockito.mock(Pitbull.class);
        Pitbull pitbull = (Pitbull) obj;
        String apple = "Foo";
        Pet pet = (Pet) obj;
        String grape = "Bar";
        Dog dog2 = (Dog) (obj);
        Dog dog = dog2;
        String orange = "Baz";

        when(pitbull.name()).thenReturn(apple);
        when(pet.sex()).thenReturn(grape);
        when((dog).breed()).thenReturn(orange);

        String actual = castHierarchyInternal.higherFirstMix(obj);

        assertEquals(orange, actual);
    }
}
