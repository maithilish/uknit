package org.codetab.uknit.itest.invoke;

import org.codetab.uknit.itest.invoke.Model.Dog;
import org.codetab.uknit.itest.invoke.Model.Pets;
import org.codetab.uknit.itest.invoke.ModelOld.Names;

class MiCreateInstance {

    // method invocation in instance creation
    public void process(final Pets pets, final Names names) {
        pets.add(new Dog(names.getName()));
    }

}
