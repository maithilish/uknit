package org.codetab.uknit.itest.superclass;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class ExternalCastWithoutSuperTest {

    @Test
    public void testGetSourceWithoutSuper() {
        LocalDate localDate = LocalDate.now();

        ExternalCastWithoutSuper externalCastWithoutSuper =
                new ExternalCastWithoutSuper(localDate);

        LocalDate actual = externalCastWithoutSuper.getSourceWithoutSuper();

        assertEquals(localDate, actual);
    }
}
