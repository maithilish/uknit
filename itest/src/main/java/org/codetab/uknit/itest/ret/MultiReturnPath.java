package org.codetab.uknit.itest.ret;

class MultiReturnPath {

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
