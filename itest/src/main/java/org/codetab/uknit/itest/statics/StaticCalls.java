package org.codetab.uknit.itest.statics;

/**
 * Static call in new object creation creates inferVar apple but not replace in
 * call.
 *
 * TODO - analyze the issue and resolve.
 *
 * @author m
 *
 */
public class StaticCalls {

    public DriverWait staticCallInInit(final Driver driver,
            final String timeout) {
        return new DriverWait(driver, Integer.parseInt(timeout));
    }

}

class DriverWait {
    DriverWait(final Driver driver, final int parseInt) {
    }
}

interface Driver {
}
