package org.codetab.uknit.itest.invoke;

import java.io.File;

public class InferredVar {

    public StringBuilder append(final StringBuilder s1, final StringBuilder s2,
            final File file) {
        return s1.append(s2.append(file.getName().toLowerCase()));
    }
}
