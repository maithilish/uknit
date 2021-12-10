package org.codetab.uknit.itest.cast;

public class CastExpAssignment {

    @SuppressWarnings("unused")
    private Dog fDog;

    @SuppressWarnings("unused")
    public void assign(final Pets pets) {
        Dog dog;
        dog = (Dog) pets.getPet("foo");
    }

    public void assignToField(final Pets pets) {
        fDog = (Dog) pets.getPet("foo");
    }

    // STEPIN - array initialization is not done
    public void assignToArray(final Pets pets) {
        Dog[] dogs = new Dog[2];
        dogs[1] = (Dog) pets.getPet("foo");
    }

    public void assignToParameterArray(final Pets pets, final Dog[] dogs) {
        dogs[1] = (Dog) pets.getPet("foo");
    }
}
