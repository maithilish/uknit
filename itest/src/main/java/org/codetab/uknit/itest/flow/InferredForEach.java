package org.codetab.uknit.itest.flow;

import java.util.Map;

import org.codetab.uknit.itest.model.Pet;

public class InferredForEach {

    @SuppressWarnings("unused")
    public void process(final Map<String, Pet> pets) {
        for (String key : pets.keySet()) {
            String dummy = key;
        }
    }

}
