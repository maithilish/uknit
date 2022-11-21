package org.codetab.uknit.itest.cast;

import org.codetab.uknit.itest.cast.Model.Dog;
import org.codetab.uknit.itest.cast.Model.Pets;

public class Cast {

    @SuppressWarnings("unused")
    private Dog fDog;

    @SuppressWarnings("unused")
    public void declare(final Pets pets) {
        Dog dog = (Dog) pets.getPet("foo");
    }

    @SuppressWarnings("unused")
    public void assign(final Pets pets) {
        Dog dog;
        dog = (Dog) pets.getPet("foo");
    }

    public void assignToField(final Pets pets) {
        fDog = (Dog) pets.getPet("foo");
    }

    public void assignToArray(final Pets pets) {
        Dog[] dogs = new Dog[2];
        dogs[0] = (Dog) pets.getPet("foo");
    }

    public void assignToParameterArray(final Pets pets, final Dog[] dogs) {
        dogs[0] = (Dog) pets.getPet("foo");
    }
}
