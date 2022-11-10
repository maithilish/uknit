package org.codetab.uknit.itest.initializer;

import java.io.File;
import java.io.IOException;

public class Mock {

    public File assignMockReturnsMock(final File file) throws IOException {
        File cFile = file.getCanonicalFile();
        return cFile;
    }

    public File returnMockReturnsMock(final File file) throws IOException {
        return file.getCanonicalFile();
    }

    public File assignMockReturnsMock2(final File file) throws IOException {
        File cFile = file.getCanonicalFile().getCanonicalFile();
        return cFile;
    }

    public File returnMockReturnsMock2(final File file) throws IOException {
        return file.getCanonicalFile().getCanonicalFile();
    }

    public int assignMockReturnsMockInArgs(final File file) throws IOException {
        int value = file.compareTo(file.getCanonicalFile().getAbsoluteFile());
        return value;
    }

    public int returnMockReturnsMockInArgs(final File file) throws IOException {
        return file.compareTo(file.getCanonicalFile().getAbsoluteFile());
    }

    public String assignMockReturnsReal(final File file) throws IOException {
        String absPath = file.getCanonicalFile().getAbsolutePath();
        return absPath;
    }

    public String returnMockReturnsReal(final File file) throws IOException {
        return file.getCanonicalFile().getAbsolutePath();
    }

    public int assignMockReturnsRealInArgs(final File file) throws IOException {
        int value = file.getAbsolutePath()
                .compareTo(file.getCanonicalFile().getAbsolutePath());
        return value;
    }

    public int returnMockReturnsRealInArgs(final File file) throws IOException {
        return file.getAbsolutePath()
                .compareTo(file.getCanonicalFile().getAbsolutePath());
    }
}
