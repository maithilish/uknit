package org.codetab.uknit.itest.reassign;

import org.codetab.uknit.itest.reassign.Model.Bar;
import org.codetab.uknit.itest.reassign.Model.Foo;

class Field {

    private String city, town = "xyz";
    private Bar bar;

    String reassignReal(final Foo foo) {
        city = "a";
        foo.append(city);
        city = foo.format(city);
        city = "b";
        foo.append(city);
        city = foo.format(city);
        foo.append(city);
        return city;
    }

    String reassignRealInitialized(final Foo foo) {
        town = "aaa";
        foo.append(town);
        town = foo.format(town);
        town = "bbb";
        foo.append(town);
        town = foo.format(town);
        foo.append(town);
        return town;
    }

    String reassignRealHide(final Foo foo) {
        String city = "a";
        foo.append(city);
        city = foo.format(city);
        city = "b";
        foo.append(city);
        city = foo.format(city);
        foo.append(city);
        return city;
    }

    Bar reassignMock(final Foo foo) {
        bar.append(foo);
        bar = bar.format(foo);
        bar.append(foo);
        bar = bar.format(foo);
        bar.append(foo);
        return bar;
    }

    Bar reassignMock2() {
        bar.append(bar);
        bar = bar.format(bar);
        bar.append(bar);
        bar = bar.format(bar);
        bar.append(bar);
        return bar;
    }

    Bar reassignMockHide(final Foo foo, Bar bar) {
        bar.append(foo);
        bar = bar.format(foo);
        bar.append(foo);
        bar = bar.format(foo);
        bar.append(foo);
        return bar;
    }

}
