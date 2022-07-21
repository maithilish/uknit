package org.codetab.uknit.itest.ctlflow;

public class TryTry {

    public String tryTryFoo(final StringBuilder sb) {
        sb.append("start");
        try {
            sb.append("try");
            try {
                sb.append("try try");
            } catch (IllegalAccessError e) {
                sb.append("try try catch");
            } finally {
                sb.append("try try finally");
            }
        } catch (IllegalArgumentException e) {
            sb.append("catch");
        } finally {
            sb.append("finally");
        }
        sb.append("end");
        return sb.toString();
    }

    public String tryCatchTryFoo(final StringBuilder sb) {
        sb.append("start");
        try {
            sb.append("try");
        } catch (IllegalArgumentException e) {
            sb.append("catch");
            try {
                sb.append("catch try");
            } catch (IllegalAccessError e1) {
                sb.append("catch catch");
            } finally {
                sb.append("catch finally");
            }
        } finally {
            sb.append("finally");
        }
        sb.append("end");
        return sb.toString();
    }

    public String tryFinallyTryFoo(final StringBuilder sb) {
        sb.append("start");
        try {
            sb.append("try");
        } catch (IllegalArgumentException e) {
            sb.append("catch");
        } finally {
            sb.append("finally");
            try {
                sb.append("finally try");
            } catch (IllegalAccessError e) {
                sb.append("finally catch");
            } finally {
                sb.append("finally finally");
            }
        }
        sb.append("end");
        return sb.toString();
    }
}
