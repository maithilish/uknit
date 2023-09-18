package org.codetab.uknit.itest.reassign;

public class ArrayAccess {

    public void assignTwice() {
        int i = 0;
        int z = 0;
        int[] vDown = new int[1];

        vDown[i] = vDown[z];
        vDown[i] = vDown[z];
    }

    public void assignTwiceInIfBlock(final int code) {
        int i = 0;
        int[] vDown = new int[1];

        if (code > 1) {
            vDown[i] = vDown[i];
        } else {
            vDown[i] = vDown[i];
        }
    }
}
