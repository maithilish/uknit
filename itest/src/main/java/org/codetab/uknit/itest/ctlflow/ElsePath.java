package org.codetab.uknit.itest.ctlflow;

public class ElsePath {

    /**
     * Else path requires for top if
     * @param sb
     * @param flag
     * @return
     */
    public String ifFoo(final StringBuilder sb, final boolean flag) {
        sb.append("start");
        if (flag) {
            sb.append("if");
        }
        sb.append("end");
        return sb.toString();
    }

    /**
     * Else path for bottom done if not required as top else path takes care of
     * it
     * @param sb
     * @param flag
     * @param done
     * @return
     */
    public String ifIfFoo(final StringBuilder sb, final boolean flag,
            final boolean done) {
        sb.append("start");
        if (flag) {
            sb.append("if flag");
        }
        if (done) {
            sb.append("if done");
        }
        sb.append("end");
        return sb.toString();
    }

    /**
     * Else path for bottom done if not required as top try path takes care of
     * it
     * @param sb
     * @param flag
     * @return
     */
    public String tryIfFoo(final StringBuilder sb, final boolean flag) {
        sb.append("start");
        try {
            sb.append("try");
        } finally {
            sb.append("finally");
        }
        if (flag) {
            sb.append("if");
        }
        sb.append("end");
        return sb.toString();
    }

    public String ifThenIfFoo(final StringBuilder sb, final boolean flag,
            final boolean done, final boolean eof) {
        sb.append("start");
        if (flag) {
            sb.append("if flag");
            if (done) {
                sb.append("if done");
            }
        }
        if (eof) {
            sb.append("if eof");
        }
        sb.append("end");
        return sb.toString();
    }
}
