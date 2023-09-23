package org.codetab.uknit.itest.brace;

import org.codetab.uknit.itest.brace.Model.Foo;

class Reassign {

    public String defineAndAssign(final Foo foo) {
        int index = 1;
        String x = ((foo).get((index)));
        return x;
    }

    public String reassignBeforeVarUse(final Foo foo) {
        int index = (1);
        index = ((2));
        String y = ((foo).get((index)));
        return (y);
    }

    public String reassignAfterVarUse(final Foo foo) {
        int index = (1);
        String x = ((foo).get((index)));

        index = ((2));
        String y = ((foo).get((index)));
        return x + y;
    }

    /**
     * If var index, on reassign, is renamed as index2, but if index2 exists
     * then renamed as index3, the next available index.
     *
     * @param foo
     * @return
     */
    public String reassignNameVarConflicts(final Foo foo) {
        int index = ((1));
        String x = ((foo).get((index)));

        @SuppressWarnings("unused")
        int index2 = ((0));
        index = ((2));
        String y = ((foo).get((index)));
        return ((x) + (y));
    }
}
