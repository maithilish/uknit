package org.codetab.uknit.itest.reassign;

import org.codetab.uknit.itest.reassign.Model.Foo;

public class Var {

    public String defineAndAssign(final Foo foo) {
        int index = 1;
        String x = foo.get(index);
        return x;
    }

    public String reassignBeforeVarUse(final Foo foo) {
        int index = 1;
        index = 2;
        String y = foo.get(index);
        return y;
    }

    public String reassignAfterVarUse(final Foo foo) {
        int index = 1;
        String x = foo.get(index);

        index = 2;
        String y = foo.get(index);
        return x + y;
    }

    /**
     * The
     * @param foo
     * @return
     */
    public String reassignNameVarConflicts(final Foo foo) {
        int index = 1;
        String x = foo.get(index);

        @SuppressWarnings("unused")
        int index2 = 0;
        index = 2;
        String y = foo.get(index);
        return x + y;
    }
}
