package org.codetab.uknit.itest.flow;

public class BreakFlow {

    public boolean plainBreak() {
        int[] arrayOfInts = {32, 87, 3, 589};
        int searchfor = 87;

        int i;
        boolean foundIt = false;

        for (i = 0; i < arrayOfInts.length; i++) {
            if (arrayOfInts[i] == searchfor) {
                foundIt = true;
                break;
            }
        }
        return foundIt;
    }

    public boolean labeledBreak() {
        int[][] arrayOfInts = {{32, 87, 3, 589}, {12, 1076, 2000, 8}};
        int searchfor = 12;

        int i;
        int j = 0;
        boolean foundIt = false;

        search: for (i = 0; i < arrayOfInts.length; i++) {
            for (j = 0; j < arrayOfInts[i].length; j++) {
                if (arrayOfInts[i][j] == searchfor) {
                    foundIt = true;
                    break search;
                }
            }
        }
        return foundIt;
    }
}
