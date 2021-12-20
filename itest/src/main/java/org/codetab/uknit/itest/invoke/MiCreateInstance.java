package org.codetab.uknit.itest.invoke;

import org.codetab.uknit.itest.model.Dog;
import org.codetab.uknit.itest.model.Names;
import org.codetab.uknit.itest.model.Pets;

public class MiCreateInstance {

    // method invocation in instance creation
    public void process(final Pets pets, final Names names) {
        pets.add(new Dog(names.getName()));
    }

}
