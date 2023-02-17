package org.codetab.uknit.itest.nested.inner;

class Model {

    static class Duck {

        public String fly(final String speed) {
            return "Speed " + speed;
        }
    }

    static class Logger {

        public void log(final String msg) {

        }
    }
}
