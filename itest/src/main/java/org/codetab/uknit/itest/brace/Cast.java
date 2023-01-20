package org.codetab.uknit.itest.brace;

import org.codetab.uknit.itest.brace.Model.Dog;
import org.codetab.uknit.itest.brace.Model.Pets;

class Cast {

    @SuppressWarnings("unused")
    private Dog fDog;

    public Dog declare(final Pets pets) {
        Dog dog = ((Dog) (pets.getPet("foo")));
        return dog;
    }

    @SuppressWarnings("unused")
    public void assign(final Pets pets) {
        Dog dog;
        dog = ((Dog) (pets.getPet(("foo"))));
    }

    public void assignToField(final Pets pets) {
        fDog = ((Dog) (pets.getPet("foo")));
    }

    public void assignToArray(final Pets pets) {
        Dog[] dogs = (new Dog[2]);
        (dogs[0]) = (Dog) pets.getPet(("foo"));
    }

    public void assignToParameterArray(final Pets pets, final Dog[] dogs) {
        (dogs[0]) = (Dog) pets.getPet(("foo"));
    }
}
