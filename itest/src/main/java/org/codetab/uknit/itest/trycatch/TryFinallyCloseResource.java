package org.codetab.uknit.itest.trycatch;

import java.io.BufferedReader;
import java.io.IOException;

import javax.inject.Inject;

/**
 *
 * TODO H - fix multiple var conflict, two exception e are created. Enable var
 * for e.getMessage() (kiwi and cherry).
 *
 * @author m
 *
 */
public class TryFinallyCloseResource {

    @Inject
    private Logger log;

    public void tryFinallyCloseResource(final Io io) {
        BufferedReader br = null;
        String line;

        try {
            log.debug("Entering try block");
            br = io.bufferedFileReader("foo.txt");
            while ((line = br.readLine()) != null) {
                log.debug("Line =>" + line);
            }
        } catch (IOException e) {
            log.debug("IOException in try block =>" + e.getMessage());
        } finally {
            log.debug("Entering finally block");
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                log.debug("IOException in finally block =>" + e.getMessage());
            }

        }
    }

    interface Io {
        BufferedReader bufferedFileReader(String path);
    }

    interface Logger {
        void debug(String msg);
    }
}
