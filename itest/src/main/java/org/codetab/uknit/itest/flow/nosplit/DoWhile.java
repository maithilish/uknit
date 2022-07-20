package org.codetab.uknit.itest.flow.nosplit;

public class DoWhile {

    public int whileDo() {
        int count = 1;
        while (count < 5) {
            count++;
        }
        return count;
    }

    public int doWhile() {
        int count = 1;
        do {
            count++;
        } while (count < 5);
        return count;
    }
}
