package org.codetab.uknit.itest.clz;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

import java.io.File;
import java.io.IOException;

import org.codetab.uknit.itest.ITBase;
import org.junit.jupiter.api.Test;

public class TwinsIT extends ITBase {

    @Test
    public void test() throws IOException {

        String testCaseDir = "org/codetab/uknit/itest/clz";
        String className = "TwinsFoo";

        configure(testCaseDir, className);

        try {
            generateTestClass();

            addTransientConfig("uknit.expectedFile", "TwinsFooTest.exp");

            File actualFile = getActualFile();
            File expectedFile = getExpectedFile();

            assertThat(actualFile).exists();
            assertThat(expectedFile).exists();

            assertThat(contentOf(actualFile))
                    .isEqualTo(contentOf(expectedFile));

            className = "TwinsBar";

            configure(testCaseDir, className);

            restoreTransientConfigs();
            addTransientConfig("uknit.expectedFile", "TwinsBarTest.exp");

            actualFile = getActualFile();
            expectedFile = getExpectedFile();

            assertThat(actualFile).exists();
            assertThat(expectedFile).exists();

            assertThat(contentOf(actualFile))
                    .isEqualTo(contentOf(expectedFile));
        } finally {
            restoreTransientConfigs();
        }
    }
}
