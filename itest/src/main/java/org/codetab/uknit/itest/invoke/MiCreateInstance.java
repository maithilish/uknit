package org.codetab.uknit.itest.invoke;

public class MiCreateInstance {

    // method invocation in instance creation
    public void process(final Pets pets, final Names names) {
        pets.add(new Dog(names.getName()));
    }

}
