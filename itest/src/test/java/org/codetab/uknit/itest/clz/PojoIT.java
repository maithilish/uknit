package org.codetab.uknit.itest.clz;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

import java.io.File;
import java.io.IOException;

import org.codetab.uknit.itest.ITBase;
import org.junit.jupiter.api.Test;

public class PojoIT extends ITBase {

    @Test
    public void test() throws IOException {

        configure();

        addTransientConfig("uknit.ignore.method.constructor", "false");

        try {
            generateTestClass();
        } finally {
            restoreTransientConfigs();
        }

        File actualFile = getActualFile();
        File expectedFile = getExpectedFile();

        assertThat(actualFile).exists();
        assertThat(expectedFile).exists();

        assertThat(contentOf(actualFile)).isEqualTo(contentOf(expectedFile));
    }
}
