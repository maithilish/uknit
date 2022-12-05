package org.codetab.uknit.itest.superclass;

import java.util.Date;

public class BlendVar extends SuperBlendVar {

    // mock
    public Date invokeInternalMock(final AFactory aFactory) {
        return getInternalDate(aFactory.getDate());
    }

    public Date invokeFromSuperMock(final AFactory aFactory) {
        return getDate(aFactory.getDate());
    }

    public Date invokeWithSuperMock(final AFactory aFactory) {
        return super.getDate(aFactory.getDate());
    }

    private Date getInternalDate(final Date d1) {
        return d1;
    }

    // Real
    public String invokeInternalReal(final AFactory aFactory) {
        return getInternalString(aFactory.getString());
    }

    public String invokeFromSuperReal(final AFactory aFactory) {
        return getString(aFactory.getString());
    }

    public String invokeWithSuperReal(final AFactory aFactory) {
        return super.getString(aFactory.getString());
    }

    private String getInternalString(final String s1) {
        return s1;
    }
}


