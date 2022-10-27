package org.codetab.uknit.core.runner;

import static java.util.Objects.nonNull;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.filter.ThresholdFilter;
import org.codetab.uknit.core.Uknit;
import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.di.DInjector;
import org.codetab.uknit.core.di.UknitModule;
import org.codetab.uknit.core.exception.CodeException;

import com.google.common.graph.Traverser;

/**
 * Generates tests for the project.
 * <p>
 * Derives modules and java class file from the configs - uknit.source.base
 * (project base dir) and uknit.source.dir (src/main/java) and generates tests
 * for all java class files in project or its modules.
 * <p>
 * As a precaution the generated test files lands in uknit/bulk folder under the
 * respective module instead of src/test/java dir and doesn't overwrite if test
 * exists in uknit/bulk.
 *
 * This runner is not for the users and it is mainly to test the uknit against
 * arbitrary projects.
 *
 * @author Maithilish
 *
 */
public class BulkGenerator {

    private static final Logger LOG = LogManager.getLogger();

    private int errorCount;
    private Map<String, Exception> errors;

    public static void main(final String[] args)
            throws URISyntaxException, IOException {
        BulkGenerator bulkGenerator = new BulkGenerator();
        bulkGenerator.process();
    }

    public void process() {

        changeLogFilterToInfo();

        UknitModule module = new UknitModule();
        DInjector di = new DInjector(module).instance(DInjector.class);
        Configs configs = di.instance(Configs.class);

        String baseDir = configs.getConfig("uknit.source.base");
        String srcDir = configs.getConfig("uknit.source.dir");
        Set<String> modulePaths = getModulePaths(baseDir, srcDir);

        errorCount = 0;
        errors = new HashMap<>();
        for (String modulePath : modulePaths) {
            List<File> filePaths =
                    getJavaFilePaths(baseDir, modulePath, srcDir);

            // System.out.printf("Module: %s No. of Java Files: %d%n",
            // modulePath,
            // filePaths.size());

            LOG.info("Module: {} No. of Java Files: {}", modulePath,
                    filePaths.size());

            for (int i = 0; i < filePaths.size(); i++) {
                File file = filePaths.get(i);
                String pkg = getPkgName(file, baseDir, modulePath, srcDir);
                String fileName = file.getName();

                LOG.info("=== Processing: {}.{} ===", pkg, fileName);

                try {
                    // for last file shutdown logger
                    boolean shutdownLogger = false;
                    if (i == filePaths.size() - 1) {
                        shutdownLogger = true;
                    }
                    runUknit(baseDir, modulePath, srcDir, pkg, fileName,
                            shutdownLogger);
                } catch (Exception e) {
                    final int repeat = 15;
                    String dash = "=".repeat(repeat);
                    String br = System.lineSeparator();
                    LOG.error("{}{} test not generated: {} {}{}", br, dash,
                            fileName, dash, br);
                    errors.put(String.join(".", pkg, fileName), e);
                    errorCount++;
                }

            }
        }
        System.out.printf("%n%nNo. of Errors: %d%n", errorCount);

        // sort on exception message and output errors
        List<Entry<String, Exception>> sortedErrors =
                errors.entrySet().stream().sorted((e1, e2) -> {
                    String m1 = e1.getValue().getMessage();
                    String m2 = e2.getValue().getMessage();
                    if (nonNull(m1) && nonNull(m2)) {
                        return m1.compareTo(m2);
                    } else {
                        return 0;
                    }
                }).collect(Collectors.toList());

        sortedErrors.forEach(e -> System.out.printf("%s %s%n", e.getKey(),
                e.getValue().getMessage()));
    }

    private void runUknit(final String baseDir, final String modulePath,
            final String srcDir, final String pkg, final String fileName,
            final boolean shutdownLogger) {

        UknitModule module = new UknitModule();
        DInjector di = new DInjector(module).instance(DInjector.class);
        Configs configs = di.instance(Configs.class);

        String clzName = fileName.replaceAll(".java$", "");
        configs.setProperty("uknit.source.base",
                String.join("/", baseDir, modulePath));
        configs.setProperty("uknit.source.dir", srcDir);
        configs.setProperty("uknit.source.package", pkg);
        configs.setProperty("uknit.source.clz", clzName);
        configs.setProperty("uknit.logger.shutdown",
                String.valueOf(shutdownLogger));

        configs.setProperty("uknit.output.dir", "uknit/bulk");

        Uknit uknit = di.instance(Uknit.class);
        uknit.run();
    }

    private String getPkgName(final File file, final String baseDir,
            final String modulePath, final String srcDir) {
        String path = file.getAbsolutePath();
        String pathToPkg;
        if (modulePath.isBlank()) {
            pathToPkg = String.format("%s/%s/", baseDir, srcDir);
        } else {
            pathToPkg = String.format("%s/%s/%s/", baseDir, modulePath, srcDir);
        }
        if (!path.startsWith(pathToPkg)) {
            throw new CodeException(String.format(
                    "unable to get pkg, path %s doesn't starts with %s", path,
                    pathToPkg));
        }
        String regEx = "^" + pathToPkg;
        path = path.replaceFirst(regEx, "");
        path = path.replaceFirst(String.format("/%s$", file.getName()), "");
        String pkg = path.replace("/", ".");
        return pkg;
    }

    private Set<String> getModulePaths(final String baseDir,
            final String srcDir) {
        Set<String> moduleSet = new HashSet<>();
        File projDir = Paths.get(baseDir).toFile();

        Traverser<File> traverser = com.google.common.io.Files.fileTraverser();
        for (File moduleSrcDir : traverser.breadthFirst(projDir)) {
            if (moduleSrcDir.isDirectory()
                    && moduleSrcDir.getPath().contains(srcDir)) {
                String modulePath = moduleSrcDir.getPath();
                int endIndex = modulePath.indexOf(srcDir);
                modulePath = modulePath.substring(0, endIndex - 1);
                modulePath = modulePath
                        .replaceFirst(String.format("^%s/", baseDir), "");
                if (baseDir.equals(modulePath)) {
                    modulePath = "";
                }
                moduleSet.add(modulePath);
            }
        }
        return moduleSet;
    }

    private List<File> getJavaFilePaths(final String baseDir,
            final String modulePath, final String srcDir) {
        List<File> fileList = new ArrayList<>();
        File dir = Paths.get(baseDir, modulePath, srcDir).toFile();
        Traverser<File> traverser = com.google.common.io.Files.fileTraverser();
        for (File file : traverser.breadthFirst(dir)) {
            if (file.isFile() && file.toString().endsWith(".java")) {
                fileList.add(file);
            }
        }
        return fileList;
    }

    private void changeLogFilterToInfo() {
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        ConsoleAppender appender =
                context.getConfiguration().getAppender("CONSOLE");
        appender.removeFilter(appender.getFilter());
        ThresholdFilter infoFilter = ThresholdFilter.createFilter(Level.INFO,
                Filter.Result.ACCEPT, Filter.Result.DENY);
        appender.addFilter(infoFilter);
    }
}
