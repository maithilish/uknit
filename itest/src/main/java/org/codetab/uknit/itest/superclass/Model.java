package org.codetab.uknit.itest.superclass;

import java.util.Date;

public class Model {

}

class SuperBlendVar {
    // date is mock
    public Date getDate(final Date date) {
        return date;
    }

    // string is real
    public String getString(final String string) {
        return string;
    }
}

class AFactory {
    public Date getDate() {
        return new Date();
    }

    public String getString() {
        return new String();
    }
}

