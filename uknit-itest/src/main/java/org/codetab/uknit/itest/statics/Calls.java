package org.codetab.uknit.itest.statics;

import org.codetab.uknit.itest.statics.Model.Driver;
import org.codetab.uknit.itest.statics.Model.DriverWait;

/**
 * Static call in new object creation creates inferVar apple but not replace in
 * call.
 *
 * TODO - analyze the issue and resolve.
 *
 * @author Maithilish
 *
 */
class Calls {

    public DriverWait staticCallInInit(final Driver driver,
            final String timeout) {
        return new DriverWait(driver, Integer.parseInt(timeout));
    }

}
