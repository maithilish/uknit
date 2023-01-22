package org.codetab.uknit.itest.misuse;

import java.io.File;

class Equals {

    public boolean foo(final File file1, final File file2) {
        if (file1.equals(file2)) {
            return true;
        } else {
            return false;
        }
    }

}
