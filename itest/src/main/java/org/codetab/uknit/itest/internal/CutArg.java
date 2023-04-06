package org.codetab.uknit.itest.internal;

import java.util.Date;

/**
 * Class under test is IMC arg.
 *
 * TODO L - cut should be spy instead of mock.
 *
 * @author m
 *
 */
public class CutArg {

    private final Date date = new Date();

    protected void copy(final CutArg target) {
        copy(target, date);
    }

    protected void copy(final CutArg target, final Date date) {
        target.date.getTime();
    }
}
