package org.codetab.uknit.itest.verify.times;

import org.codetab.uknit.itest.verify.times.Model.Foo;

class ClassInstanceCreation {

    // arg is class instance creation
    public void classInstanceCreation(final Foo foo) {
        foo.append(new String("foo"));
        foo.append(new String("foo"));
        foo.append(new String("bar"));
    }
}
