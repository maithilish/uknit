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

        String key = "uknit.createInstance.List";
        String defaultConfig = getConfig(key);
        addConfig(key, "mock"); // mock the list

        generateTestClass();

        addConfig(key, defaultConfig);

        File actualFile = getActualFile();
        File expectedFile = getExpectedFile();

        assertThat(actualFile).exists();
        assertThat(expectedFile).exists();

        assertThat(contentOf(actualFile)).isEqualTo(contentOf(expectedFile));
    }

}
