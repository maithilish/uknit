package org.codetab.uknit.itest.getset.detect;

import java.util.Date;

/**
 * For methods similar to getter, uKnit wrongly calls setter method.
 *
 * TODO - fix the error.
 *
 * @author m
 *
 */
public class PseudoGetter {

    private Payload payload;

    public Date getDate() {
        return payload.getDate();
    }

    interface Payload {
        Date getDate();
    }

}
