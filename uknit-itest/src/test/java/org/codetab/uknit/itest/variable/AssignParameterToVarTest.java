package org.codetab.uknit.itest.variable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;

import org.codetab.uknit.itest.variable.Model.Foo;
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

    @Test
    public void testValueOfVar() {
        Foo foo = Mockito.mock(Foo.class);
        File f1 = Mockito.mock(File.class);
        File f2 = Mockito.mock(File.class);
        boolean flg = true;
        File a = flg ? f1 : f2;
        File c = f2;
        String apple = "Foo";
        String grape = "Bar";
        String orange = "Baz";
        String kiwi = "Qux";
        String mango = "Quux";
        String banana = "Corge";

        when(a.getAbsolutePath()).thenReturn(apple).thenReturn(grape)
                .thenReturn(orange).thenReturn(kiwi).thenReturn(mango);
        when(c.getAbsolutePath()).thenReturn(banana);
        assignParameterToVar.valueOfVar(foo, f1, f2);

        verify(foo).appendString(apple);
        verify(foo).appendString(grape);
        verify(foo).appendString(orange);
        verify(foo).appendString(kiwi);
        verify(foo).appendString(mango);
        verify(foo).appendString(banana);
    }
}
