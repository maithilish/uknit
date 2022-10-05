package org.codetab.uknit.itest.trycatch;

import java.io.BufferedReader;
import java.io.IOException;

import javax.inject.Inject;

/**
 *
 * TODO N - enable infer var for e.getMessage() in try test.
 *
 * @author m
 *
 */
public class TryWithResource {

    @Inject
    private Logger log;

    public void tryWithResource(final Io io) {
        String line;
        try (BufferedReader br = io.bufferedFileReader("foo.txt")) {
            while ((line = br.readLine()) != null) {
                log.debug("Line =>" + line);
            }
        } catch (IOException e) {
            log.debug("catch block" + e.getMessage());
        }
    }

    interface Io {
        BufferedReader bufferedFileReader(String path);
    }

    interface Logger {
        void debug(String msg);
    }
}
