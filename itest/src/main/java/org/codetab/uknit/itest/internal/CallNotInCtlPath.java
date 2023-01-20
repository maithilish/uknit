package org.codetab.uknit.itest.internal;

import static java.util.Objects.isNull;

import java.util.Date;

/**
 * TODO N - enable itest.
 *
 * @author Maithilish
 *
 */
class CallNotInCtlPath {

    private Payload payload;

    public Date callNotInPath() {
        if (isNull(payload)) {
            return null;
        } else {
            return getDate();
        }
    }

    private Date getDate() {
        return payload.getDate();
    }

    interface Payload {
        Date getDate();
    }
}
