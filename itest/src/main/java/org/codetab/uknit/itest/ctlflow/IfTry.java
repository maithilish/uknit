package org.codetab.uknit.itest.ctlflow;

public class IfTry {

    public String fooIfTry(final StringBuilder sb, final boolean flag,
            final boolean done) {
        sb.append("start");
        if (flag) {
            sb.append("if");
            try {
                sb.append("try");
            } catch (IllegalStateException e) {
                sb.append("catch");
            } finally {
                sb.append("finally");
            }

        } else {
            sb.append("else");
        }
        sb.append("end");
        return sb.toString();
    }

    public String fooTryIf(final StringBuilder sb, final boolean flag,
            final boolean done) {
        sb.append("start");
        try {
            sb.append("try");
            if (flag) {
                sb.append("if");
                if (done) {
                    sb.append("if if");
                } else {
                    sb.append("if else");
                }
            } else {
                sb.append("else");
            }
        } catch (IllegalStateException e) {
            sb.append("catch");
        } finally {
            sb.append("finally");
        }
        sb.append("end");
        return sb.toString();
    }

}
