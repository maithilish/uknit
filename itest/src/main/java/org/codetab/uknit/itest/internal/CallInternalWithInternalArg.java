package org.codetab.uknit.itest.internal;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

public class CallInternalWithInternalArg {

    // first test
    public int callA(final Date date) {
        return internalA(internalB(date));
    }

    private Instant internalB(final Date date) {
        return date.toInstant();
    }

    private int internalA(final Instant instant1) {
        return instant1.getNano();
    }

    // second test
    public boolean callX(final DateFormat dateFormat, final String dateStr1,
            final Date date2) throws ParseException {
        return internalX(internalY(dateFormat, dateStr1), date2);
    }

    private Date internalY(final DateFormat dateFormat, final String dateStr1)
            throws ParseException {
        return dateFormat.parse(dateStr1);
    }

    private boolean internalX(final Date date1, final Date date2) {
        return date1.after(date2);
    }
}
