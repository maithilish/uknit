package org.codetab.uknit.itest.flow.simple;

import java.util.Map;

import org.codetab.uknit.itest.flow.simple.Model.Pet;

class InferredForEach {

    @SuppressWarnings("unused")
    public void process(final Map<String, Pet> pets) {
        for (String key : pets.keySet()) {
            String dummy = key;
        }
    }

}
