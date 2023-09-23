package org.codetab.uknit.itest.internal;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

/**
 * Call IM with another IM as arg.
 *
 * @author Maithilish
 *
 */
class InternalNestedArg {

    // Test A
    public int callA(final Date date) {
        return internalA2(internalA1(date));
    }

    private Instant internalA1(final Date date) {
        return date.toInstant();
    }

    private int internalA2(final Instant instant1) {
        return instant1.getNano();
    }

    // Test B
    public boolean callB(final DateFormat dateFormat, final String dateStr1,
            final Date date2) throws ParseException {
        return internalB2(internalB1(dateFormat, dateStr1), date2);
    }

    private Date internalB1(final DateFormat dateFormat, final String dateStr1)
            throws ParseException {
        return dateFormat.parse(dateStr1);
    }

    private boolean internalB2(final Date date1, final Date date2) {
        return date1.after(date2);
    }

    // Test C - arg and param diff name
    public Instant mockDiffNameC(final Instant instant) {
        return internalC1(internalC2(internalC3(instant)));
    }

    private Instant internalC1(final Instant instantX) {
        return instantX.minusMillis(3);
    }

    private Instant internalC2(final Instant instantY) {
        return instantY.minusMillis(2);
    }

    private Instant internalC3(final Instant instantZ) {
        return instantZ.minusMillis(1);
    }

    // Test D - arg and param same name
    public Instant mockSameNameD(final Instant instant) {
        return internalD1(internalD2(internalD3(instant)));
    }

    private Instant internalD1(final Instant instant) {
        return instant.minusMillis(6);
    }

    private Instant internalD2(final Instant instant) {
        return instant.minusMillis(5);
    }

    private Instant internalD3(final Instant instant) {
        return instant.minusMillis(4);
    }

    /*
     * Test E - arg and param diff name. As the objects are real, developer has
     * to set the expected value of return var.
     */
    public String realDiffNameE(final String str) {
        return internalE1(internalE2(internalE3(str)));
    }

    private String internalE1(final String strX) {
        return strX.concat("E1");
    }

    private String internalE2(final String strY) {
        return strY.concat("E2");
    }

    private String internalE3(final String strZ) {
        return strZ.concat("E3");
    }

    /*
     * Test F - arg and param same name. As the objects are real, developer has
     * to set the expected value of return var.
     */
    public String realSameNameF(final String str) {
        return internalF1(internalF2(internalF3(str)));
    }

    private String internalF1(final String str) {
        return str.concat("F1");
    }

    private String internalF2(final String str) {
        return str.concat("F2");
    }

    private String internalF3(final String str) {
        return str.concat("F3");
    }
}
