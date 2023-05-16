package org.codetab.uknit.itest.verify.times;

import java.io.File;

import org.codetab.uknit.itest.verify.times.Model.Foo;

class Conditional {

    public void invoke(final Foo foo, final File file) {
        String x = "x";
        String y = "y";
        foo.append(file.getAbsolutePath().equals(x) ? foo.format(x)
                : foo.format(y));
        foo.append(file.getAbsolutePath().equals(y) ? foo.format(x)
                : foo.format(y));
    }

    public void arrayAccess(final Foo foo) {
        String[] cities = {"x", "y"};
        foo.append(cities[0].equals("x") ? cities[0] : cities[1]);
        foo.append(cities[0].equals("y") ? cities[0] : cities[1]);
    }
}
