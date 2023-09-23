package org.codetab.uknit.itest.file;

class Model {

    class Duck {

        public void swim(final String time) {
            // do nothing
        }

        public String fly(final String speed) {
            return null;
        }

        public void dive(final String state) {

        }
    }

    interface Foo {

        String format(String name);

        void append(String name);

        String format(String name, String dept);

        void append(String name, String dept);
    }

}
