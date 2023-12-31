package org.codetab.uknit.itest.misuse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

import java.io.File;
import java.io.IOException;

import org.codetab.uknit.itest.ITBase;
import org.junit.jupiter.api.Test;

class FinalDisallowIT extends ITBase {

    @Test
    public void test() throws IOException {

        configure();

        try {

            addTransientConfig("uknit.mockito.stub.final", "false");

            generateTestClass();

            File actualFile = getActualFile();
            File expectedFile = getExpectedFile();

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
