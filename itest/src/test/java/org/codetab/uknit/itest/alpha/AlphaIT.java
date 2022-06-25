package org.codetab.uknit.itest.alpha;

import static java.util.Objects.isNull;

import java.io.File;
import java.io.IOException;

import org.codetab.uknit.itest.ITBase;
import org.junit.jupiter.api.Test;

public class AlphaIT extends ITBase {

    @Test
    public void test() throws IOException {

        System.setProperty("uknit.configs.loadUserDefined", "true");

        // don't run in cli
        String alphaRun = System.getProperty("uknit.alpha.run");
        if (isNull(alphaRun) || !alphaRun.equalsIgnoreCase("true")) {
            return;
        }

        configure();

        // String listKey = "uknit.createInstance.List";
        // String listConfig = getConfig(listKey);
        // addConfig(listKey, "mock"); // mock the list
        //
        // String streamKey = "uknit.createInstance.Stream";
        // String streamConfig = getConfig(streamKey);
        // addConfig(streamKey, "mock"); // mock the stream

        generateTestClass();

        File actualFile = getActualFile();

        display(actualFile, false);
    }

}
