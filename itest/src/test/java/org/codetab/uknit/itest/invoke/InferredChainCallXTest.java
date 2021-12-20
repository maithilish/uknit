package org.codetab.uknit.itest.invoke;

import static org.junit.jupiter.api.Assertions.assertSame;
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

public class InferredChainCallXTest {
    @InjectMocks
    private InferredChainCall inferredChainCall;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInfer() {
        List<Pet> dogs = new ArrayList<>();
        Map<String, List<Pet>> apple = new HashMap<>();
        apple.put("dog", dogs);
        Pets pets = Mockito.mock(Pets.class);

        when(pets.getPets()).thenReturn(apple);
        // when(apple.get("dog")).thenReturn(dogs);
        inferredChainCall.infer(pets);
    }

    @Test
    public void testInferAndReturn() {
        List<Pet> dogs = new ArrayList<>();
        Map<String, List<Pet>> apple = new HashMap<>();
        apple.put("dog", dogs);
        Pets pets = Mockito.mock(Pets.class);

        when(pets.getPets()).thenReturn(apple);
        // when(apple.get("dog")).thenReturn(dogs);

        List<Pet> actual = inferredChainCall.inferAndReturn(pets);

        assertSame(dogs, actual);
    }
}
