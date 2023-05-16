package org.codetab.uknit.itest.verify.times;

import org.codetab.uknit.itest.verify.times.Model.Foo;

class ArrayAccess {

    // array access in verify arg
    public void arrayAccess(final Foo foo) {
        String[] cities = {"x", "y"};
        int[] indexes = {0, 1};
        foo.append(cities[indexes[0]]);
        foo.append(cities[indexes[1]]);
    }

}
