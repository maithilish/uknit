package org.codetab.uknit.itest.anon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

import java.io.File;
import java.io.IOException;

import org.codetab.uknit.itest.ITBase;
import org.junit.jupiter.api.Test;

class AnonIT extends ITBase {

    @Test
    public void test() throws IOException {

        configure();

        try {
            // for diamond operator <>
            addTransientConfig("uknit.compiler.compliance", "11");

            generateTestClass();

            File actualFile = getActualFile();
            File expectedFile = getExpectedFile();

            assertThat(actualFile).exists();
            assertThat(expectedFile).exists();

            assertThat(contentOf(actualFile))
                    .isEqualTo(contentOf(expectedFile));
        } finally {
            restoreTransientConfigs();
        }
    }
}
