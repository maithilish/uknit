package org.codetab.uknit.core.util;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

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

    public String getClassFilePath(final String workspace, final String destDir,
            final String clzName) {
        return String.join("/", workspace, destDir, clzName + ".java");
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
