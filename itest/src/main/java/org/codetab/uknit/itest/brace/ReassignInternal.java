package org.codetab.uknit.itest.brace;

import org.codetab.uknit.itest.brace.Model.Foo;

public class ReassignInternal {

    public String defineAndAssign(final Foo foo) {
        int index = ((1));
        String x = (getName((foo), (index)));
        return x;
    }

    public String reassignBeforeVarUse(final Foo foo) {
        int index = ((1));
        index = ((2));
        String y = (getName((foo), (index)));
        return y;
    }

    public String reassignAfterVarUse(final Foo foo) {
        int index = ((1));
        String x = (getName((foo), (index)));

        index = ((2));
        String y = (getName((foo), (index)));
        return ((x) + (y));
    }

    public String reassignAfterVarUse2(final Foo foo) {
        int index = ((1));
        getName((foo), (index));

        index = ((2));
        String y = (getName((foo), (index)));
        return y;
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
        String x = (getName((foo), (index)));

        @SuppressWarnings("unused")
        int index2 = ((0));
        index = ((2));
        String y = (getName((foo), (index)));
        return ((x) + (y));
    }

    // internal method
    private String getName(final Foo foo, final int index) {
        String y = (((foo).get(((index)))));
        return y;
    }
}
