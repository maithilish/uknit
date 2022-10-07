package org.codetab.uknit.core.util;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public class IOUtils {

    private static final int EOF = -1;
    private static final int BUF_SIZE = 4096;

    public char[] toCharArray(final String fileName, final Charset charset)
            throws IOException {
        char[] buffer = new char[BUF_SIZE];
        CharArrayWriter writer = new CharArrayWriter();
        try (InputStreamReader reader =
                new InputStreamReader(new FileInputStream(fileName), charset)) {
            int n;
            while (EOF != (n = reader.read(buffer))) {
                writer.write(buffer, 0, n);
            }
            return writer.toCharArray();
        }
    }

    /**
     * Get platform neutral path to a source file. Note: pkg is package name
     * such as org.foo.bar.
     * @param base
     *            project dir, /home/x/eclipse-workspace/uknit/core
     * @param dir
     *            source dir, src/main/java
     * @param pkg
     *            package name, org.foo.bar
     * @param srcFile
     *            java file name, Foo.java
     * @return
     */
    public Path asSrcFilePath(final String base, final String dir,
            final String pkg, final String srcFile) {
        List<String> list = Lists.newArrayList(Splitter.on(".").split(pkg));
        list.add(0, dir);
        list.add(srcFile);
        String[] more = list.stream().toArray(String[]::new);
        return java.nio.file.Paths.get(base, more);
    }

    /**
     * Get platform neutral path to a source folder. Note: pkg is package name
     * such as org.foo.bar.
     * @param base
     *            project dir, /home/x/eclipse-workspace/uknit/core
     * @param dir
     *            source dir, src/main/java
     * @param pkg
     *            package name, org.foo.bar
     * @return
     */
    public Path asSrcFolderPath(final String base, final String dir,
            final String pkg) {
        List<String> list = Lists.newArrayList(Splitter.on(".").split(pkg));
        list.add(0, dir);
        String[] more = list.stream().toArray(String[]::new);
        return java.nio.file.Paths.get(base, more);
    }

    public List<File> searchSource(final String dirName, final String ext,
            final String regex) {
        File dir = new File(dirName);
        List<File> fileList = new ArrayList<>();

        String suffix = "." + ext;
        File[] files = dir.listFiles((d, name) -> name.endsWith(suffix));
        for (File file : files) {
            if (file.isFile()) {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(new FileInputStream(file)))) {
                    String line;
                    boolean inBlockComment = false;
                    while ((line = br.readLine()) != null) {
                        boolean processLine = true;
                        if (line.contains("/*")) {
                            inBlockComment = true;
                        }
                        if (line.contains("//") || inBlockComment) {
                            processLine = false;
                        }
                        if (processLine && line.contains(regex)) {
                            fileList.add(file);
                            break;
                        }
                        if (line.contains("*/")) {
                            inBlockComment = false;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileList;
    }

    public boolean dirExists(final String dir) {
        return new File(dir).exists();
    }
}
