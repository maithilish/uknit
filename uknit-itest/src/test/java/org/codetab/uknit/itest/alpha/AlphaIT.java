package org.codetab.uknit.itest.alpha;

import static java.util.Objects.isNull;

import java.io.File;
import java.io.IOException;

import org.codetab.uknit.itest.ITBase;
import org.junit.jupiter.api.Test;

/**
 * Place Alpha.java in org.codetab.uknit.itest.alpha package in
 * itest/src/test/java to avoid scrolling in Package Explorer.
 * <p>
 * To output test method in console set VM argument -Duknit.alpha.run=true in
 * Run configuration and not here in the test.
 *
 * @author Maithilish
 *
 */
class AlphaIT extends ITBase {

    @Test
    public void test() throws IOException {

        System.setProperty("uknit.configs.loadUserDefined", "true");
        // uknit and itest uses 5
        System.setProperty("uknit.profile.test.framework", "junit5");

        // don't run in cli
        String alphaRun = System.getProperty("uknit.alpha.run");
        if (isNull(alphaRun) || !alphaRun.equalsIgnoreCase("true")) {
            return;
        }

        configure();

        clearConfig("uknit.source.filter");
        clearConfig("uknit.output.filter");

        // addTransientConfig("uknit.source.method",
        // "testIfPlusIfFooIfCanSwimElseDone");

        addTransientConfig("uknit.controlFlow.method.split", "true");
        // addTransientConfig("uknit.createInstance.PrintPayload",
        // "new PrintPayload()");

        try {
            generateTestClass();
        } finally {
            restoreTransientConfigs();
        }

        // create AlphaTest.java
        boolean overwrite = true;
        createTestFile(overwrite);

        File actualFile = getActualFile();

        display(actualFile, false);
    }

}
