package org.codetab.uknit.itest.flow;

public class ContinueFlow {

    public int plainContinue() {
        String searchMe = "peter piper picked a " + "peck of pickled peppers";
        int max = searchMe.length();
        int numPs = 0;
        for (int i = 0; i < max; i++) {
            if (searchMe.charAt(i) != 'p') {
                continue;
            }
            numPs++;
        }
        return numPs;
    }

    public boolean labeledContinue() {
        String searchMe = "Look for a substring in me";
        String substring = "sub";
        boolean foundIt = false;

        int max = searchMe.length() - substring.length();

        test: for (int i = 0; i <= max; i++) {
            int n = substring.length();
            int j = i;
            int k = 0;
            while (n-- != 0) {
                if (searchMe.charAt(j++) != substring.charAt(k++)) {
                    continue test;
                }
            }
            foundIt = true;
            break test;
        }
        return foundIt;
    }
}
