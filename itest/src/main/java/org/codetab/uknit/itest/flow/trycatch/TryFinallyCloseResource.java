package org.codetab.uknit.itest.flow.trycatch;

import java.io.BufferedReader;
import java.io.IOException;

import javax.inject.Inject;

import org.codetab.uknit.itest.flow.trycatch.Model.Io;
import org.codetab.uknit.itest.flow.trycatch.Model.Logger;

/**
 *
 * TODO H - fix multiple var conflict, two exception e are created. Enable var
 * for e.getMessage() (kiwi and cherry).
 *
 * TODO L - test needs some updates, try to improve the test
 *
 * @author Maithilish
 *
 */
class TryFinallyCloseResource {

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
}
