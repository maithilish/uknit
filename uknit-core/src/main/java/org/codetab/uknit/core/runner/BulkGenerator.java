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
import java.util.concurrent.TimeUnit;
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

import com.google.common.base.Stopwatch;
import com.google.common.base.Strings;
import com.google.common.graph.Traverser;

/**
 * Generates tests for the project.
 *
 * This runner is not for the users and it is mainly to test the uknit against
 * arbitrary projects.
 *
 * Refer devops.md for usage.
 *
 * @author Maithilish
 *
 */
public class BulkGenerator {

    private static final Logger LOG = LogManager.getLogger();

    private int errorCount, clzCount;
    private Map<String, Exception> errors;

    private Map<String, Long> timings;

    public static void main(final String[] args)
            throws URISyntaxException, IOException {
        BulkGenerator bulkGenerator = new BulkGenerator();
        bulkGenerator.process();
    }

    public void process() {

        Stopwatch runStopwatch = Stopwatch.createStarted();

        timings = new HashMap<>();

        changeLogFilterToInfo();

        UknitModule module = new UknitModule();
        DInjector di = new DInjector(module).instance(DInjector.class);
        Configs configs = di.instance(Configs.class);

        // process all methods
        configs.clearProperty("uknit.source.filter");
        configs.clearProperty("uknit.output.filter");
        configs.clearProperty("uknit.source.clz");
        configs.setProperty("uknit.source.error.ignore", "true");

        String baseDir = configs.getConfig("uknit.source.base");
        String srcDir = configs.getConfig("uknit.source.dir");
        String packg = configs.getConfig("uknit.source.package");
        Set<String> modulePaths = getModulePaths(baseDir, srcDir);

        errorCount = 0;
        clzCount = 0;
        errors = new HashMap<>();
        for (String modulePath : modulePaths) {

            List<File> filePaths =
                    getJavaFilePaths(baseDir, modulePath, srcDir, packg);
            clzCount += filePaths.size();
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
                    String fqName = String.format("%s.%s", pkg, fileName);
                    LOG.info("generate test for {}, file #{}", fqName, i);
                    Stopwatch testStopwatch = Stopwatch.createStarted();
                    runUknit(baseDir, modulePath, srcDir, pkg, fileName,
                            shutdownLogger);
                    testStopwatch.stop();
                    timings.put(fqName,
                            testStopwatch.elapsed(TimeUnit.MILLISECONDS));
                    LOG.info("generated test, time taken {}s",
                            testStopwatch.elapsed(TimeUnit.SECONDS));
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

        runStopwatch.stop();

        outputSummary(runStopwatch);

        if (configs.getConfig("uknit.logger.shutdown", true)) {
            LogManager.shutdown();
        }
    }

    private void outputSummary(final Stopwatch runStopwatch) {
        final int dashCount = 20;
        String dashes = Strings.repeat("-", dashCount);

        LOG.info("");
        LOG.info("{} Timing {}", dashes, dashes);

        List<Entry<String, Long>> sortedTimings =
                timings.entrySet().stream().sorted((e1, e2) -> {
                    Long t1 = e1.getValue();
                    Long t2 = e2.getValue();
                    return t1.compareTo(t2);
                }).collect(Collectors.toList());

        sortedTimings.forEach(e -> {
            LOG.info("{} : {}ms", e.getKey(), e.getValue());
        });

        LOG.info("");
        LOG.info("{} Errors {}", dashes, dashes);

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

        sortedErrors.forEach(e -> {
            LOG.error("{}", e.getKey());
            LOG.error("    -> {}", e.getValue().getMessage());
        });

        LOG.info("");
        LOG.info("{} Summary {}", dashes, dashes);
        LOG.info("Total Classes: {}, errors: {}", clzCount, errorCount);
        LOG.info("Time taken: {}s", runStopwatch.elapsed(TimeUnit.SECONDS));
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
            final String modulePath, final String srcDir, final String packg) {
        List<File> fileList = new ArrayList<>();
        File dir = Paths.get(baseDir, modulePath, srcDir).toFile();
        Traverser<File> traverser = com.google.common.io.Files.fileTraverser();
        for (File file : traverser.breadthFirst(dir)) {
            if (file.isFile()) {
                String fileName = file.toString();
                if (packg.equals("org.codetab.uknit.itest.example")) {
                    // package config is not set, add all java files
                    if (fileName.endsWith(".java")) {
                        fileList.add(file);
                    }
                } else {
                    // package config is set, add only java files of package
                    String packgPath = packg.replace(".", File.separator);
                    if (fileName.endsWith(".java")
                            && fileName.contains(packgPath)) {
                        fileList.add(file);
                    }
                }
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
