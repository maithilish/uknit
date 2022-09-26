package org.codetab.uknit.itest.create;

/**
 * Factory test creates object of same type and tests with assertSame().
 *
 * TODO - explore better way to test object created by Factory.
 *
 * @author m
 *
 */
public class Factory {
    public Driver createDriver(final String name) {
        return new Driver(name);
    }

    class Driver {
        Driver(final String name) {
        }
    }
}
