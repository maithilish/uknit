package org.codetab.uknit.itest.alpha;

import static java.util.Objects.isNull;

import java.io.File;
import java.io.IOException;

import org.codetab.uknit.itest.ITBase;
import org.junit.jupiter.api.Test;

/**
 * To find super class file place SuperAlpha.java in
 * org.codetab.uknit.itest.alpha package in itest/src/main/java.
 * <p>
 * To output test method in console set VM argument -Duknit.alpha.run=true in
 * Run configuration and not here in the test.
 *
 * @author Maithilish
 *
 */
class SuperAlphaIT extends ITBase {

    @Test
    public void test() throws IOException {

        System.setProperty("uknit.configs.loadUserDefined", "true");
        // uknit and itest uses 5
        System.setProperty("uknit.profile.test.framework", "junit5");

        // don't run in cli - in cli system prop is not set so return
        String alphaRun = System.getProperty("uknit.alpha.run");
        if (isNull(alphaRun) || !alphaRun.equalsIgnoreCase("true")) {
            return;
        }

        configure();

        // addTransientConfig("uknit.createInstance.PayloadReal",
        // "new PayloadReal();");
        // addTransientConfig("uknit.createInstance.InfoReal", "new
        // InfoReal();");

        // addTransientConfig("uknit.createInstance.List", "mock");
        // addTransientConfig("uknit.createInstance.Stream", "mock");

        try {
            generateTestClass();
        } finally {
            restoreTransientConfigs();
        }

        File actualFile = getActualFile();

        display(actualFile, false);
    }

}
