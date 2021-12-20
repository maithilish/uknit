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

        String listKey = "uknit.createInstance.List";
        String listConfig = getConfig(listKey);
        addConfig(listKey, "mock"); // mock the list

        String streamKey = "uknit.createInstance.Stream";
        String streamConfig = getConfig(streamKey);
        addConfig(streamKey, "mock"); // mock the stream

        try {
            generateTestClass();
        } finally {
            addConfig(listKey, listConfig);
            addConfig(streamKey, streamConfig);
        }

        File actualFile = getActualFile();
        File expectedFile = getExpectedFile();

        assertThat(actualFile).exists();
        assertThat(expectedFile).exists();

        assertThat(contentOf(actualFile)).isEqualTo(contentOf(expectedFile));
    }

}
