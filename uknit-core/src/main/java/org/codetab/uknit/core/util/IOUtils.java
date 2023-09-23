package org.codetab.uknit.core.util;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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

    /**
     * Search the regex in all the files of the ext in the dir and return list
     * of matching files.
     *
     * @param dirName
     * @param ext
     * @param regex
     * @return
     */
    public List<File> searchSource(final String dirName, final String ext,
            final String regex) {

        List<File> fileList = new ArrayList<>();

        Pattern pattern = Pattern.compile(regex);

        File dir = new File(dirName);
        File[] files = dir.listFiles((d, name) -> name.endsWith("." + ext));

        for (File file : files) {
            if (file.isFile()) {
                try {
                    StringBuilder content = readFile(file);
                    if (pattern.matcher(content).find()) {
                        fileList.add(file);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileList;
    }

    /**
     * Read a text file and collect and return the contents as a StringBuilder.
     *
     * @param file
     * @return
     * @throws IOException
     */
    public StringBuilder readFile(final File file) throws IOException {
        StringBuilder content = new StringBuilder();
        final int size = 4 * 1024;
        char[] buffer = new char[size];
        try (Reader reader = new InputStreamReader(new FileInputStream(file))) {
            int length = -1;
            while ((length = reader.read(buffer, 0, buffer.length)) >= 0) {
                content.append(buffer, 0, length);
            }
        }
        return content;
    }

    /**
     * Is dir exists.
     *
     * @param dir
     * @return
     */
    public boolean dirExists(final String dir) {
        return new File(dir).exists();
    }

    /**
     * Find java file and if exists return its path
     *
     * @param searchDir
     * @param clzName
     * @return
     * @throws FileNotFoundException
     */
    public String findFile(final String searchDir, final String clzName)
            throws FileNotFoundException {
        Path path = Path.of(searchDir, clzName);
        path = path.resolveSibling(path.getFileName() + ".java");
        File javaFile = path.toFile();
        if (javaFile.exists() && javaFile.isFile()) {
            return javaFile.toString();
        } else {
            throw new FileNotFoundException(path.toString());
        }
    }
}
