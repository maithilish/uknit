package org.codetab.uknit.itest.linked;

import java.util.Date;
import java.util.Locale;

class Model {

    interface Foo {

        Locale locale();

        Date date();

        Object obj();
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
