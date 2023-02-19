package org.codetab.uknit.itest.reassign;

import org.codetab.uknit.itest.reassign.Model.Bar;
import org.codetab.uknit.itest.reassign.Model.Foo;

class Parameter {

    @SuppressWarnings("unused")
    private String city, town = "xyz";
    @SuppressWarnings("unused")
    private Bar bar;

    String reassignReal(final Foo foo, String city) {
        city = "a";
        foo.append(city);
        city = foo.format(city);
        city = "b";
        foo.append(city);
        city = foo.format(city);
        foo.append(city);
        return city;
    }

    String reassignRealInitialized(final Foo foo, String town) {
        town = "aaa";
        foo.append(town);
        town = foo.format(town);
        town = "bbb";
        foo.append(town);
        town = foo.format(town);
        foo.append(town);
        return town;
    }

    Bar reassignMock(final Foo foo, Bar bar) {
        bar.append(foo);
        bar = bar.format(foo);
        bar.append(foo);
        bar = bar.format(foo);
        bar.append(foo);
        return bar;
    }

    Bar reassignMock2(Bar bar) {
        bar.append(bar);
        bar = bar.format(bar);
        bar.append(bar);
        bar = bar.format(bar);
        bar.append(bar);
        return bar;
    }
}
