package org.codetab.uknit.itest.imc.reassign;

import org.codetab.uknit.itest.imc.reassign.Model.Bar;
import org.codetab.uknit.itest.imc.reassign.Model.Foo;

class Parameter {

    @SuppressWarnings("unused")
    private String city, town = "xyz";
    @SuppressWarnings("unused")
    private Bar bar;

    String reassignReal(final Foo foo, String city) {
        city = reassignRealImc(foo, city);
        foo.append(city);
        return city;
    }

    private String reassignRealImc(final Foo foo, String city) {
        city = "a";
        foo.append(city);
        city = foo.format(city);
        city = "b";
        foo.append(city);
        city = foo.format(city);
        return city;
    }

    String reassignRealInitialized(final Foo foo, String town) {
        town = reassignRealInitializedImc(foo, town);
        foo.append(town);
        return town;
    }

    private String reassignRealInitializedImc(final Foo foo, String town) {
        town = "aaa";
        foo.append(town);
        town = foo.format(town);
        town = "bbb";
        foo.append(town);
        town = foo.format(town);
        return town;
    }

    Bar reassignMock(final Foo foo, Bar bar) {
        bar = reassignMockImc(foo, bar);
        bar.append(foo);
        return bar;
    }

    private Bar reassignMockImc(final Foo foo, Bar bar) {
        bar.append(foo);
        bar = bar.format(foo);
        bar.append(foo);
        bar = bar.format(foo);
        return bar;
    }

    Bar reassignMock2(Bar bar) {
        bar = reassignMock2Imc(bar);
        bar.append(bar);
        return bar;
    }

    private Bar reassignMock2Imc(Bar bar) {
        bar.append(bar);
        bar = bar.format(bar);
        bar.append(bar);
        bar = bar.format(bar);
        return bar;
    }
}
