package org.codetab.uknit.itest.superclass;

import java.util.EventObject;

/**
 * Extend external class without access to its source.
 *
 * @author m
 *
 */
public class ExternalWithoutSuper extends EventObject {

    private static final long serialVersionUID = 1L;

    public ExternalWithoutSuper(final Object source) {
        super(source);
    }

    public Object getSourceWithoutSuper() {
        return super.getSource();
    }
}
