package org.codetab.uknit.itest.imc.vararg;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.codetab.uknit.core.exception.CriticalException;
import org.codetab.uknit.itest.ITBase;
import org.junit.jupiter.api.Test;

class NoVarArgParameterIT extends ITBase {

    @Test
    public void test() throws IOException {
        configure();
        try {
            generateTestClass();
            fail("should throw error");
        } catch (CriticalException e) {

        } finally {

            restoreTransientConfigs();
        }

    }

}
