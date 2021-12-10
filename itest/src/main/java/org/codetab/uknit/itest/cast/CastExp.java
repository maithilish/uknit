package org.codetab.uknit.itest.cast;

public class CastExp {

    @SuppressWarnings("unused")
    public void declare(final Pets pets) {
        Dog dog = (Dog) pets.getPet("foo");
    }
}
