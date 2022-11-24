package org.codetab.uknit.itest.reassign;

import java.util.Date;
import java.util.Locale;

public class Model {

    interface Foo {

        Locale locale();

        Date date();

        Object obj();

        String get(int index);
    }
}
