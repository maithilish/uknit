package org.codetab.uknit.itest.imc.ret;

import org.codetab.uknit.itest.imc.ret.Model.Person;

public class NoVar {

    /**
     * TODO L - improve the test.
     *
     * @return
     */
    public String foo() {
        return bar();
    }

    private String bar() {
        return new Person().getCity();
    }
}
