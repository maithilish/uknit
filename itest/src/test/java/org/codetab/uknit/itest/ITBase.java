package org.codetab.uknit.itest;

import static java.util.Objects.isNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.codetab.uknit.core.Uknit;
import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.di.DInjector;
import org.codetab.uknit.core.di.UknitModule;

public class ITBase {

    private Configs configs;

    protected String getTestCaseDir() {
        return this.getClass().getPackage().getName().replace(".", "/");
    }

    protected String getClassName() {
        return this.getClass().getSimpleName().replace("IT", "");
    }

    protected void configure() {
        String testCaseDir = getTestCaseDir();
        String className = getClassName();
        configure(testCaseDir, className);
    }

    protected void configure(final String testCaseDir, final String className) {
        String testClassName = className.concat("Test");
        String srcFile = className.concat(".java");
        String expFile = className.concat(".exp");
        configure(testCaseDir, srcFile, testClassName, expFile);
    }

    protected void configure(final String testCaseDir, final String srcFile,
            final String testClassName, final String expFile) {
        /*
         * Configs uses enum Configuration for configs and it is singleton and
         * both new Configs() and DI instance share the same instance.
         */
        configs = new Configs();

        // workspace
        configs.setProperty("uknit.workspace", System.getProperty("user.dir"));

        // src
        configs.setProperty("uknit.dir", "src/main/java/" + testCaseDir);
        configs.setProperty("uknit.testDir", "src/test/java/" + testCaseDir);

        configs.setProperty("uknit.file", srcFile);

        configs.setProperty("uknit.expectedFile", expFile);

        // destination
        configs.setProperty("uknit.dest", "target/itest");

        configs.setProperty("uknit.testClassName", testClassName);
    }

    /**
     * Add transient config or change a config for a test.
     * <p>
     * Configs uses enum Configuration for configs and it is singleton. So both
     * new Configs() and DI instance share the same instance. It is created only
     * once for all test cases! If any config is added/modified for a test,
     * revert it back after generateTestClass() call.
     *
     * @param key
     * @param value
     */
    protected void addConfig(final String key, final String value) {
        configs.setProperty(key, value);
    }

    protected void generateTestClass() throws IOException {
        UknitModule module = new UknitModule();
        DInjector di = new DInjector(module).instance(DInjector.class);
        Uknit uknit = di.instance(Uknit.class);
        uknit.run();
    }

    protected File getExpectedFile() {
        String ws = configs.getConfig("uknit.workspace");
        String srcDir = configs.getConfig("uknit.testDir");

        String expectedFile = configs.getConfig("uknit.expectedFile");
        if (isNull(expectedFile)) {
            expectedFile = "Test.exp";
        }

        return new File(String.join("/", ws, srcDir, expectedFile));
    }

    protected File getActualFile() {
        String ws = configs.getConfig("uknit.workspace");
        String destDir = configs.getConfig("uknit.dest");
        String testClassName = configs.getConfig("uknit.testClassName");

        return new File(String.join("/", ws, destDir, testClassName + ".java"));
    }

    protected void print(final File file) throws IOException {
        Files.copy(file.toPath(), System.out);
    }

    protected void display(final File file, final boolean exit) {
        try {
            Files.copy(file.toPath(), System.out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (exit) {
                System.exit(0);
            }
        }
    }
}
