package org.codetab.uknit.itest.imc.vararg;

import java.util.List;

public class VarArgParameter {

    public String noArgForVarArgParam(final List<String> names) {
        return imc(names);
    }

    public String singleArgForVarArgParam(final List<String> names) {
        return imc(names, 0);
    }

    public String multiArgForVarArgParam(final List<String> names) {
        return imc(names, 0, 1, 2);
    }

    private String imc(final List<String> names, final Integer... keys) {
        if (keys.length > 0) {
            return names.get(keys[0]);
        } else {
            return null;
        }
    }
}
