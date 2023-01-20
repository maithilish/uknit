package org.codetab.uknit.itest.initializer;

import java.io.File;
import java.util.List;
import java.util.stream.IntStream;

class Real {

    /**
     * Real returns real - file.getName() returns String (real) on which
     * toLowerCase() is called which again returns real
     *
     * @param file
     * @return
     */
    public String assignRealReturnsReal(final File file) {
        String name = file.getName().toLowerCase();
        return name;
    }

    public String returnRealReturnsReal(final File file) {
        return file.getName().toLowerCase();
    }

    public StringBuilder assignRealReturnsRealInArgs(final StringBuilder s1,
            final File file) {
        StringBuilder s2 = s1.append(file.getName().toLowerCase());
        return s2;
    }

    public StringBuilder returnRealReturnsRealInArgs(final StringBuilder s1,
            final File file) {
        return s1.append(file.getName().toLowerCase());
    }

    public StringBuilder assignRealReturnsRealInArgs2(final StringBuilder s1,
            final StringBuilder s2, final File file) {
        StringBuilder s3 = s1.append(s2.append(file.getName().toLowerCase()));
        return s3;
    }

    public StringBuilder returnRealReturnsRealInArgs2(final StringBuilder s1,
            final StringBuilder s2, final File file) {
        return s1.append(s2.append(file.getName().toLowerCase()));
    }

    // TODO L - assert to compare streams
    public IntStream assignRealReturnsMock(final File file) {
        IntStream codePoints = file.getName().codePoints();
        return codePoints;
    }

    public IntStream returnRalReturnsMock(final File file) {
        return file.getName().codePoints();
    }

    // TODO - may change after inserter is enabled
    public File assignRealCollectionReturnsMock(final List<File> files) {
        File file = files.get(0);
        return file;
    }

    public File returnRealCollectionReturnsMock(final List<File> files) {
        return files.get(0);
    }
}
