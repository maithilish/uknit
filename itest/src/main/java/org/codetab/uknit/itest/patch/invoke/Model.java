package org.codetab.uknit.itest.patch.invoke;

import java.util.Locale;

public class Model {

    interface Foo {
        String lang();

        String lang(String cntry);

        String cntry();

        int size();

        int index();

        Object obj();

        String name();
    }

    interface Bar {
        Locale locale(String lang);

        Locale locale(String lang, String cntry);

        int size();

        String key(String cntry);

        String name();
    }
}
