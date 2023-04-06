package org.codetab.uknit.itest.imc.ret;

public class Invoke {

    public boolean invokeOnFieldAccess(final Zoo zoo) {
        return imc(zoo);
    }

    private boolean imc(final Zoo zoo) {
        return Zoo.CURRENT_USER.equals("foo");
    }

}

interface Zoo {
    String CURRENT_USER = "foo";
}
