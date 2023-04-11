package org.codetab.uknit.itest.clz;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class SutVarConflictTest {
    @InjectMocks
    private SutVarConflict sutVarConflict;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUseSutAsVarName() {
        File file = Mockito.mock(File.class);
        String sutVarConflict = "foo";
        String apple = "Foo";

        when(file.format(sutVarConflict)).thenReturn(apple);
        // sutVarConflict.useSutAsVarName(file);

        // fail("unable to assert, STEPIN");
    }
}
