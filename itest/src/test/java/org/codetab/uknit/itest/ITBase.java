package org.codetab.uknit.itest;

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

public class ITBase {

    private Configs configs;
    private Map<String, String> transientConfigMap = new HashMap<>();

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

        // destination
        configs.setProperty("uknit.dest", "target/itest");

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

        cleanDestDir(Paths.get(configs.getConfig("uknit.dest")));

        UknitModule module = new UknitModule();
        DInjector di = new DInjector(module).instance(DInjector.class);
        Uknit uknit = di.instance(Uknit.class);
        uknit.run();
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
        String destDir = configs.getConfig("uknit.dest");
        String testClassName = configs.getConfig("uknit.testClassName");

        return new File(String.join("/", ws, destDir, testClassName + ".java"));
    }

    protected void print(final File file) throws IOException {
        Files.copy(file.toPath(), System.out);
    }

    public static void cleanDestDir(final Path directory) throws IOException {
        final File[] files = directory.toFile().listFiles();
        if (nonNull(files)) {
            for (File f : files) {
                f.delete();
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
