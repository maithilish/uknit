package org.codetab.uknit.itest.superclass;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class SuperCallAsArg extends SuperFactory {

    public boolean callInternalCreateArgInSuper() {
        return internalFoo(super.getDate(), super.getDate());
    }

    private boolean internalFoo(final Date date1, final Date date2) {
        return date1.after(date2);
    }

    public boolean callInternalGetArgFromSuper(final DateFormat dateFormat,
            final String dateStr1, final Date date2) throws ParseException {
        return internalBar(super.getDate(dateFormat, dateStr1), date2);
    }

    private boolean internalBar(final Date date1, final Date date2) {
        return date1.after(date2);
    }
}

class SuperFactory {

    public Date getDate() {
        return new Date();
    }

    protected Date getDate(final DateFormat dateFormat, final String dateStr)
            throws ParseException {
        return dateFormat.parse(dateStr);
    }
}
