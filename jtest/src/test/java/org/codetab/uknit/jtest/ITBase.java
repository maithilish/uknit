package org.codetab.uknit.jtest;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.codetab.uknit.core.Uknit;
import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.di.DInjector;
import org.codetab.uknit.core.di.UknitModule;
import org.codetab.uknit.core.exception.CriticalException;

import com.google.common.io.MoreFiles;
import com.google.common.io.RecursiveDeleteOption;

public class ITBase {

    private Configs configs;
    private Map<String, String> transientConfigMap = new HashMap<>();

    private String testCat = "jtest";

    protected String getTestCasePkg() {
        return this.getClass().getPackage().getName();
    }

    protected String getClzName() {
        return this.getClass().getSimpleName().replace("IT", "");
    }

    protected void configure() {
        String testCasePkg = getTestCasePkg();
        String className = getClzName();
        configure(testCasePkg, className);
    }

    protected void configure(final String testCasePkg, final String clzName) {
        String testClzName = clzName.concat("Test");
        String expFile = clzName.concat(".exp");
        configure(testCasePkg, clzName, testClzName, expFile);
    }

    protected void configure(final String testCasePkg, final String srcFile,
            final String testClzName, final String expFile) {

        /*
         * itest against default configs, ignore user defined. If not set by the
         * itest (subclass) then set now before creating Configs instance.
         */
        if (isNull(System.getProperty("uknit.configs.loadUserDefined"))) {
            System.setProperty("uknit.configs.loadUserDefined", "false");
        }

        /*
         * Configs uses enum Configuration for configs and it is singleton. Both
         * new Configs() or DI instance share the same instance of it.
         */
        configs = new Configs();

        // workspace
        configs.setProperty("uknit.source.base",
                System.getProperty("user.dir"));

        // src
        configs.setProperty("uknit.source.dir", "src/main/java");
        configs.setProperty("uknit.source.package", testCasePkg);
        configs.setProperty("uknit.testDir",
                "src/test/java/" + testCasePkg.replace(".", "/"));

        configs.setProperty("uknit.source.clz", srcFile);

        configs.setProperty("uknit.expectedFile", expFile);

        // Suppress console output
        configs.setProperty("uknit.run.mode", "test");
        configs.setProperty("uknit.output.file.overwrite", "false");

        // output dir
        configs.setProperty("uknit.output.dir", "target/" + testCat);

        configs.setProperty("uknit.testClassName", testClzName);
    }

    /**
     * Add transient config or change a config for a test.
     * <p>
     * Configs uses enum Configuration for configs and it is singleton. So both
     * new Configs() and DI instance share the same instance. It is created only
     * once for all test cases! If any config is added/modified for a test,
     * revert it back after generateTestClass() call. Or use transient config
     * for easy revert.
     *
     * @param key
     * @param value
     */
    protected void addConfig(final String key, final String value) {
        configs.setProperty(key, value);
    }

    protected void addTransientConfig(final String key, final String value) {
        String oldValue = configs.getConfig(key);
        configs.setProperty(key, value);
        transientConfigMap.put(key, oldValue); // save for revert
    }

    protected void restoreTransientConfigs() {
        for (String key : transientConfigMap.keySet()) {
            String oldValue = transientConfigMap.get(key);
            if (isNull(oldValue)) {
                configs.clearProperty(key);
            } else {
                configs.setProperty(key, oldValue);
            }
        }
    }

    protected String getConfig(final String key) {
        return configs.getConfig(key);
    }

    protected void generateTestClass() throws IOException {

        sanityCheckConfigs();
        cleanOutputDir(Paths.get(configs.getConfig("uknit.output.dir")));

        UknitModule module = new UknitModule();
        DInjector di = new DInjector(module).instance(DInjector.class);
        Uknit uknit = di.instance(Uknit.class);
        uknit.run();
    }

    /**
     * Ensure itest output goes to target/itest and not overwritten and doesn't
     * land in src/test/java which holds corrected tests.
     */
    private void sanityCheckConfigs() {
        String msg = null;
        if (!configs.getConfig("uknit.output.dir")
                .equals("target/" + testCat)) {
            msg = String.format("uknit.output.dir should be target/%s for %s",
                    testCat);
        }
        if (configs.getConfig("uknit.output.file.overwrite")
                .equalsIgnoreCase("true")) {
            msg = String.format(
                    "uknit.output.file.overwrite should be false for %s",
                    testCat);
        }
        if (nonNull(msg)) {
            throw new CriticalException(msg);
        }
    }

    protected File getExpectedFile() {
        String ws = configs.getConfig("uknit.source.base");
        String srcDir = configs.getConfig("uknit.testDir");

        String expectedFile = configs.getConfig("uknit.expectedFile");
        if (isNull(expectedFile)) {
            expectedFile = "Test.exp";
        }

        return new File(String.join("/", ws, srcDir, expectedFile));
    }

    protected File getActualFile() {
        String ws = configs.getConfig("uknit.source.base");
        String outputDir = configs.getConfig("uknit.output.dir");
        String pkg = configs.getConfig("uknit.source.package");
        String testClassName = configs.getConfig("uknit.testClassName");

        return new File(String.join("/", ws, outputDir, pkg.replace(".", "/"),
                testClassName + ".java"));
    }

    protected void print(final File file) throws IOException {
        Files.copy(file.toPath(), System.out);
    }

    public static void cleanOutputDir(final Path directory) throws IOException {
        final File[] files = directory.toFile().listFiles();
        if (nonNull(files)) {
            for (File file : files) {
                MoreFiles.deleteRecursively(file.toPath(),
                        RecursiveDeleteOption.ALLOW_INSECURE);
            }
        }
    }

    protected void display(final File file, final boolean exit) {
        try {
            if (file.exists()) {
                Files.copy(file.toPath(), System.out);
            } else {
                System.out.printf("file not generated: %s%n", file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (exit) {
                System.exit(0);
            }
        }
    }
}
