package org.codetab.uknit.itest.ctlflow;

public class IfTry {

    public String ifTryFoo(final StringBuilder sb, final boolean flag) {
        sb.append("start");
        if (flag) {
            sb.append("if");
            try {
                sb.append("if try");
            } catch (IllegalStateException e) {
                sb.append("if catch");
            } finally {
                sb.append("if finally");
            }

        } else {
            sb.append("else");
        }
        sb.append("end");
        return sb.toString();
    }

    public String tryIfFoo(final StringBuilder sb, final boolean flag,
            final boolean done) {
        sb.append("start");
        try {
            sb.append("try");
            if (flag) {
                sb.append("try if");
                if (done) {
                    sb.append("try if if");
                } else {
                    sb.append("try if else");
                }
            } else {
                sb.append("try else");
            }
        } catch (IllegalStateException e) {
            sb.append("catch");
        } finally {
            sb.append("finally");
        }
        sb.append("end");
        return sb.toString();
    }

    public String tryCatchIfFoo(final StringBuilder sb, final boolean flag,
            final boolean done) {
        sb.append("start");
        try {
            sb.append("try");
        } catch (IllegalStateException e) {
            sb.append("catch");
            if (flag) {
                sb.append("catch if");
                if (done) {
                    sb.append("catch if if");
                } else {
                    sb.append("catch if else");
                }
            } else {
                sb.append("catch else");
            }
        } finally {
            sb.append("finally");
        }
        sb.append("end");
        return sb.toString();
    }

    public String tryFinallyIfFoo(final StringBuilder sb, final boolean flag,
            final boolean done) {
        sb.append("start");
        try {
            sb.append("try");
        } catch (IllegalStateException e) {
            sb.append("catch");
        } finally {
            sb.append("finally");
            if (flag) {
                sb.append("finally if");
                if (done) {
                    sb.append("finally if if");
                } else {
                    sb.append("finally if else");
                }
            } else {
                sb.append("finally else");
            }
        }
        sb.append("end");
        return sb.toString();
    }

    public String tryIfNoFinallyFoo(final StringBuilder sb,
            final boolean flag) {
        sb.append("start");
        try {
            sb.append("try");
            if (flag) {
                sb.append("try if");
            } else {
                sb.append("try else");
            }
        } catch (IllegalStateException e) {
            sb.append("catch");
        }
        sb.append("end");
        return sb.toString();
    }

    public String tryCatchIfNoFinallyFoo(final StringBuilder sb,
            final boolean flag) {
        sb.append("start");
        try {
            sb.append("try");
        } catch (IllegalStateException e) {
            sb.append("catch");
            if (flag) {
                sb.append("catch if");
            } else {
                sb.append("catch else");
            }
        }
        sb.append("end");
        return sb.toString();
    }

    public String tryIfNoCatchFoo(final StringBuilder sb, final boolean flag) {
        sb.append("start");
        try {
            sb.append("try");
            if (flag) {
                sb.append("try if");
            } else {
                sb.append("try else");
            }
        } finally {
            sb.append("finally");
        }
        sb.append("end");
        return sb.toString();
    }

    public String tryFinallyIfNoCatchFoo(final StringBuilder sb,
            final boolean flag) {
        sb.append("start");
        try {
            sb.append("try");
        } finally {
            sb.append("finally");
            if (flag) {
                sb.append("finally if");
            } else {
                sb.append("finally else");
            }
        }
        sb.append("end");
        return sb.toString();
    }

}
