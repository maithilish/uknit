package org.codetab.uknit.itest.flow.simple;

import java.util.HashMap;
import java.util.Map;

import org.codetab.uknit.itest.flow.simple.Model.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class InferredForEachTest {
    @InjectMocks
    private InferredForEach inferredForEach;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess() {
        Map<String, Pet> pets = new HashMap<>();
        String key = "Foo";
        Pet apple = Mockito.mock(Pet.class);
        pets.put(key, apple);
        inferredForEach.process(pets);

        // fail("unable to assert, STEPIN");
    }
}
