package org.codetab.uknit.itest.flow.simple;

import java.util.Date;

class IfThen {

    public int ifThen(final Date date1, final Date date2) {
        int result = 0;
        if (date1.compareTo(date2) > 0) {
            result = 1;
        }
        return result;
    }

    public int ifThenElse(final Date date1, final Date date2,
            final Date date3) {
        int result = 0;
        if (date1.compareTo(date2) > 0) {
            result = 1;
        } else if (date2.compareTo(date3) > 0) {
            result = 2;
        } else {
            result = 3;
        }
        return result;
    }
}
