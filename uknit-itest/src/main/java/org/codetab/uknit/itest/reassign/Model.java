package org.codetab.uknit.itest.reassign;

class Model {

    interface Foo {

        String format(String name);

        void append(String name);

        String format(String name, String city);

        void append(String name, String city);

        String get(int index);
    }

    interface Bar {

        Bar format(Foo foo);

        void append(Foo foo);

        void append(Bar bar);

        Bar format(Bar bar);
    }

    interface Baz {
        Foo format(Foo foo);

        void append(Foo foo);
    }
}
