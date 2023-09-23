package org.codetab.uknit.itest.superclass;

import java.util.EventObject;

/**
 * Extend external class without access to its source.
 *
 * @author m
 *
 */
public class ExtendExternal extends EventObject {

    private static final long serialVersionUID = 1L;

    public ExtendExternal(final Object source) {
        super(source);
    }

    @Override
    public Object getSource() {
        return super.getSource();
    }
}
