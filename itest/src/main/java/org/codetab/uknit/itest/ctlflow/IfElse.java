package org.codetab.uknit.itest.ctlflow;

public class IfElse {

    public String fooIf(final StringBuilder sb, final boolean flag) {
        sb.append("start");
        if (flag) {
            sb.append("if");
        }
        sb.append("end");
        return sb.toString();
    }

    public String fooIfElse(final StringBuilder sb, final boolean flag) {
        sb.append("start");
        if (flag) {
            sb.append("if");
        } else {
            sb.append("else");
        }
        sb.append("end");
        return sb.toString();
    }

    public String fooIfIf(final StringBuilder sb, final boolean flag,
            final boolean done) {
        sb.append("start");
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
        sb.append("end");
        return sb.toString();
    }

}
