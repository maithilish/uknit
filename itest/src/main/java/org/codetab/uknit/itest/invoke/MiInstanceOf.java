package org.codetab.uknit.itest.invoke;

import static com.google.common.base.Preconditions.checkState;

import org.codetab.uknit.itest.invoke.Model.Dog;
import org.codetab.uknit.itest.invoke.Model.Pets;
import org.codetab.uknit.itest.invoke.ModelOld.Names;

public class MiInstanceOf {

    // method invocation in instance of
    public void process(final Pets pets, final Names names) {
        checkState(pets.getPet("foo") instanceof Dog, "pet is not Dog");
    }

}