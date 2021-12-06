package org.codetab.uknit.itest.invoke;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class CallStatic {

    public int callStatic(final LocalDate date) {
        return date.compareTo(LocalDate.now());
    }

    public Date staticCallReturn() {
        return Calendar.getInstance().getTime();
    }

    public Date realCallReturn() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        return cal.getTime();
    }
}
