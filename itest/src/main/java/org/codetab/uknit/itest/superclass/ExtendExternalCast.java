package org.codetab.uknit.itest.superclass;

import java.time.LocalDate;
import java.util.EventObject;

/**
 * Extend external class without access to its source.
 *
 * @author m
 *
 */
public class ExtendExternalCast extends EventObject {

    private static final long serialVersionUID = 1L;

    public ExtendExternalCast(final Object source) {
        super(source);
    }

    @Override
    public LocalDate getSource() {
        return (LocalDate) super.getSource();
    }
}
