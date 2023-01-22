package org.codetab.uknit.itest.misuse;

class Model {

    class Foo {

        final public int finalMethod(final String foo, final String bar) {
            return foo.compareTo(bar);
        }
    }
}
