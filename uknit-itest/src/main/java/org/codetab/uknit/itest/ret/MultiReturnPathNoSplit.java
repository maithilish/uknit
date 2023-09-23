package org.codetab.uknit.itest.ret;

class MultiReturnPathNoSplit {

    public String twoReturnIfElse(final boolean flag) {
        if (flag) {
            return "foo";
        } else {
            return "bar";
        }
    }

    public String twoReturnNoElse(final boolean flag) {
        if (flag) {
            return "foo";
        }
        return "bar";
    }

    public String twoNullNoElse(final boolean flag) {
        if (flag) {
            return "foo";
        }
        return null;
    }

    public String multiReturnIfElse(final boolean flag1, final boolean flag2) {
        if (flag1) {
            return "foo";
        } else if (flag2) {
            return "bar";
        } else {
            return "baz";
        }
    }

    public String multiReturnNoElse(final boolean flag1, final boolean flag2) {
        if (flag1) {
            return "foo";
        } else if (flag2) {
            return "bar";
        }
        return "baz";
    }

    public String multiNullNoElse(final boolean flag1, final boolean flag2) {
        if (flag1) {
            return "foo";
        } else if (flag2) {
            return "bar";
        }
        return null;
    }

    // STEPIN - no evaluation of flag
    public String twoReturnPaths() {
        boolean flag = true;
        if (flag) {
            return "foo";
        } else {
            return "bar";
        }
    }
}
