package org.codetab.uknit.jtest.alpha;

import java.util.Date;
import java.util.List;

public class SuperAlpha extends Beta {

    public Date foo(final List<Date> dates) {
        Date selectedDate = null;
        for (Date date : dates) {
            selectedDate = date;
        }
        return selectedDate;
    }
}

class Beta {

    private Date date;

    public Date getDate() {
        return date;
    }
}
