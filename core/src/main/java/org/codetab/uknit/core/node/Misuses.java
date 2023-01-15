package org.codetab.uknit.core.node;

import static java.util.Objects.nonNull;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.IVar;

public class Misuses {

    @Inject
    private Types types;

    /**
     * Mockito throws errors for
     *
     * 1. you stub either of: final/private/equals()/hashCode() methods. Those
     * methods *cannot* be stubbed/verified. Mocking methods declared on
     * non-public parent classes is not supported.
     *
     * TODO L - yet to fully implement.
     *
     * @return
     */
    public boolean isMisuse(final IVar var) {
        String clzName = types.getClzName(var.getType());
        if (nonNull(clzName) && clzName.equals("java.lang.Class")) {
            return true;
        }
        return false;
    }
}
