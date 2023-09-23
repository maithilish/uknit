package org.codetab.uknit.itest.patch.var;

import java.util.Locale;

class Model {

    interface Foo {
        String lang();

        String lang(String cntry);

        String cntry();

        int size();

        int index();

        Object obj();

        Bar bar();
    }

    interface Bar {
        Locale locale(String lang);

        Locale locale(String lang, String cntry);

        int size();

        String key(String cntry);

        void baz();
    }

    interface Factory {
        Foo createFoo();
    }
}
