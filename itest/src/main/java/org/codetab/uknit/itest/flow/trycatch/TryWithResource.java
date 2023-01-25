package org.codetab.uknit.itest.flow.trycatch;

import java.io.BufferedReader;
import java.io.IOException;

import javax.inject.Inject;

import org.codetab.uknit.itest.flow.trycatch.Model.Io;
import org.codetab.uknit.itest.flow.trycatch.Model.Logger;

/**
 *
 * TODO N - enable infer var for e.getMessage() in try test.
 *
 * @author Maithilish
 *
 */
class TryWithResource {

    @Inject
    private Logger log;

    // STEPIN - add thenReturn(null) to when stmt
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
}
