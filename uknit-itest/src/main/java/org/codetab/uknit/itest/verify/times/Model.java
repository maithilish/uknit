package org.codetab.uknit.itest.verify.times;

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

        int index();
    }

    class Person {
        int id;
        long lid;
    }
}
