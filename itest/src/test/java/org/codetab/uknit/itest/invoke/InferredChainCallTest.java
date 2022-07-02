package org.codetab.uknit.itest.invoke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codetab.uknit.itest.model.Pet;
import org.codetab.uknit.itest.model.Pets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class InferredChainCallTest {
    @InjectMocks
    private InferredChainCall inferredChainCall;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInfer() {
        Pets pets = Mockito.mock(Pets.class);
        Map<String, List<Pet>> map = new HashMap<>();

        when(pets.getPets()).thenReturn(map);
        inferredChainCall.infer(pets);
    }

    @Test
    public void testInferAndReturn() {
        Pets pets = Mockito.mock(Pets.class);
        Map<String, List<Pet>> map = new HashMap<>();
        List<Pet> dogs = new ArrayList<>();
        map.put("dog", dogs);

        when(pets.getPets()).thenReturn(map);

        List<Pet> actual = inferredChainCall.inferAndReturn(pets);

        assertEquals(dogs, actual);
    }
}
