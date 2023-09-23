package org.codetab.uknit.itest.internal;

import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.internal.Model.Dog;
import org.codetab.uknit.itest.internal.Model.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class ParamCastTest {
    @InjectMocks
    private ParamCast paramCast;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testParamConflict() {
        Dog pet = Mockito.mock(Dog.class);
        String apple = "Foo";

        when(pet.breed()).thenReturn(apple);
        paramCast.paramConflict(pet);

        // fail("unable to assert, STEPIN");
    }

    // can't test ClassCastException
    // @Test
    // public void testParamConflictIWithllegalCast() {
    // Pet pet = Mockito.mock(Pet.class);
    // String apple = "Foo";
    //
    // when(((Foo) pet).lang()).thenReturn(apple);
    // paramCast.paramConflictIWithllegalCast(pet);
    //
    // fail("unable to assert, STEPIN");
    // }

    @Test
    public void testParamConflictNested() {
        Dog pet = Mockito.mock(Dog.class);
        String apple = "Foo";

        when(pet.breed()).thenReturn(apple);
        paramCast.paramConflictNested(pet);

        // fail("unable to assert, STEPIN");
    }

    // can't test ClassCastException
    // @Test
    // public void testParamConflictNestedWithllegalCast() {
    // Pet pet = Mockito.mock(Pet.class);
    // String apple = "Foo";
    //
    // when(((Foo) pet).lang()).thenReturn(apple);
    // paramCast.paramConflictNestedWithllegalCast(pet);
    //
    // fail("unable to assert, STEPIN");
    // }

    @Test
    public void testParamConflictVarargsNested() {
        Dog dog = Mockito.mock(Dog.class);
        Pet[] pets = {dog};
        Dog pets2 = (Dog) pets[0];
        String apple = "Foo";

        when(pets2.breed()).thenReturn(apple);
        paramCast.paramConflictVarargsNested(pets);

        // fail("unable to assert, STEPIN");
    }

    // can't test ClassCastException
    // @Test
    // public void testParamConflictVarargsNestedWithllegalCast() {
    // Pet[] pets = {};
    // Pet pets2 = pets;
    // String apple = "Foo";
    //
    // when(pets2.lang()).thenReturn(apple);
    // paramCast.paramConflictVarargsNestedWithllegalCast(pets);
    //
    // fail("unable to assert, STEPIN");
    // }
}
