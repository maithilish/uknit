package org.codetab.uknit.itest.flow.nosplit;

import java.util.HashMap;
import java.util.Map;

import org.codetab.uknit.itest.model.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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
        String key = "foo";
        Map<String, Pet> pets = new HashMap<>();
        // STEPIN - wire map put
        pets.put(key, null);
        inferredForEach.process(pets);
    }
}
