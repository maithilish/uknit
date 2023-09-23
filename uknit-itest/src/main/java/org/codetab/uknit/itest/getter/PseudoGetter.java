package org.codetab.uknit.itest.getter;

import java.util.Date;

/**
 * Methods similar to getter but not POJO getter.
 *
 * @author Maithilish
 *
 */
class PseudoGetter {

    private Payload payload;

    public Date getDate() {
        return payload.getDate();
    }

    interface Payload {
        Date getDate();
    }

}
