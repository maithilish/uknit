package org.codetab.uknit.itest.cast;

import org.codetab.uknit.itest.model.Dog;
import org.codetab.uknit.itest.model.Pets;

public class CastExp {

    @SuppressWarnings("unused")
    public void declare(final Pets pets) {
        Dog dog = (Dog) pets.getPet("foo");
    }
}
