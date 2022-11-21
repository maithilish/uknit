package org.codetab.uknit.itest.array;

import java.util.ArrayList;

public class Model {

    class Groups<E> extends ArrayList<E> {
        private static final long serialVersionUID = 1L;
    }

    interface Foo {
        String name();
    }

    interface Bar {
        String name();
    }
}
