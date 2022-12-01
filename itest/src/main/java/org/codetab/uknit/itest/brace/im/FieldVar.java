package org.codetab.uknit.itest.brace.im;

import java.util.Locale;

public class FieldVar {

    private Locale locale;

    public String callReturnsFieldInvoke() {
        return (returnsFieldInvoke());
    }

    private String returnsFieldInvoke() {
        return ((locale).getCountry());
    }
}
