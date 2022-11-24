package org.codetab.uknit.itest.initializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class StepinInternal {

    public int accessArrayCreation() {
        int[] array = new int[2];
        int foo = accessArray(array);
        return foo;
    }

    public int accessArrayInitializer() {
        int[] array = {1, 2};
        int foo = accessArray(array);
        return foo;
    }

    public int accessArrayNewInitializer() {
        int[] array = new int[] {1, 2};
        int foo = accessArray(array);
        return foo;
    }

    // internal method
    private int accessArray(final int[] array) {
        int foo = array[0];
        return foo;
    }

    public String invokeReal() {
        List<String> list = new ArrayList<>();
        list.add("foo");
        String foo = accessList(list);
        return foo;
    }

    // internal method
    private String accessList(final List<String> list) {
        String foo = list.get(0);
        return foo;
    }

    public Set<String> invokeRealOfMockType() {
        Locale locale = new Locale("en");
        Set<String> attr = accessLocale(locale);
        return attr;
    }

    // internal method
    private Set<String> accessLocale(final Locale locale) {
        Set<String> attr = locale.getUnicodeLocaleAttributes();
        return attr;
    }
}
