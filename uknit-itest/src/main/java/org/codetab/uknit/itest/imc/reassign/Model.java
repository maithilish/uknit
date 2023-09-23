package org.codetab.uknit.itest.imc.reassign;

import java.util.Date;
import java.util.Locale;

class Model {

    interface Foo {

        Locale locale();

        Date date();

        Object obj();

        String get(int index);

        String format(String name);

        void append(String name);

        String format(String name, String city);

        void append(String name, String city);

        String cntry();
    }

    interface Bar {

        Bar format(Foo foo);

        void append(Foo foo);

        void append(Bar bar);

        Bar format(Bar bar);
    }

    interface Baz {
        Foo format(Foo foo);

        void append(Foo foo);
    }
}
