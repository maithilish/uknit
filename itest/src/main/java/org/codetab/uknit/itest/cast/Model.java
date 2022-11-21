package org.codetab.uknit.itest.cast;

public class Model {

    interface Pets {
        Pet getPet(String type);
    }

    interface Pet {
    }

    interface Dog extends Pet {

    }

    interface Metric {
        void aggregate(Metric other);
    }
}

