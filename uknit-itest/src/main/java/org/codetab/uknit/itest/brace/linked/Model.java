package org.codetab.uknit.itest.brace.linked;

import java.util.Date;
import java.util.Locale;

class Model {

    interface Foo {

        Locale locale();

        Date date();

        Object obj();

        int index();
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
