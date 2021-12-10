package org.codetab.uknit.itest.flow;

import java.util.Map;

public class InferredForEach {

    @SuppressWarnings("unused")
    public void process(final Map<String, Pet> pets) {
        for (String key : pets.keySet()) {
        }
    }

}
