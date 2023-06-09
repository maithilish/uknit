package org.codetab.uknit.itest.exp.value;

import org.codetab.uknit.itest.exp.value.Model.Foo;

class MultiDimArrayAccess {

    public void literal(final Foo foo) {
        String[] cities = {"foo", "bar"};
        int[][] stateCodes = {{1, 2}, {11, 12}};
        String[][][] countries =
                {{{"foo1", "bar1"}, {"foo2", "bar2"}}, {{"foox", "barx"}}};

        foo.appendString(cities[0]);
        foo.appendString(cities[1]);

        foo.appendObj(stateCodes[0][0]);
        foo.appendObj(stateCodes[0][1]);
        foo.appendObj(stateCodes[1][0]);
        foo.appendObj(stateCodes[1][1]);

        foo.appendString(countries[0][0][0]);
        foo.appendString(countries[0][0][1]);
        foo.appendString(countries[0][1][0]);
        foo.appendString(countries[0][1][1]);
        foo.appendString(countries[1][0][0]);
        foo.appendString(countries[1][0][1]);

        // braces
        foo.appendString(countries[(1)][(0)][(0)]);
        foo.appendString((countries[1][0][(1)]));
    }

    public void argIsMultiDimArrayAccess(final Foo foo) {
        int[][] codes = {{1, 0}};
        String[][][] cities =
                {{{"foo1", "bar1"}, {"foo2", "bar2"}}, {{"foox", "barx"}}};

        foo.appendString(cities[codes[0][1]][codes[0][1]][codes[0][0]]); // cities[0][0][1]
        foo.appendString(cities[codes[0][0]][codes[0][1]][codes[0][0]]); // cities[1][0][1]

        // braces
        foo.appendString(
                cities[(codes[(0)][(0)])][codes[(0)][1]][(codes[0][0])]); // cities[1][0][1]
    }

}
