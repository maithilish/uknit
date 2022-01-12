package org.codetab.uknit.itest.extend;

import org.codetab.uknit.itest.model.Vehicle;

public class HideSuperField extends Vehicle {

    protected String licensePlate = null;

    @Override
    public void setLicensePlate(final String license) {
        super.setLicensePlate(license);
    }

    @Override
    public String getLicensePlate() {
        return super.getLicensePlate();
    }

    public void updateLicensePlate(final String license) {
        this.licensePlate = license;
    }
}
