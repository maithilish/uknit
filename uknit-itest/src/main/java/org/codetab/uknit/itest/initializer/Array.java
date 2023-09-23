package org.codetab.uknit.itest.initializer;

import org.codetab.uknit.itest.initializer.Model.Foo;

public class Array {

    public void useArrayAccessInWhen(final Foo foo) {
        String[] cities = {"foo", "bar"};
        foo.format(cities[1]);
    }

    public void useArrayAccessInVerify(final Foo foo) {
        String[] cities = {"foo", "bar"};
        foo.append(cities[0]);
    }
}
