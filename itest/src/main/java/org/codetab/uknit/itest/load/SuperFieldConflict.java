package org.codetab.uknit.itest.load;

import java.util.Date;
import java.util.List;

/**
 * Super field date and loop var date conflicts.
 *
 * TODO - analyze the issue and resolve.
 *
 * @author m
 *
 */
class SuperFieldConflict extends SuperField {

    public Date conflicts(final List<Date> dates) {
        Date selectedDate = null;
        for (Date date : dates) {
            selectedDate = date;
        }
        return selectedDate;
    }

    public Date noConflict(final List<Date> dates) {
        Date selectedDate = null;
        for (Date someDate : dates) {
            selectedDate = someDate;
        }
        return selectedDate;
    }
}

class SuperField {

    private Date date;

    public Date getDate() {
        return date;
    }
}
