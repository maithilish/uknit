package org.codetab.uknit.itest.clz;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

import java.io.File;
import java.io.IOException;

import org.codetab.uknit.itest.ITBase;
import org.junit.jupiter.api.Test;

class MultiClzIT extends ITBase {

    @Test
    public void test() throws IOException {

        String testCaseDir = "org/codetab/uknit/itest/clz";
        String className = "MultiClz";

        configure(testCaseDir, className);

        try {
            generateTestClass();

            addTransientConfig("uknit.expectedFile", "MultiClz.exp");

            File actualFile = getActualFile();
            File expectedFile = getExpectedFile();

            assertThat(actualFile).exists();
            assertThat(expectedFile).exists();
            writeDiffToFile(expectedFile, actualFile);

            assertThat(contentOf(actualFile))
                    .isEqualTo(contentOf(expectedFile));

            className = "MultiClzBar";

            configure(testCaseDir, className);

            restoreTransientConfigs();
            addTransientConfig("uknit.expectedFile", "MultiClzBar.exp");

            actualFile = getActualFile();
            expectedFile = getExpectedFile();

            assertThat(actualFile).exists();
            assertThat(expectedFile).exists();
            writeDiffToFile(expectedFile, actualFile);

            assertThat(contentOf(actualFile))
                    .isEqualTo(contentOf(expectedFile));
        } finally {
            restoreTransientConfigs();
        }
    }
}
