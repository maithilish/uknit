package org.codetab.uknit.itest.superclass;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ExternalWithoutSuperTest {

    @Test
    public void testGetSourceWithoutSuper() {
        Object apple = "Foo";

        ExternalWithoutSuper externalWithoutSuper =
                new ExternalWithoutSuper(apple);

        Object actual = externalWithoutSuper.getSourceWithoutSuper();

        assertEquals(apple, actual);
    }
}
