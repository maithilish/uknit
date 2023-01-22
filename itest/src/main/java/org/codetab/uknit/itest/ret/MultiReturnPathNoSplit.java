package org.codetab.uknit.itest.ret;

class MultiReturnPathNoSplit {

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
