package org.codetab.uknit.itest.misuse;

import java.io.File;

class HashCode {

    public boolean foo(final File file1, final File file2) {
        return file1.hashCode() == file2.hashCode();
    }
}
