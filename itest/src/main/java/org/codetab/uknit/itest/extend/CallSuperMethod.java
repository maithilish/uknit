package org.codetab.uknit.itest.extend;

import org.codetab.uknit.itest.model.Vehicle;

public class CallSuperMethod extends Vehicle {

    @Override
    public void setLicensePlate(final String license) {
        super.setLicensePlate(license);
    }
}
