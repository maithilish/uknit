package org.codetab.uknit.core.util;

import java.io.CharArrayWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class IOUtils {

    public char[] toCharArray(final String fileName, final Charset charset)
            throws IOException {
        final int EOF = -1;
        char[] buffer = new char[4096];
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
}
