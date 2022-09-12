package org.codetab.uknit.itest.flow.nosplit;

import java.util.HashMap;
import java.util.Map;

import org.codetab.uknit.itest.model.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class InferredForEachTest {
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
