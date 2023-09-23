package org.codetab.uknit.itest.invoke;

import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.invoke.Model.Pet;
import org.codetab.uknit.itest.invoke.Model.Pets;
import org.codetab.uknit.itest.invoke.ModelOld.Names;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class MiInstanceOfTest {
    @InjectMocks
    private MiInstanceOf miInstanceOf;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess() {
        Pets pets = Mockito.mock(Pets.class);
        Names names = Mockito.mock(Names.class);
        Pet pet = Mockito.mock(Pet.class);

        when(pets.getPet("foo")).thenReturn(pet);
        miInstanceOf.process(pets, names);

        // fail("unable to assert, STEPIN");
    }
}
