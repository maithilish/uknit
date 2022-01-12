package org.codetab.uknit.itest.model;

public class Car extends Vehicle {

    private int numberOfSeats = 0;

    public int getNumberOfSeats() {
        return this.numberOfSeats;
    }

    public String getLicensePlate() {
        return this.licensePlate;
    }

    @Override
    public void setLicensePlate(final String license) {
        super.setLicensePlate(license);
    }
}
