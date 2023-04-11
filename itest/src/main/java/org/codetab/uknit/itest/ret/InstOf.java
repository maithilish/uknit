package org.codetab.uknit.itest.ret;

import java.time.LocalDate;

public class InstOf {

    public boolean ret(final Object x) {
        return x instanceof LocalDate;
    }

    public boolean ret() {
        Object x = "foo";
        return x instanceof LocalDate;
    }

    public boolean call() {
        return im();
    }

    private boolean im() {
        Object x = "foo";
        return x instanceof LocalDate;
    }

    public boolean callStatic() {
        return staticIm();
    }

    private static boolean staticIm() {
        Object x = "foo";
        return x instanceof LocalDate;
    }
}
