package org.codetab.uknit.itest.verify.times;

import org.codetab.uknit.itest.verify.times.Model.Foo;

class ArrayInitializer {

    // arg is initializer
    public void literal(final Foo foo) {

        String[] cities = {"foo", "bar"};
        String[] states = {"foo", "bar"};
        String[] countries = {"bar", "baz"};

        foo.append(cities);
        foo.append(states);
        foo.append(countries);
    }
}
