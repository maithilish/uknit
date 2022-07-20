package org.codetab.uknit.itest.ctlflow;

public class TryCatchFinally {

    public String fooTry(final StringBuilder sb) {
        sb.append("start");
        try {
            sb.append("try");
        } catch (IllegalArgumentException e) {
            sb.append("catch illegal arg");
        } finally {
            sb.append("finally");
        }
        sb.append("end");
        return sb.toString();
    }

    public String fooTryFinally(final StringBuilder sb) {
        sb.append("start");
        try {
            sb.append("try");
        } catch (IllegalStateException e) {
            sb.append("catch illegal state");
        } catch (IllegalArgumentException e) {
            sb.append("catch illegal arg");
        } finally {
            sb.append("finally");
        }

        try {
            sb.append("try 2");
        } finally {
            sb.append("finally 2");
        }

        try {
            sb.append("try 3");
        } finally {
            sb.append("finally 3");
        }
        sb.append("end");
        return sb.toString();
    }
}
