package org.codetab.uknit.itest.imc.reassign;

import org.codetab.uknit.itest.imc.reassign.Model.Bar;
import org.codetab.uknit.itest.imc.reassign.Model.Foo;

class Field {

    private String city, town = "xyz";
    private Bar bar;
    private Foo foo;

    String reassignRealSimple() {
        city = reassignRealSimpleImc();
        return city;
    }

    private String reassignRealSimpleImc() {
        city = foo.cntry();
        return city;
    }

    String reassignReal(final Foo foo) {
        city = reassignRealImc(foo);
        foo.append(city);
        return city;
    }

    private String reassignRealImc(final Foo foo) {
        city = "a";
        foo.append(city);
        city = foo.format(city);
        city = "b";
        foo.append(city);
        return foo.format(city);
    }

    String reassignRealInitialized(final Foo foo) {
        town = "aaa";
        foo.append(town);
        town = reassignRealInitializedImc(foo);
        foo.append(town);
        return town;
    }

    private String reassignRealInitializedImc(final Foo foo) {
        town = "bbb";
        foo.append(town);
        town = foo.format(town);
        town = "ccc";
        foo.append(town);
        return foo.format(town);
    }

    String reassignRealHide(final Foo foo) {
        String city = reassignRealHideImc(foo);
        foo.append(city);
        return city;
    }

    private String reassignRealHideImc(final Foo foo) {
        String city = "a";
        foo.append(city);
        city = foo.format(city);
        city = "b";
        foo.append(city);
        city = foo.format(city);
        return city;
    }

    Bar reassignMock(final Foo foo) {
        bar = reassignMockImc(foo);
        bar.append(foo);
        return bar;
    }

    private Bar reassignMockImc(final Foo foo) {
        nestedMockImc2(foo);
        bar.append(foo);
        return bar.format(foo);
    }

    Bar reassignMock2() {
        bar = reassignMock2Imc();
        bar.append(bar);
        return bar;
    }

    private Bar reassignMock2Imc() {
        bar.append(bar);
        bar = bar.format(bar);
        bar.append(bar);
        return bar.format(bar);
    }

    Bar reassignMockHide(final Foo foo, Bar bar) {
        bar = reassignMockHideImc(foo, bar);
        bar.append(foo);
        return bar;
    }

    private Bar reassignMockHideImc(final Foo foo, Bar bar) {
        bar.append(foo);
        bar = bar.format(foo);
        bar.append(foo);
        bar = bar.format(foo);
        return bar;
    }

    // nested IMC

    String nestedRealSimple() {
        city = nestedRealSimpleImc();
        return city;
    }

    private String nestedRealSimpleImc() {
        city = nestedRealSimpleImc2();
        return city;
    }

    private String nestedRealSimpleImc2() {
        city = foo.cntry();
        return city;
    }

    String nestedReal(final Foo foo) {
        city = nestedRealImc(foo);
        foo.append(city);
        return city;
    }

    private String nestedRealImc(final Foo foo) {
        city = "a";
        foo.append(city);
        city = foo.format(city);
        return nestedRealImc2(foo);
    }

    private String nestedRealImc2(final Foo foo) {
        city = "b";
        foo.append(city);
        return foo.format(city);
    }

    String nestedRealInitialized(final Foo foo) {
        town = "aaa";
        foo.append(town);
        town = nestedRealInitializedImc(foo);
        foo.append(town);
        return town;
    }

    private String nestedRealInitializedImc(final Foo foo) {
        town = "bbb";
        foo.append(town);
        town = foo.format(town);
        return nestedRealInitializedImc2(foo);
    }

    private String nestedRealInitializedImc2(final Foo foo) {
        town = "ccc";
        foo.append(town);
        town = foo.format(town);
        return foo.format(town);
    }

    String nestedRealHide(final Foo foo) {
        String city = nestedRealHideImc(foo);
        foo.append(city);
        return city;
    }

    private String nestedRealHideImc(final Foo foo) {
        String city = nestedRealHideImc2(foo);
        city = foo.format(city);
        return city;
    }

    private String nestedRealHideImc2(final Foo foo) {
        String city = "a";
        foo.append(city);
        city = foo.format(city);
        city = "b";
        foo.append(city);
        return city;
    }

    Bar nestedMock(final Foo foo) {
        bar = nestedMockImc(foo);
        bar.append(foo);
        return bar;
    }

    private Bar nestedMockImc(final Foo foo) {
        bar = nestedMockImc2(foo);
        bar.append(foo);
        bar = bar.format(foo);
        bar.append(foo);
        return bar.format(foo);
    }

    private Bar nestedMockImc2(final Foo foo) {
        bar.append(foo);
        bar = bar.format(foo);
        return bar;
    }

    Bar nestedMock2() {
        bar = nestedMock2Imc();
        bar.append(bar);
        return bar;
    }

    private Bar nestedMock2Imc() {
        bar.append(bar);
        nestedMock2Imc2();
        return bar.format(bar);
    }

    private void nestedMock2Imc2() {
        bar = bar.format(bar);
        bar.append(bar);
    }

    Bar nestedMockHide(final Foo foo, Bar bar) {
        bar = nestedMockHideImc(foo, bar);
        bar.append(foo);
        return bar;
    }

    private Bar nestedMockHideImc(final Foo foo, Bar bar) {
        bar.append(foo);
        bar = bar.format(foo);
        bar = nestedMockHideImc2(foo, bar);
        return bar;
    }

    private Bar nestedMockHideImc2(final Foo foo, Bar bar) {
        bar.append(foo);
        bar = bar.format(foo);
        return bar;
    }

    Bar nestedMockHide2(final Foo foo, Bar bar) {
        bar = nestedMockHide2Imc(foo, bar);
        return bar;
    }

    private Bar nestedMockHide2Imc(final Foo foo, Bar bar) {
        bar = bar.format(foo);
        bar = nestedMockHide2Imc2(foo, bar);
        return bar;
    }

    private Bar nestedMockHide2Imc2(final Foo foo, final Bar bar) {
        return bar;
    }
}
