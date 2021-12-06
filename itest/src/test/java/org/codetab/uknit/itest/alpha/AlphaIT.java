package org.codetab.uknit.itest.alpha;

import java.io.File;
import java.io.IOException;

import org.codetab.uknit.itest.ITBase;
import org.junit.jupiter.api.Test;

public class AlphaIT extends ITBase {

    @Test
    public void test() throws IOException {

        configure();

        generateTestClass();

        File actualFile = getActualFile();

        display(actualFile, false);
    }

}
