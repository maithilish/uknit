package org.codetab.uknit.itest.ret;

public class Voids {

    public void singleReturn() {
        return;
    }

    public void twoReturnInIfElse(final boolean flag) {
        if (flag) {
            return;
        } else {
            return;
        }
    }

    public void twoReturnNoElse(final boolean flag) {
        if (flag) {
            return;
        }
        return;
    }

    public void multiReturnIfElse(final boolean flag1, final boolean flag2) {
        if (flag1) {
            return;
        } else if (flag2) {
            return;
        } else {
            return;
        }
    }

    public void multiReturnNoElse(final boolean flag1, final boolean flag2) {
        if (flag1) {
            return;
        } else if (flag2) {
            return;
        }
        return;
    }
}
