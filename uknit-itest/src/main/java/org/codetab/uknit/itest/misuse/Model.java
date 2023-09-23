package org.codetab.uknit.itest.misuse;

class Model {

    class Foo {

        final public int finalMethod(final String foo, final String bar) {
            return foo.compareTo(bar);
        }
    }

    class Duck {

        public void swim(final String time) {
        }

        public String fly(final String speed) {
            return null;
        }

        public void dive(final String state) {
        }
    }
}
