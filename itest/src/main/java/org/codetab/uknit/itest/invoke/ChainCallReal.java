package org.codetab.uknit.itest.invoke;

import java.io.File;
import java.util.IntSummaryStatistics;
import java.util.stream.IntStream;

class ChainCallReal {

    public String realRealReal(final String name) {
        return name.toLowerCase().toUpperCase();
    }

    public File realRealReal(final File file) {
        return file.getAbsoluteFile().getParentFile();
    }

    public String realRealRealString(final File file) {
        return file.getName().toLowerCase();
    }

    public String realRealRealRealString(final File file) {
        return file.getName().toLowerCase().toUpperCase();
    }

    public IntStream realRealRealish(final File file) {
        return file.getName().codePoints();
    }

    public IntSummaryStatistics realRealRealishRealish(final File file) {
        return file.getName().codePoints().summaryStatistics();
    }

    public StringBuilder realRealRealAsArg(final StringBuilder s1,
            final StringBuilder s2, final File file) {
        return s1.append(s2.append(file.getName().toLowerCase()));
    }

    public StringBuilder realMockMockAsArg(final StringBuilder s1,
            final StringBuilder s2, final File file) {
        return s1.append(s2.append(file.getAbsoluteFile().getParentFile()));
    }

}
