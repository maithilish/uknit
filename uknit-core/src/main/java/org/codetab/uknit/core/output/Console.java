package org.codetab.uknit.core.output;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;

public class Console {

    @Inject
    private Configs configs;

    public void print(final String msg) {
        if (configs.isLibRunMode()) {
            System.out.println(msg);
        }
    }

    public void print(final String msg, final boolean force) {
        if (force) {
            System.out.println(msg);
        } else {
            print(msg);
        }
    }

}
