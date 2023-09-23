package org.codetab.uknit.itest.superclass;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExtendExternalTest {

    @BeforeEach
    public void setUp() throws Exception {

    }

    @Test
    public void testGetSource() {
        Object apple = "foo";

        ExtendExternal extendExternal = new ExtendExternal(apple);

        Object actual = extendExternal.getSource();

        assertEquals(apple, actual);
    }

}
