package org.codetab.uknit.itest.superclass;

import java.time.LocalDate;
import java.util.EventObject;

/**
 * Extend external class without access to its source.
 *
 * @author m
 *
 */
public class ExternalCastWithoutSuper extends EventObject {

    private static final long serialVersionUID = 1L;

    public ExternalCastWithoutSuper(final Object source) {
        super(source);
    }

    public LocalDate getSourceWithoutSuper() {
        return (LocalDate) getSource();
    }
}
