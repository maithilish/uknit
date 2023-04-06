package org.codetab.uknit.itest.superclass;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class ExtendExternalCastTest {

    @Test
    public void testGetSource() {
        LocalDate localDate = LocalDate.now();

        ExtendExternalCast extendExternalCast =
                new ExtendExternalCast(localDate);

        LocalDate actual = extendExternalCast.getSource();

        assertEquals(localDate, actual);
    }
}
