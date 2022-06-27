package org.codetab.uknit.itest.superclass;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

import java.io.File;
import java.io.IOException;

import org.codetab.uknit.itest.ITBase;
import org.junit.jupiter.api.Test;

public class MultiGetRealIT extends ITBase {

    @Test
    public void test() throws IOException {

        configure();

        addTransientConfig("uknit.createInstance.PayloadReal",
                "new PayloadReal();");
        addTransientConfig("uknit.createInstance.InfoReal", "new InfoReal();");

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
