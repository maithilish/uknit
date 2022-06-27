package org.codetab.uknit.itest.stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

import java.io.File;
import java.io.IOException;

import org.codetab.uknit.itest.ITBase;
import org.junit.jupiter.api.Test;

public class MockStreamIT extends ITBase {

    @Test
    public void test() throws IOException {

        configure();

        addTransientConfig("uknit.createInstance.List", "mock");
        addTransientConfig("uknit.createInstance.Stream", "mock");

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
