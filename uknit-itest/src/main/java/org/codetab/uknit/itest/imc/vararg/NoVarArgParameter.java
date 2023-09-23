package org.codetab.uknit.itest.imc.vararg;

import java.util.List;

public class NoVarArgParameter {

    // Should throw error
    public String noArgForVarArgParam(final List<String> names) {
        return imc(names);
    }

    private String imc(final List<String> names, final Integer... keys) {
        if (keys.length > 0) {
            return names.get(keys[0]);
        } else {
            return null;
        }
    }
}
