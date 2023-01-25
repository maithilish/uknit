package org.codetab.uknit.itest.variable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.variable.Model.StepInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class AssignParameterToVarTest {
    @InjectMocks
    private AssignParameterToVar assignParameterToVar;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetStepName() {
        StepInfo stepInfo = Mockito.mock(StepInfo.class);
        StepInfo thisStep = stepInfo;
        String apple = "Foo";

        when(thisStep.getName()).thenReturn(apple);

        String actual = assignParameterToVar.getStepName(stepInfo);

        assertEquals(apple, actual);
    }
}
