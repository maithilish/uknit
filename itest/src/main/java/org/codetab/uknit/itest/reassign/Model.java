package org.codetab.uknit.itest.reassign;

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
    }
}
