package org.codetab.uknit.itest.load;

public class Model {

    interface Foo {

        void file();

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
}
