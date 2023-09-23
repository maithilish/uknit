package org.codetab.uknit.itest.imc.cyclic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

import java.io.File;
import java.io.IOException;

import org.codetab.uknit.itest.ITBase;
import org.junit.jupiter.api.Test;

class SimpleIT extends ITBase {

    @Test
    public void test() throws IOException {

        configure();

        try {
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
