package org.codetab.uknit.itest.invoke;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, List<Pet>> apple = new HashMap<>();

        when(pets.getPets()).thenReturn(apple);
        inferredChainCall.infer(pets);
    }

    @Test
    public void testInferAndReturn() {
        Pets pets = Mockito.mock(Pets.class);
        Map<String, List<Pet>> apple = new HashMap<>();
        List<Pet> dogs = new ArrayList<>();
        apple.put("dog", dogs);

        when(pets.getPets()).thenReturn(apple);

        List<Pet> actual = inferredChainCall.inferAndReturn(pets);

        assertSame(dogs, actual);
    }
}
