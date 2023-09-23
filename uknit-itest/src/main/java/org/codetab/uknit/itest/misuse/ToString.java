package org.codetab.uknit.itest.misuse;

import org.codetab.uknit.itest.misuse.Model.Duck;

/**
 *
 * Mockito cannot verify toString(). It is too often used behind of scenes (i.e.
 * during String concatenation, in IDE debugging views). Verifying it may give
 * inconsistent or hard to understand results.
 *
 * @author Maithilish
 *
 */
public class ToString {

    public String toStringNotAllowedInVerify(final Duck duck,
            final boolean canSwim) {
        if (canSwim) {
            return duck.toString();
        }
        return duck.toString();
    }
}
