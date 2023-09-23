package org.codetab.uknit.itest.flow.trycatch;

import java.io.BufferedReader;
import java.io.FileNotFoundException;

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

    interface Io {
        BufferedReader bufferedFileReader(String path)
                throws FileNotFoundException;
    }

    interface Logger {
        void debug(String msg);
    }
}
