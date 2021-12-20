package org.codetab.uknit.itest.invoke;

import java.util.List;

import org.codetab.uknit.itest.model.Pet;
import org.codetab.uknit.itest.model.Pets;

public class InferredChainCall {

    // infer var in chain call
    public void infer(final Pets pets) {
        @SuppressWarnings("unused")
        List<Pet> dogs = pets.getPets().get("dog");
    }

    // STEPIN - put entry to map
    public List<Pet> inferAndReturn(final Pets pets) {
        List<Pet> dogs = pets.getPets().get("dog");
        return dogs;
    }
}
