package org.codetab.uknit.itest.exp.value;

class Model {

    interface Foo {
        String format(String name);

        void append(String name);

        String format(Object names);

        void append(Object names);

        static String valueOf(final String name) {
            return name;
        }

        static Object valueOf(final Object name) {
            return name;
        }
    }

    class Person {
        int id;
        long lid;
    }
}
