package org.codetab.uknit.jtest.array;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

import java.io.File;
import java.io.IOException;

import org.codetab.uknit.jtest.ITBase;
import org.junit.jupiter.api.Test;

public class ArrayAccessIT extends ITBase {

    @Test
    public void test() throws IOException {

        configure();

        generateTestClass();

        File actualFile = getActualFile();
        File expectedFile = getExpectedFile();

        assertThat(actualFile).exists();
        assertThat(expectedFile).exists();

        assertThat(contentOf(actualFile)).isEqualTo(contentOf(expectedFile));
    }
}
