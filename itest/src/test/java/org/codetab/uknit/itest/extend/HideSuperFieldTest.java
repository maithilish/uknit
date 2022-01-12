package org.codetab.uknit.itest.extend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class HideSuperFieldTest {
    @InjectMocks
    private HideSuperField hideSuperField;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSetLicensePlate() {
        String license = "Foo";
        hideSuperField.setLicensePlate(license);
    }

    @Test
    public void testGetLicensePlate() {
        String license = "Foo";
        hideSuperField.setLicensePlate(license);
        String actual = hideSuperField.getLicensePlate();
        assertEquals(license, actual);
    }

    @Test
    public void testUpdateLicensePlate() {
        String license = "Foo";
        hideSuperField.updateLicensePlate(license);
    }
}
