package org.codetab.uknit.itest.cast;

public class Model {

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
