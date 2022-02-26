package org.codetab.uknit.itest.superclass;

import java.util.Date;

public class SuperCallAsArg extends SuperFactory {

    public boolean callInternalFoo() {
        return internalFoo(super.getDate(), super.getDate());
    }

    private boolean internalFoo(final Date date1, final Date date2) {
        return date1.after(date2);
    }
}

class SuperFactory {

    public Date getDate() {
        return new Date();
    }
}
