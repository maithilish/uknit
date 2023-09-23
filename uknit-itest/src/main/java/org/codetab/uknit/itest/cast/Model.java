package org.codetab.uknit.itest.cast;

class Model {

    interface Foo {
        int index();

        void append(int index);
    }

    interface Pets {
        Pet getPet(String type);
    }

    interface Pet {
        String sex();
    }

    interface Dog extends Pet {
        String breed();
    }

    interface Pitbull extends Dog {
        String name();
    }

    interface Metric {
        void aggregate(Metric other);
    }
}
