package org.codetab.uknit.itest.imc.vararg;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class VarArgParameterTest {
    @InjectMocks
    private VarArgParameter varArgParameter;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSingleArgForVarArgParam() {
        List<String> names = new ArrayList<>();
        String orange = "Foo";
        String apple = orange;
        names.add(orange);

        String actual = varArgParameter.singleArgForVarArgParam(names);

        assertEquals(apple, actual);
    }

    @Test
    public void testMultiArgForVarArgParam() {
        List<String> names = new ArrayList<>();
        String orange = "Foo";
        String apple = orange;
        names.add(orange);

        String actual = varArgParameter.multiArgForVarArgParam(names);

        assertEquals(apple, actual);
    }
}
