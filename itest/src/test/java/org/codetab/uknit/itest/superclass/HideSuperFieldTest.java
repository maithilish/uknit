package org.codetab.uknit.itest.superclass;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class HideSuperFieldTest {
    @InjectMocks
    private HideSuperField hideSuperField;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSetLicensePlate() {
        String license = "Baz";
        hideSuperField.setLicensePlate(license);

        Object actual = hideSuperField.getLicensePlate();

        assertSame(license, actual);
    }

    @Test
    public void testGetLicensePlate() {
        String licensePlate = "Foo";
        hideSuperField.setLicensePlate(licensePlate);

        String actual = hideSuperField.getLicensePlate();

        assertEquals(licensePlate, actual);
    }

    @Test
    public void testUpdateLicensePlate() {
        String license = "Baz";
        hideSuperField.updateLicensePlate(license);
    }
}
