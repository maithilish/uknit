package org.codetab.uknit.itest.varargs;

class Model {

    interface Foo {

        String format(String name);

        void append(String name);

        String format(String name, String dept);

        void append(String name, String dept);
    }

    interface Bar {
        String name();
    }

}
