package org.codetab.uknit.itest.initializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class Stepin {

    public int accessArrayCreation() {
        int[] array = new int[2];
        int foo = array[0];
        return foo;
    }

    public int accessArrayInitializer() {
        int[] array = {1, 2};
        int foo = array[0];
        return foo;
    }

    public int accessArrayNewInitializer() {
        int[] array = new int[] {1, 2};
        int foo = array[0];
        return foo;
    }

    public String invokeReal() {
        List<String> list = new ArrayList<>();
        list.add("foo");
        String foo = list.get(0);
        return foo;
    }

    public Set<String> invokeRealOfMockType() {
        Locale locale = new Locale("en");
        Set<String> attr = locale.getUnicodeLocaleAttributes();
        return attr;
    }

}
